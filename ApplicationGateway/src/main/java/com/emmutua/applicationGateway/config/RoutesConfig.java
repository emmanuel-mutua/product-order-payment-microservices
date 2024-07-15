package com.emmutua.applicationGateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoutesConfig {
    @Bean
    public RouteLocator microserviceRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route( predicateSpec ->
                        predicateSpec.path("/product/**")
                                .uri("http://localhost:8085")
                )
                .route(
                        predicateSpec -> predicateSpec
                                .path("/order/**")
                                .uri("http://localhost:8081")
                )
                .route(
                        predicateSpec ->
                                predicateSpec.path("/payment/**")
                                        .uri("http://localhost:8082")
                )
                .build();
    }
}
