package com.viafoura.template.microservice.infrastructure.stream;

public interface EventConsumer {

    void consumeEvent(KafkaEvent event);

}
