package com.viafoura.template.microservice.infra.config;

import com.typesafe.config.Config;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.Getter;

@Getter
@Singleton
public class AppConfig implements VertxConfig, StreamConfig {

    private final int serverPort;
    private final List<String> incomingTopics;
    private final List<String> outgoingTopics;
    private final String applicationId;
    private final String bootstrapServer;
    private final String resetPolicy;
    private final int metadataMaxAgeMs;
    private final String namespace;

    @Inject
    public AppConfig(Config config) {
        this.serverPort = config.getInt("service.server.port");
        this.incomingTopics = config.getStringList("kafka.topic.incoming");
        this.outgoingTopics = config.getStringList("kafka.topic.outgoing");
        this.namespace = config.getString("kafka.namespace");
        this.applicationId = config.getString("kafka.applicationId");
        this.bootstrapServer = config.getString("kafka.bootstrap");
        this.resetPolicy = config.getString("kafka.auto.offset.reset");
        this.metadataMaxAgeMs = config.getInt("kafka.max.age.ms");
    }
}
