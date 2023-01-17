package com.viafoura.template.microservice.infrastructure.guice;

import static org.junit.jupiter.api.Assertions.*;

import com.viafoura.template.microservice.application.port.input.HealthCheckUseCase;
import com.viafoura.template.microservice.application.port.input.ProcessEventUseCase;
import org.junit.jupiter.api.*;

class UseCaseModuleTest extends ModuleTester {

    @Test
    void givenUseCaseModule_whenCreateInjector_thenInject() {
        assertNotNull(injector.getInstance(HealthCheckUseCase.class));
        assertNotNull(injector.getInstance(ProcessEventUseCase.class));
    }
}