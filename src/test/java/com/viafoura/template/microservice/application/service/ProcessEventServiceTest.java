package com.viafoura.template.microservice.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.viafoura.template.microservice.application.port.out.metrics.ApplicationMetricsPort;
import com.viafoura.template.microservice.application.port.out.stream.MessagePublisherPort;
import com.viafoura.template.microservice.domain.model.StreamEvent;
import java.util.HashMap;
import org.junit.jupiter.api.*;

class ProcessEventServiceTest {

    private final ApplicationMetricsPort applicationMetricsPort = mock(ApplicationMetricsPort.class);
    private final MessagePublisherPort messagePublisherPort = mock(MessagePublisherPort.class);
    private final StreamEvent streamEvent = new StreamEvent(new HashMap<>());
    private ProcessEventService processEventService;

    @BeforeEach
    void setup() {
        processEventService = new ProcessEventService(applicationMetricsPort, messagePublisherPort);
    }

    @Test
    void givenStreamEvent_whenProcessEvent_thenSuccess() {
        processEventService.processEvent(streamEvent);
        verify(applicationMetricsPort, times(1)).incrementVerifiedEvents();
        verify(messagePublisherPort, times(1)).publishMessageToAllTopics(streamEvent);
        assertEquals(1, streamEvent.getValues().size());
        assertEquals(true, streamEvent.getValues().get("verified"));
    }

}