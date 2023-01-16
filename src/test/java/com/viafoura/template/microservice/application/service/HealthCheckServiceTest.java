package com.viafoura.template.microservice.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.viafoura.template.microservice.application.port.out.metrics.ApplicationMetricsPort;
import org.junit.jupiter.api.*;

class HealthCheckServiceTest {

    private final ApplicationMetricsPort applicationMetricsPort = mock(ApplicationMetricsPort.class);

    private HealthCheckService healthCheckService;

    @BeforeEach
    void setup() {
        healthCheckService = new HealthCheckService(applicationMetricsPort);
    }

    @Test
    void givenService_whenIsHealthy_thenSuccess() {
        assertTrue(healthCheckService.isHealthy());
        verify(applicationMetricsPort, times(1)).incrementHealthyCalls();
    }
}