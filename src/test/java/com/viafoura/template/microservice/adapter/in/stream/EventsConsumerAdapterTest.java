package com.viafoura.template.microservice.adapter.in.stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.viafoura.template.microservice.application.port.in.ProcessEventUseCase;
import com.viafoura.template.microservice.domain.model.StreamEvent;
import com.viafoura.template.microservice.infra.stream.KafkaEvent;
import java.util.HashMap;
import org.junit.jupiter.api.*;

class EventsConsumerAdapterTest {

    private final ProcessEventUseCase processEventUseCase = mock(ProcessEventUseCase.class);
    private final KafkaEvent kafkaEvent = new KafkaEvent(new HashMap<>());
    private EventsConsumerAdapter eventsConsumerAdapter;

    @BeforeEach
    void setup() {
        eventsConsumerAdapter = new EventsConsumerAdapter(processEventUseCase);
    }

    @Test
    void givenEvent_whenConsumeEvent_thenSuccess() {
        assertDoesNotThrow(() -> eventsConsumerAdapter.consumeEvent(kafkaEvent));
        verify(processEventUseCase, times(1)).processEvent(any(StreamEvent.class));
    }

}