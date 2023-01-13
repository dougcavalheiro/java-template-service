package com.viafoura.template.microservice.application.port.out.stream;

import com.viafoura.template.microservice.domain.model.StreamEvent;

public interface MessagePublisherPort {

    void publishMessageToOutgoingTopic(StreamEvent streamEvent, String outgoingTopic);

    void publishMessageToAllTopics(StreamEvent streamEvent);

}
