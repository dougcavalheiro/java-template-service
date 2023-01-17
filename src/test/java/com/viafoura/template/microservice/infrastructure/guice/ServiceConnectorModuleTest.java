package com.viafoura.template.microservice.infrastructure.guice;

import static org.junit.jupiter.api.Assertions.*;

import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import com.viafoura.common.vertx.ServiceConnector;
import com.viafoura.template.microservice.infrastructure.vertx.verticle.HealthCheckVerticle;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.*;

class ServiceConnectorModuleTest extends ModuleTester {

    @Test
    void givenServiceConnectorModule_whenCreateInjector_thenInject() {
        TypeLiteral<Set<ServiceConnector>> headersContextExtractorSet = new TypeLiteral<>() {};
        Key<Set<ServiceConnector>> setKey = Key.get(headersContextExtractorSet);
        Set<ServiceConnector> implementations = injector.getInstance(setKey);
        assertEquals(implementations.stream().map(ServiceConnector::getClass).collect(Collectors.toSet()),
                Set.of(HealthCheckVerticle.class));
    }

}