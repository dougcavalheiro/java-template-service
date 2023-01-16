package com.viafoura.template.microservice.infra.guice;

import static org.junit.jupiter.api.Assertions.*;

import com.viafoura.template.microservice.application.port.in.HealthCheckUseCase;
import com.viafoura.template.microservice.application.port.in.ProcessEventUseCase;
import org.junit.jupiter.api.*;

class UseCaseModuleTest extends ModuleTester {

    @Test
    void givenUseCaseModule_whenCreateInjector_thenInject() {
        assertNotNull(injector.getInstance(HealthCheckUseCase.class));
        assertNotNull(injector.getInstance(ProcessEventUseCase.class));
    }
}