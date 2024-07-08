package com.emmutua.applicationGateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

public class RoutesConfig {
    @Bean
    public RouteLocator microserviceRoutes(RouteLocatorBuilder builder) {
        return builder.routes().build();
    }
}
