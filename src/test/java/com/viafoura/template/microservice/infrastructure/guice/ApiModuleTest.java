package com.viafoura.template.microservice.infrastructure.guice;

import static org.junit.jupiter.api.Assertions.*;

import com.viafoura.template.microservice.infrastructure.vertx.api.HealthCheckApi;
import org.junit.jupiter.api.*;

class ApiModuleTest extends ModuleTester {

    @Test
    void givenApiModule_whenCreateInjector_thenInjectApiImplementations() {
        assertNotNull(injector.getInstance(HealthCheckApi.class));
    }
}