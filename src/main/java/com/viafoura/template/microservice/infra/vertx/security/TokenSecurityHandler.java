package com.viafoura.template.microservice.infra.vertx.security;

import com.viafoura.common.service.auth.JwtAuthenticator;
import com.viafoura.common.service.auth.PermissionLevel;
import io.swagger.v3.oas.models.Operation;
import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import java.util.Comparator;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.AllArgsConstructor;

@Singleton
@AllArgsConstructor(onConstructor = @__({@Inject}))
public class TokenSecurityHandler implements Handler<RoutingContext> {

    private final JwtAuthenticator authenticator;

    @Override
    public void handle(RoutingContext routingContext) {
        Operation operation = routingContext.get(OperationModelKeyType.OPERATION_POJO.getKey());
        PermissionLevel permissionLevel = filterRequiredPermission(operation);
        authenticator.authenticate(routingContext, permissionLevel);
    }

    private PermissionLevel filterRequiredPermission(Operation operation) {
        Set<PermissionLevel> permissions = filterPermissionsByOperation(operation);
        return filterLeastRequiredPermission(permissions).orElse(PermissionLevel.OPTIONAL);
    }

    /**
     * @return permissions defined in the Open API specification for the specific operation
     */
    private static Set<PermissionLevel> filterPermissionsByOperation(Operation operation) {
        return operation
                .getSecurity()
                .stream()
                .filter(sr -> sr.containsKey(SecuritySchemaType.TOKEN.getName()))
                .map(sr -> sr.get(SecuritySchemaType.TOKEN.getName()))
                .findAny()
                .orElseThrow()
                .stream()
                .map(PermissionLevel::fromString)
                .collect(Collectors.toSet());
    }

    private static Optional<PermissionLevel> filterLeastRequiredPermission(Set<PermissionLevel> permissions) {
        return permissions.stream().max(Comparator.comparing(PermissionLevel::ordinal));
    }

    private static Optional<PermissionLevel> filterMostRequiredPermission(Set<PermissionLevel> permissions) {
        return permissions.stream().min(Comparator.comparing(PermissionLevel::ordinal));
    }

}
