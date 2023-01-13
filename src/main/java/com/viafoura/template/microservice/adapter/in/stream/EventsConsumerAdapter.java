package com.viafoura.template.microservice.adapter.in.stream;

import com.viafoura.template.microservice.application.port.in.ProcessEventUseCase;
import com.viafoura.template.microservice.domain.model.StreamEvent;
import com.viafoura.template.microservice.infra.stream.EventConsumer;
import com.viafoura.template.microservice.infra.stream.KafkaEvent;
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
