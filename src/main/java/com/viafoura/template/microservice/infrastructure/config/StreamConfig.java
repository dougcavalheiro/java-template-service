package com.viafoura.template.microservice.infrastructure.config;

import com.viafoura.common.vertx.kafka.KafkaConfig;
import java.util.List;

public interface StreamConfig extends KafkaConfig {

    List<String> getIncomingTopics();
    List<String> getOutgoingTopics();

}
