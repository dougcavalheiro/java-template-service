package com.viafoura.template.microservice.infrastructure.vertx.event;

public enum EventBusType {

    HEALTHY("healthy", "service.healthy");

    private final String operationId;
    private final String address;

    EventBusType(String operationId, String address) {
        this.operationId = operationId;
        this.address = address;
    }

    public String getOperationId() {
        return operationId;
    }

    public String getAddress() {
        return address;
    }
}
