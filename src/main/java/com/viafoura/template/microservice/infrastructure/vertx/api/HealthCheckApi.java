package com.viafoura.template.microservice.infrastructure.vertx.api;

import com.viafoura.common.vertx.ApiResponse;
import io.reactivex.Single;

public interface HealthCheckApi {

    Single<ApiResponse<Void>> getHealthyResponse();

}
