package com.viafoura.template.microservice.infra.metric;

public class MetricNames {

    private MetricNames() { }

    public static final String HEALTH_CHECK_COUNT = "HealthCheckCount";
    public static final String VERIFIED_EVENT_COUNT = "VerifiedEventCount";
    public static final String KAFKA_STREAM_CREATIONS = "kafka.stream.creations";
    public static final String KAFKA_STREAM_HEARTBEATS = "kafka.stream.heartbeats";
    public static final String KAFKA_STREAM_FAILURES = "kafka.stream.failures";
}
