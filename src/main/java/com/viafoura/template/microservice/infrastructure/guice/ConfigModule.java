package com.viafoura.template.microservice.infrastructure.guice;

import com.google.inject.AbstractModule;
import com.typesafe.config.Config;
import com.viafoura.common.vertx.kafka.KafkaConfig;
import com.viafoura.template.microservice.infrastructure.config.AppConfig;
import com.viafoura.template.microservice.infrastructure.config.StreamConfig;
import com.viafoura.template.microservice.infrastructure.config.VertxConfig;

public class ConfigModule extends AbstractModule {

    private final Config config;

    public ConfigModule(Config config) {
        this.config = config;
    }

    @Override
    protected void configure() {
        super.configure();
        bind(AppConfig.class).toInstance(new AppConfig(config));
        bind(VertxConfig.class).to(AppConfig.class);
        bind(KafkaConfig.class).to(AppConfig.class);
        bind(StreamConfig.class).to(AppConfig.class);
    }
}
