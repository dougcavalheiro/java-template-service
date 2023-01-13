package com.viafoura.template.microservice.infra.vertx.verticle;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.inject.Injector;
import com.viafoura.common.vertx.ServiceConnector;
import com.viafoura.common.vertx.ServiceConnectorRegistry;
import com.viafoura.common.vertx.handlers.CorsHandlerFactory;
import com.viafoura.common.vertx.kafka.KafkaStreamSourceMonitor;
import com.viafoura.template.microservice.infra.vertx.event.EventBusType;
import com.viafoura.template.microservice.infra.metric.MetricsPlatforms;
import com.viafoura.template.microservice.infra.metric.MicrometerRegistryFactory;
import com.viafoura.template.microservice.infra.vertx.error.RoutingErrorHandler;
import com.viafoura.template.microservice.infra.vertx.security.SecuritySchemaType;
import com.viafoura.template.microservice.infra.vertx.security.TokenSecurityHandler;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.json.jackson.DatabindCodec;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.api.contract.RouterFactoryOptions;
import io.vertx.ext.web.api.contract.openapi3.OpenAPI3RouterFactory;
import io.vertx.ext.web.handler.BodyHandler;
import java.util.Arrays;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
abstract class BaseVerticle extends AbstractVerticle {

    private static final int ABNORMAL_TERMINATION_STATUS_CODE = 1;
    private static final String API_SPEC_FILE = "api-spec.yaml";

    private HttpServer httpServer;
    private ServiceConnectorRegistry serviceConnectorRegistry;
    private TokenSecurityHandler tokenSecurityHandler;

    @Override
    public void start(Promise<Void> promise) {
        this.getVertx().executeBlocking(this::initialize, result -> {
            if (result.failed()) {
                promise.fail(result.cause());
                System.exit(ABNORMAL_TERMINATION_STATUS_CODE);
            } else {
                promise.complete();
            }
        });
    }

    private void initialize(Promise<Void> promise) {
        Injector injector = getInjector();
        serviceConnectorRegistry = injector.getInstance(ServiceConnectorRegistry.class);
        HttpServerOptions httpServerOptions = injector.getInstance(HttpServerOptions.class);
        RouterFactoryOptions routerFactoryOptions = injector.getInstance(RouterFactoryOptions.class);
        KafkaStreamSourceMonitor streamSourceMonitor = injector.getInstance(KafkaStreamSourceMonitor.class);

        configureJackson();

        OpenAPI3RouterFactory.create(getVertx(), API_SPEC_FILE, result -> {
            if (result.failed()) {
                promise.fail(result.cause());
                return;
            }

            OpenAPI3RouterFactory factory = result.result();
            factory.setOptions(routerFactoryOptions);
            addSecurityHandlers(factory);
            addGlobalHandlers(factory);
            addPrometheusMetricsHandler(factory);

            serviceConnectorRegistry.getServiceConnectors().forEach(ServiceConnector::start);
            mountEventBusAndAddErrorHandlers(factory);

            httpServer = getVertx().createHttpServer(httpServerOptions);

            httpServer.requestHandler(factory.getRouter()).listen(serverStartupResult -> {
                if (serverStartupResult.failed()) {
                    promise.fail(serverStartupResult.cause());
                } else {
                    promise.complete();
                }
            });
        });

        streamSourceMonitor.start();
    }

    private static void configureJackson() {
        DatabindCodec.mapper().registerModule(new JavaTimeModule());
        DatabindCodec.prettyMapper().registerModule(new JavaTimeModule());
    }

    private static void addGlobalHandlers(OpenAPI3RouterFactory factory) {
        factory.addGlobalHandler(BodyHandler.create());
        factory.addGlobalHandler(CorsHandlerFactory.createInstance());
    }

    private void addSecurityHandlers(OpenAPI3RouterFactory factory) {
        factory.addSecurityHandler(SecuritySchemaType.TOKEN_IN_COOKIE.getName(), RoutingContext::next);
        factory.addSecurityHandler(SecuritySchemaType.TOKEN_IN_COOKIE.getName(), tokenSecurityHandler);
        factory.addSecurityHandler(SecuritySchemaType.TOKEN.getName(), RoutingContext::next);
        factory.addSecurityHandler(SecuritySchemaType.TOKEN.getName(), tokenSecurityHandler);
    }

    private void addPrometheusMetricsHandler(OpenAPI3RouterFactory factory) {
        Optional<MeterRegistry> meterRegistry = MicrometerRegistryFactory.findRegistryByPlatform(MetricsPlatforms.PROMETHEUS);

        if (meterRegistry.isPresent()) {
            PrometheusMeterRegistry prometheusMeterRegistry = (PrometheusMeterRegistry) meterRegistry.get();
            factory.addHandlerByOperationId("metrics",
                    context -> context.response().end(prometheusMeterRegistry.scrape()));
        } else {
            log.info("Can't find Prometheus instance in Composite Registry");
        }
    }

    private static void mountEventBusAndAddErrorHandlers(OpenAPI3RouterFactory factory) {
        final RoutingErrorHandler failureHandler = new RoutingErrorHandler();
        Arrays.stream(EventBusType.values())
                .forEach(event -> {
                    factory.mountOperationToEventBus(event.getOperationId(), event.getAddress());
                    factory.addFailureHandlerByOperationId(event.getOperationId(), failureHandler);
                });
    }

    protected abstract Injector getInjector();

    @Override
    public void stop(Promise<Void> promise) {
        // Stopping each ServiceConnector before is an attempt to avoid weird states if the server crashes
        serviceConnectorRegistry.getServiceConnectors().forEach(ServiceConnector::stop);
        httpServer.close();
        promise.complete();
    }
}
