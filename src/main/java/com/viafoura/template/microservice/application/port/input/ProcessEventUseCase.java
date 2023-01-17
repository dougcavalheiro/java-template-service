package com.viafoura.template.microservice.application.port.input;

import com.viafoura.template.microservice.domain.event.StreamEvent;

public interface ProcessEventUseCase {

    void processEvent(StreamEvent event);

}
