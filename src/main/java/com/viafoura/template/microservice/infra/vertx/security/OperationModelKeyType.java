package com.viafoura.template.microservice.infra.vertx.security;

/**
 * Operation model key is how Vert.x {@link io.vertx.ext.web.api.contract.openapi3.OpenAPI3RouterFactory} passes the
 * operation information to the context
 */
public enum OperationModelKeyType {

    OPERATION_POJO("operationPOJO");

    private final String key;

    OperationModelKeyType(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
