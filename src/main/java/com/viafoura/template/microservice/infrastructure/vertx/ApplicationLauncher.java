package com.viafoura.template.microservice.infrastructure.vertx;

import com.viafoura.template.microservice.infrastructure.metric.MicrometerRegistryFactory;
import io.vertx.core.Launcher;
import io.vertx.core.VertxOptions;
import io.vertx.micrometer.MicrometerMetricsOptions;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ApplicationLauncher extends Launcher {

    public static void main(String[] args) {
        new ApplicationLauncher().dispatch(args);
    }

    @Override
    public void beforeStartingVertx(VertxOptions options) {
        // Enable Vert.x metrics
        options.setMetricsOptions(
                new MicrometerMetricsOptions()
                        .setMicrometerRegistry(MicrometerRegistryFactory.create())
                        .setEnabled(true)
        );
    }
}