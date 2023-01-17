package com.viafoura.template.microservice.infrastructure.vertx.verticle;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.viafoura.template.microservice.infrastructure.guice.AdapterModule;
import com.viafoura.template.microservice.infrastructure.guice.ApiModule;
import com.viafoura.template.microservice.infrastructure.guice.ConfigModule;
import com.viafoura.template.microservice.infrastructure.guice.MetricsModule;
import com.viafoura.template.microservice.infrastructure.guice.ServiceConnectorModule;
import com.viafoura.template.microservice.infrastructure.guice.StreamModule;
import com.viafoura.template.microservice.infrastructure.guice.UseCaseModule;
import com.viafoura.template.microservice.infrastructure.guice.VertxModule;
import io.vertx.core.Vertx;

public class MainVerticle extends BaseVerticle {

    @Override
    protected Injector getInjector() {
        Config typeSafeConfig = ConfigFactory.load();
        Vertx vertx = getVertx();
        return Guice.createInjector(
                new ConfigModule(typeSafeConfig),
                new MetricsModule(),
                new VertxModule(vertx, config()),
                new ServiceConnectorModule(),
                new ApiModule(),
                new StreamModule(),
                new AdapterModule(),
                new UseCaseModule());
    }
}
