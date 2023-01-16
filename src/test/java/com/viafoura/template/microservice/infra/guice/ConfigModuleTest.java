package com.viafoura.template.microservice.infra.guice;

import static org.junit.jupiter.api.Assertions.*;

import com.viafoura.common.vertx.kafka.KafkaConfig;
import com.viafoura.template.microservice.infra.config.StreamConfig;
import com.viafoura.template.microservice.infra.config.VertxConfig;
import org.junit.jupiter.api.*;

class ConfigModuleTest extends ModuleTester {

    @Test
    void givenConfigModule_whenCreateInjector_thenInjectConfig() {
        assertNotNull(injector.getInstance(VertxConfig.class));
        assertNotNull(injector.getInstance(StreamConfig.class));
        assertNotNull(injector.getInstance(KafkaConfig.class));
    }
}