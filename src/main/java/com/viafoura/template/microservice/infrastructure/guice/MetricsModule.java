package com.viafoura.template.microservice.infrastructure.guice;

import com.google.inject.AbstractModule;
import com.viafoura.template.microservice.infrastructure.metric.MicrometerRegistryFactory;
import io.micrometer.core.instrument.MeterRegistry;

public class MetricsModule extends AbstractModule {

    @Override
    protected void configure() {
        super.configure();
        bind(MeterRegistry.class).toInstance(MicrometerRegistryFactory.get());
    }
}
