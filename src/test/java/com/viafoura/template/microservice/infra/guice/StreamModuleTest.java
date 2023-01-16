package com.viafoura.template.microservice.infra.guice;

import static org.junit.jupiter.api.Assertions.*;

import com.viafoura.common.vertx.kafka.KafkaStreamMonitorMetrics;
import com.viafoura.common.vertx.kafka.KafkaStreamSource;
import com.viafoura.common.vertx.kafka.TopologySupplier;
import com.viafoura.template.microservice.infra.stream.EventConsumer;
import org.junit.jupiter.api.*;

class StreamModuleTest extends ModuleTester {

    @Test
    void givenStreamModule_whenCreateInjector_thenInject() {
        assertNotNull(injector.getInstance(KafkaStreamSource.class));
        assertNotNull(injector.getInstance(TopologySupplier.class));
        assertNotNull(injector.getInstance(EventConsumer.class));
        assertNotNull(injector.getInstance(KafkaStreamMonitorMetrics.class));
    }

}