package com.viafoura.template.microservice.infra.vertx.security;

public enum SecuritySchemaType {

    TOKEN("Token"),
    TOKEN_IN_COOKIE("TokenInCookie");

    private final String name;

    SecuritySchemaType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
