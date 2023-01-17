package com.viafoura.template.microservice.application.port.output.stream;

import com.viafoura.template.microservice.domain.event.StreamEvent;

public interface MessagePublisherPort {

    void publishMessageToOutgoingTopic(StreamEvent streamEvent, String outgoingTopic);

    void publishMessageToAllTopics(StreamEvent streamEvent);

}
