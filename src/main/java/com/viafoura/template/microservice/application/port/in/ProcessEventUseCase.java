package com.viafoura.template.microservice.application.port.in;

import com.viafoura.template.microservice.domain.model.StreamEvent;

public interface ProcessEventUseCase {

    void processEvent(StreamEvent event);

}
