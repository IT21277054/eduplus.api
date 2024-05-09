package com.ds.assignment.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouteValidator {
    public static final List<String> openApiEndpoints = List.of(
            "/api/auth/register",
            "/api/auth/token",
            "/eureka",
            "/api/notification/generate",
            "/api/notification/verify",
            "/api/notification/validateEmail",
            "/api/course/images/6cf29422-88db-4ff6-8763-6738a268e84d.jpg "
    );

    public Predicate<ServerHttpRequest> isSecured =
            request -> openApiEndpoints
                    .stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));
}
