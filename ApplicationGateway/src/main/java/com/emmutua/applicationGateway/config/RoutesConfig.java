package com.emmutua.applicationGateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Configuration
public class RoutesConfig {
    @Bean
    public RouteLocator microserviceRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(predicateSpec ->
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
                                predicateSpec
                                        .path("/payment/**")
                                        .filters(f -> f.circuitBreaker(
                                                config -> config
                                                        .setName("my_cmd")
                                                        .setFallbackUri("forward:/fallback")
                                        ).filter(
                                                ((exchange, chain) -> {
                                                    ServerHttpRequest request = exchange.getRequest().mutate()
                                                            .header("x-failed-url", exchange.getRequest().getURI().toString())
                                                            .build();
                                                    return chain.filter(exchange.mutate().request(request).build());

                                                })
                                        ))
                                        .uri("http://localhost:8082")
                )
                .build();
    }
}
