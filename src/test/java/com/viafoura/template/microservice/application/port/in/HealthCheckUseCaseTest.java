package com.viafoura.template.microservice.application.port.in;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.viafoura.template.microservice.application.port.out.metrics.ApplicationMetricsPort;
import com.viafoura.template.microservice.application.service.HealthCheckService;
import org.junit.jupiter.api.*;

class HealthCheckUseCaseTest {

    private final ApplicationMetricsPort applicationMetricsPort = mock(ApplicationMetricsPort.class);

    private HealthCheckUseCase healthCheckUseCase;

    @BeforeEach
    void setup() {
        healthCheckUseCase = new HealthCheckService(applicationMetricsPort);
    }

    @Test
    void givenHealthyService_whenHealthy_thenSuccess() {
        assertTrue(healthCheckUseCase.isHealthy());
        verify(applicationMetricsPort, times(1)).incrementHealthyCalls();
    }
}
