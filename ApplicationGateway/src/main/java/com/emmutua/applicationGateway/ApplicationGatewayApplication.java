package com.emmutua.applicationGateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApplicationGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApplicationGatewayApplication.class, args);
	}

}


/**
 * Predicates -> condition to determine whether to execute or not
 * Filters -> condition that determine whether to modify / reprocess request or a response
 * Fault Tolerance -> ability of a system to continue operating despite of a part of the system stopping / failing
 * Resilience4J - fault tolerance (Circuit Breaker, Rate Limiter, Retry or Bulkhead.)
 * Contract Stub Runner
 */

