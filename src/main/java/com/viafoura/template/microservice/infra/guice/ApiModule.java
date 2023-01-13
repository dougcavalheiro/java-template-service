package com.viafoura.template.microservice.infra.guice;

import com.google.inject.AbstractModule;
import com.viafoura.template.microservice.adapter.in.web.HealthCheckApiAdapter;
import com.viafoura.template.microservice.infra.vertx.api.HealthCheckApi;

public class ApiModule extends AbstractModule {

    @Override
    protected void configure() {
        super.configure();
        bind(HealthCheckApi.class).to(HealthCheckApiAdapter.class);
    }
}
