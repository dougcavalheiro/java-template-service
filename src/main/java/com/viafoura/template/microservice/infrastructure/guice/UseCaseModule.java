package com.viafoura.template.microservice.infrastructure.guice;

import com.google.inject.AbstractModule;
import com.viafoura.template.microservice.application.port.input.HealthCheckUseCase;
import com.viafoura.template.microservice.application.port.input.ProcessEventUseCase;
import com.viafoura.template.microservice.application.service.HealthCheckService;
import com.viafoura.template.microservice.application.service.ProcessEventService;

public class UseCaseModule extends AbstractModule {

    @Override
    protected void configure() {
        super.configure();
        bind(HealthCheckUseCase.class).to(HealthCheckService.class);
        bind(ProcessEventUseCase.class).to(ProcessEventService.class);
    }
}
