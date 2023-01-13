package com.viafoura.template.microservice.infra.guice;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import com.viafoura.common.vertx.ServiceConnector;
import com.viafoura.template.microservice.infra.vertx.verticle.HealthCheckVerticle;

public class ServiceConnectorModule extends AbstractModule {

    @Override
    protected void configure() {
        Multibinder<ServiceConnector> multiBinder = Multibinder.newSetBinder(binder(), ServiceConnector.class);
        multiBinder.addBinding().to(HealthCheckVerticle.class);
    }
}
