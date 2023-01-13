package com.viafoura.template.microservice.application.port.out.metrics;

public interface ApplicationMetricsPort {

    void incrementHealthyCalls();
    void incrementVerifiedEvents();

}
