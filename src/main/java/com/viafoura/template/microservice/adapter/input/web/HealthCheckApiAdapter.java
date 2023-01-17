package com.viafoura.template.microservice.adapter.input.web;

import com.viafoura.common.vertx.ApiException;
import com.viafoura.common.vertx.ApiResponse;
import com.viafoura.common.vertx.ApiResponseFactory;
import com.viafoura.template.microservice.application.port.input.HealthCheckUseCase;
import com.viafoura.template.microservice.infrastructure.vertx.api.HealthCheckApi;
import io.reactivex.Single;
import javax.inject.Inject;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(onConstructor = @__({@Inject}))
public class HealthCheckApiAdapter implements HealthCheckApi {

    private final HealthCheckUseCase healthCheckUseCase;

    @Override
    public Single<ApiResponse<Void>> getHealthyResponse() {
        return healthCheckUseCase.isHealthy() ? Single.just(ApiResponseFactory.noContent())
                : Single.error(new ApiException(503, "Service Unavailable", null));
    }
}
