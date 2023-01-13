package com.viafoura.template.microservice.infra.vertx.verticle;

import com.viafoura.common.vertx.ApiResponseFactory;
import com.viafoura.common.vertx.ServiceConnector;
import com.viafoura.common.vertx.VerticleAdapter;
import com.viafoura.template.microservice.infra.vertx.api.HealthCheckApi;
import com.viafoura.template.microservice.infra.vertx.event.EventBusType;
import io.vertx.core.Vertx;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HealthCheckVerticle implements ServiceConnector {

    private final Vertx vertx;
    private final HealthCheckApi healthCheckApi;

    @Inject
    public HealthCheckVerticle(VerticleAdapter verticleAdapter, HealthCheckApi healthCheckApi) {
        this.vertx = verticleAdapter.getVertx();
        this.healthCheckApi = healthCheckApi;
    }

    @Override
    public void start() {
        log.debug("Starting health check verticle");
        vertx.eventBus().<JsonObject>consumer(EventBusType.HEALTHY.getAddress())
                .handler(message -> this.healthCheckApi.getHealthyResponse().subscribe(
                        onSuccess -> message.reply(new JsonObject(Json.encode(ApiResponseFactory.noContent()))),
                        onError -> message.reply(new JsonObject(Json.encode(onError)))).dispose());
    }

    @Override
    public void stop() {
        log.debug("Stopping HealthCheck verticle");
    }
}
