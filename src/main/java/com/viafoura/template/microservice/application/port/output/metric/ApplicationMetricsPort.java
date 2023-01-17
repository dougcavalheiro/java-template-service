package com.viafoura.template.microservice.application.port.output.metric;

public interface ApplicationMetricsPort {

    void incrementHealthyCalls();
    void incrementVerifiedEvents();

}
