package com.viafoura.template.microservice.adapter.output.metrics;

import com.viafoura.common.vertx.kafka.KafkaStreamMonitorMetrics;
import com.viafoura.template.microservice.application.port.output.metric.ApplicationMetricsPort;
import com.viafoura.template.microservice.infrastructure.metric.MetricNames;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

@Singleton
@Slf4j
public class ServiceMetricsAdapter implements ApplicationMetricsPort, KafkaStreamMonitorMetrics {

    private final MeterRegistry meterRegistry;
    private final Counter countHealthyCalls;
    private final Counter countVerifiedEvents;
    private final Counter kafkaStreamFailures;
    private final Counter kafkaStreamCreations;
    private final Counter kafkaStreamHeartbeats;
    @Inject
    public ServiceMetricsAdapter(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
        countHealthyCalls = buildCounter(MetricNames.HEALTH_CHECK_COUNT, "Number of healthy endpoint calls");
        countVerifiedEvents = buildCounter(MetricNames.VERIFIED_EVENT_COUNT, "Number of verified event calls");
        this.kafkaStreamCreations = buildCounter(MetricNames.KAFKA_STREAM_CREATIONS,
                "Total number of times the Kafka stream has been created");
        this.kafkaStreamHeartbeats =
                buildCounter(MetricNames.KAFKA_STREAM_HEARTBEATS,
                        "Total number of heartbeats received from Kafka");
        this.kafkaStreamFailures = buildCounter(MetricNames.KAFKA_STREAM_FAILURES,
                "Total number of times the Kafka stream failed to be created");
    }

    @Override
    public void incrementHealthyCalls() {
        incrementAndLog(countHealthyCalls);
    }

    @Override
    public void incrementVerifiedEvents() {
        incrementAndLog(countVerifiedEvents);
    }

    @Override
    public void kafkaStreamFailure(Object... objects) {
        incrementAndLog(kafkaStreamFailures);
    }

    @Override
    public void kafkaStreamCreate(Object... objects) {
        incrementAndLog(kafkaStreamCreations);
    }

    @Override
    public void kafkaStreamHeartbeat(Object... objects) {
        kafkaStreamHeartbeats.increment();
    }

    private Counter buildCounter(String name, String description) {
        return Counter.builder(name).description(description).register(meterRegistry);
    }

    private void incrementAndLog(Counter metric) {
        metric.increment();
        log.debug("{}: {}", metric.getId().getDescription(), metric.count());
    }
}
