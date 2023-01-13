package com.viafoura.template.microservice.application.service;

import com.viafoura.template.microservice.application.port.in.ProcessEventUseCase;
import com.viafoura.template.microservice.application.port.out.metrics.ApplicationMetricsPort;
import com.viafoura.template.microservice.application.port.out.stream.MessagePublisherPort;
import com.viafoura.template.microservice.domain.model.StreamEvent;
import javax.inject.Inject;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(onConstructor = @__({@Inject}))
public class ProcessEventService implements ProcessEventUseCase {

    private final ApplicationMetricsPort applicationMetricsPort;
    private final MessagePublisherPort messagePublisherPort;

    @Override
    public void processEvent(StreamEvent event) {
        event.markAsVerified();
        messagePublisherPort.publishMessageToAllTopics(event);
        applicationMetricsPort.incrementVerifiedEvents();
    }
}

