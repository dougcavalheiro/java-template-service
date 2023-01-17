package com.viafoura.template.microservice.infrastructure.guice;

import static com.google.inject.multibindings.Multibinder.newSetBinder;
import static java.util.Objects.requireNonNull;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.multibindings.Multibinder;
import com.viafoura.common.vertx.VerticleAdapter;
import com.viafoura.common.vertx.context.extractor.HeadersContextExtractor;
import com.viafoura.common.vertx.context.extractor.OriginatingIpExtractor;
import com.viafoura.common.vertx.context.extractor.SyndicationContextExtractor;
import com.viafoura.common.vertx.context.extractor.UserContextExtractor;
import com.viafoura.template.microservice.infrastructure.config.VertxConfig;
import com.viafoura.template.microservice.infrastructure.vertx.security.OperationModelKeyType;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.api.contract.RouterFactoryOptions;
import javax.inject.Singleton;

public class VertxModule extends AbstractModule {

    private final Vertx vertx;
    private final JsonObject config;

    public VertxModule(Vertx vertx, JsonObject config) {
        this.vertx = requireNonNull(vertx);
        this.config = requireNonNull(config);
    }

    @Override
    protected void configure() {
        bind(Vertx.class).toInstance(vertx);
        bindSetOfHeadersContextExtractors();
    }

    @Provides
    @Singleton
    VerticleAdapter provideVerticleAdapter() {
        return new VerticleAdapter(vertx, config);
    }

    @Provides
    @Singleton
    HttpServerOptions provideHttpServerOptions(VertxConfig vertxConfig) {
        return new HttpServerOptions()
                .setPort(vertxConfig.getServerPort())
                .setCompressionSupported(true)
                .setDecompressionSupported(true);
    }

    @Provides
    @Singleton
    RouterFactoryOptions provideRouterFactoryOptions() {
        return new RouterFactoryOptions().setOperationModelKey(OperationModelKeyType.OPERATION_POJO.getKey());
    }

    private void bindSetOfHeadersContextExtractors() {
        // register visitors of any given request's headers
        // this is used to create the RequestContext, if there is authentication, which is then passed into the API
        Multibinder<HeadersContextExtractor> multiBinder = newSetBinder(binder(), HeadersContextExtractor.class);
        multiBinder.addBinding().to(UserContextExtractor.class);
        multiBinder.addBinding().to(SyndicationContextExtractor.class);
        multiBinder.addBinding().to(OriginatingIpExtractor.class);
    }
}
