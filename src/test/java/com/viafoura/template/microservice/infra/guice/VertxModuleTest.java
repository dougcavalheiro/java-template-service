package com.viafoura.template.microservice.infra.guice;

import static org.junit.jupiter.api.Assertions.*;

import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import com.viafoura.common.vertx.context.extractor.HeadersContextExtractor;
import com.viafoura.common.vertx.context.extractor.OriginatingIpExtractor;
import com.viafoura.common.vertx.context.extractor.SyndicationContextExtractor;
import com.viafoura.common.vertx.context.extractor.UserContextExtractor;
import io.vertx.core.Vertx;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.*;

class VertxModuleTest extends ModuleTester {

    @Test
    void givenVertxModule_whenCreateInjector_thenInject() {
        assertNotNull(injector.getInstance(Vertx.class));

        TypeLiteral<Set<HeadersContextExtractor>> headersContextExtractorSet = new TypeLiteral<>() {};
        Key<Set<HeadersContextExtractor>> setKey = Key.get(headersContextExtractorSet);
        Set<HeadersContextExtractor> implementations = injector.getInstance(setKey);
        assertEquals(implementations.stream().map(HeadersContextExtractor::getClass).collect(Collectors.toSet()),
                Set.of(UserContextExtractor.class, SyndicationContextExtractor.class, OriginatingIpExtractor.class));
    }
}