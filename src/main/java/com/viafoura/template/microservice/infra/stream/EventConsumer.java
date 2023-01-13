package com.viafoura.template.microservice.infra.stream;

public interface EventConsumer {

    void consumeEvent(KafkaEvent event);

}
