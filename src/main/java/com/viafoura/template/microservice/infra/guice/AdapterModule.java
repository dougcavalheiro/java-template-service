package com.viafoura.template.microservice.infra.guice;

import com.google.inject.AbstractModule;
import com.viafoura.template.microservice.adapter.out.metrics.ServiceMetricsAdapter;
import com.viafoura.template.microservice.adapter.out.stream.MessagePublisherAdapter;
import com.viafoura.template.microservice.application.port.out.metrics.ApplicationMetricsPort;
import com.viafoura.template.microservice.application.port.out.stream.MessagePublisherPort;

public class AdapterModule extends AbstractModule {

    @Override
    protected void configure() {
        super.configure();
        bind(ApplicationMetricsPort.class).to(ServiceMetricsAdapter.class);
        bind(MessagePublisherPort.class).to(MessagePublisherAdapter.class);
    }
}
