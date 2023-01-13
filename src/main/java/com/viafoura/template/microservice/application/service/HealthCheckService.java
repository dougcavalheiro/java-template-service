package com.viafoura.template.microservice.application.service;

import com.viafoura.template.microservice.application.port.in.HealthCheckUseCase;
import com.viafoura.template.microservice.application.port.out.metrics.ApplicationMetricsPort;
import javax.inject.Inject;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(onConstructor = @__({@Inject}))
public class HealthCheckService implements HealthCheckUseCase {

    private final ApplicationMetricsPort applicationMetricsPort;
    @Override
    public boolean isHealthy() {
        applicationMetricsPort.incrementHealthyCalls();
        return true;
    }
}
