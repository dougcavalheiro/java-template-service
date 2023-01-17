package com.viafoura.template.microservice.application.service;

import com.viafoura.template.microservice.application.port.input.ProcessEventUseCase;
import com.viafoura.template.microservice.application.port.output.metric.ApplicationMetricsPort;
import com.viafoura.template.microservice.application.port.output.stream.MessagePublisherPort;
import com.viafoura.template.microservice.domain.event.StreamEvent;
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

