package com.viafoura.template.microservice.infra.guice;

import static org.junit.jupiter.api.Assertions.*;

import com.viafoura.template.microservice.application.port.out.metrics.ApplicationMetricsPort;
import com.viafoura.template.microservice.application.port.out.stream.MessagePublisherPort;
import org.junit.jupiter.api.*;

class AdapterModuleTest extends ModuleTester {

    @Test
    void givenAdapterModule_whenCreateInjector_thenInjectAdapters() {
        assertNotNull(injector.getInstance(ApplicationMetricsPort.class));
        assertNotNull(injector.getInstance(MessagePublisherPort.class));
    }

}