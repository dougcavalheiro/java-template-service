package com.viafoura.template.microservice.adapter.in.web;

import com.viafoura.common.vertx.ApiException;
import com.viafoura.common.vertx.ApiResponse;
import com.viafoura.common.vertx.ApiResponseFactory;
import com.viafoura.template.microservice.application.port.in.HealthCheckUseCase;
import com.viafoura.template.microservice.infra.vertx.api.HealthCheckApi;
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
