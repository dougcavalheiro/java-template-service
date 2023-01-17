package com.viafoura.template.microservice.infrastructure.guice;

import com.google.inject.AbstractModule;
import com.viafoura.template.microservice.adapter.input.web.HealthCheckApiAdapter;
import com.viafoura.template.microservice.infrastructure.vertx.api.HealthCheckApi;

public class ApiModule extends AbstractModule {

    @Override
    protected void configure() {
        super.configure();
        bind(HealthCheckApi.class).to(HealthCheckApiAdapter.class);
    }
}
