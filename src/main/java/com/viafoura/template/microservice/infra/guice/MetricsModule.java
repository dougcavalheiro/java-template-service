package com.viafoura.template.microservice.infra.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.viafoura.template.microservice.infra.metric.MicrometerRegistryFactory;
import io.micrometer.core.instrument.MeterRegistry;
import javax.inject.Singleton;

public class MetricsModule extends AbstractModule {

    @Provides
    @Singleton
    public MeterRegistry provideMeterRegistry() {
        return MicrometerRegistryFactory.get();
    }
}
