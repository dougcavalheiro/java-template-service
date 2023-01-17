package com.viafoura.template.microservice.infrastructure.guice;

import com.google.inject.AbstractModule;
import com.viafoura.template.microservice.adapter.output.metrics.ServiceMetricsAdapter;
import com.viafoura.template.microservice.adapter.output.stream.MessagePublisherAdapter;
import com.viafoura.template.microservice.application.port.output.metric.ApplicationMetricsPort;
import com.viafoura.template.microservice.application.port.output.stream.MessagePublisherPort;

public class AdapterModule extends AbstractModule {

    @Override
    protected void configure() {
        super.configure();
        bind(ApplicationMetricsPort.class).to(ServiceMetricsAdapter.class);
        bind(MessagePublisherPort.class).to(MessagePublisherAdapter.class);
    }
}
