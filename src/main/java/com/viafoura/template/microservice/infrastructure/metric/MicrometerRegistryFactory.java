package com.viafoura.template.microservice.infrastructure.metric;

import com.google.inject.Singleton;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import io.github.mweirauch.micrometer.jvm.extras.ProcessMemoryMetrics;
import io.github.mweirauch.micrometer.jvm.extras.ProcessThreadMetrics;
import io.micrometer.core.instrument.Clock;
import io.micrometer.core.instrument.Meter.Id;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.jvm.ClassLoaderMetrics;
import io.micrometer.core.instrument.binder.jvm.JvmGcMetrics;
import io.micrometer.core.instrument.binder.jvm.JvmMemoryMetrics;
import io.micrometer.core.instrument.binder.jvm.JvmThreadMetrics;
import io.micrometer.core.instrument.binder.system.ProcessorMetrics;
import io.micrometer.core.instrument.composite.CompositeMeterRegistry;
import io.micrometer.core.instrument.config.MeterFilter;
import io.micrometer.prometheus.PrometheusConfig;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import io.micrometer.statsd.StatsdConfig;
import io.micrometer.statsd.StatsdFlavor;
import io.micrometer.statsd.StatsdMeterRegistry;
import java.util.Optional;
import java.util.Properties;

@Singleton
public class MicrometerRegistryFactory {

    private static final Config config = ConfigFactory.load();

    private static final CompositeMeterRegistry registry = new CompositeMeterRegistry();

    private static final Properties properties = new Properties();

    static {
        config.entrySet().forEach((entry) -> properties.put(entry.getKey(), entry.getValue().unwrapped().toString()));
    }

    private MicrometerRegistryFactory() {
    }

    public static CompositeMeterRegistry create() {
        configRegistry();
        registry.add(new PrometheusMeterRegistry(PrometheusConfig.DEFAULT));
        registry.add(buildStatsdMeterRegistry());

        bindUtilMetrics();
        return registry;
    }

    private static MeterRegistry buildStatsdMeterRegistry() {
        return new StatsdMeterRegistry(new StatsdConfig() {
            @Override
            public String get(String key) {
                return properties.getProperty(key);
            }

            @Override
            public String prefix() {
                return "service.metrics.datadog";
            }

            @Override
            public StatsdFlavor flavor() {
                return StatsdFlavor.DATADOG;
            }
        }, Clock.SYSTEM);
    }

    public static Optional<MeterRegistry> findRegistryByPlatform(MetricsPlatforms platform) {
        return registry.getRegistries().stream()
                .filter(r -> r.getClass().getName().toUpperCase().contains(platform.name()))
                .findAny();
    }

    private static void configRegistry() {
        String prefix = config.getString("service.metrics.prefix");
        registry.config().meterFilter(new MeterFilter() {
            @Override
            public Id map(Id id) {
                return id.withName(String.format("%s.%s", prefix, id.getName()));
            }
        });
    }

    private static void bindUtilMetrics() {
        new ClassLoaderMetrics().bindTo(registry);
        new JvmMemoryMetrics().bindTo(registry);

        new ProcessorMetrics().bindTo(registry);
        new JvmThreadMetrics().bindTo(registry);
        new ProcessMemoryMetrics().bindTo(registry);
        new ProcessThreadMetrics().bindTo(registry);

        try (JvmGcMetrics jvmGcMetrics = new JvmGcMetrics()) {
            jvmGcMetrics.bindTo(registry);
        }
    }

    public static MeterRegistry get() {
        return registry;
    }
}
