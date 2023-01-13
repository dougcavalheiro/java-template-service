package com.viafoura.template.microservice.infra.guice;

import com.google.inject.AbstractModule;
import com.viafoura.template.microservice.application.port.in.HealthCheckUseCase;
import com.viafoura.template.microservice.application.port.in.ProcessEventUseCase;
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
