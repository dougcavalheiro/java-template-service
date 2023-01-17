package com.viafoura.template.microservice.infrastructure.guice;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import org.junit.jupiter.api.*;

class ModuleTester {

    Injector injector;

    @BeforeEach
    void setup() {
        Config typeSafeConfig = ConfigFactory.load();
        Vertx vertx = Vertx.vertx();
        injector = Guice.createInjector(
                new ConfigModule(typeSafeConfig),
                new MetricsModule(),
                new VertxModule(vertx, new JsonObject()),
                new ServiceConnectorModule(),
                new ApiModule(),
                new StreamModule(),
                new AdapterModule(),
                new UseCaseModule());
    }
}
