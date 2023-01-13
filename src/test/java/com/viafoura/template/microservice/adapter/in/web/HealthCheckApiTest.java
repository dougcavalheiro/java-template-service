package com.viafoura.template.microservice.adapter.in.web;

import static org.mockito.Mockito.*;

import com.viafoura.common.vertx.ApiException;
import com.viafoura.template.microservice.application.port.in.HealthCheckUseCase;
import com.viafoura.template.microservice.infra.vertx.api.HealthCheckApi;
import org.junit.jupiter.api.*;

class HealthCheckApiTest {

    private final HealthCheckUseCase healthCheckUseCase = mock(HealthCheckUseCase.class);
    private final int API_RESPONSE_NO_CONTENT = 204;
    private final int API_RESPONSE_SERVICE_UNAVAILABLE = 503;
    private HealthCheckApi healthCheckApi;

    @BeforeEach
    void setup() {
        healthCheckApi = new HealthCheckApiAdapter(healthCheckUseCase);
    }

    @Test
    void givenHealthyUseCase_whenHealthyCheck_thenSuccess() {
        when(healthCheckUseCase.isHealthy()).thenReturn(Boolean.TRUE);
        healthCheckApi.getHealthyResponse()
                .test()
                .assertNoErrors()
                .assertComplete()
                .assertTerminated()
                .assertValue(apiResponse -> apiResponse.getStatusCode() == API_RESPONSE_NO_CONTENT);
    }

    @Test
    void givenHealthyUseCase_whenHealthyCheck_thenServiceUnavailable() {
        when(healthCheckUseCase.isHealthy()).thenReturn(Boolean.FALSE);
        healthCheckApi.getHealthyResponse()
                .test()
                .assertError(error -> {
                    if(error instanceof ApiException response) {
                        return response.getStatusCode() == API_RESPONSE_SERVICE_UNAVAILABLE;
                    }
                    return false;
                })
                .assertNotComplete()
                .assertTerminated()
                .assertNoValues();
    }
}
