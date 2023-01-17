package com.viafoura.template.microservice.adapter.input.stream;

import com.viafoura.template.microservice.application.port.input.ProcessEventUseCase;
import com.viafoura.template.microservice.domain.event.StreamEvent;
import com.viafoura.template.microservice.infrastructure.stream.EventConsumer;
import com.viafoura.template.microservice.infrastructure.stream.KafkaEvent;
import javax.inject.Inject;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(onConstructor = @__({@Inject}))
public class EventsConsumerAdapter implements EventConsumer {

    private final ProcessEventUseCase incomingEventUseCase;

    @Override
    public void consumeEvent(KafkaEvent event) {
        incomingEventUseCase.processEvent(new StreamEvent(event.getActualEvent()));
    }
}
