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
 *
 * We can impl communication between microservices using kafka as the message broker
 * Need to integrate keyclock - identity and access management tool (authenticate users and auth)
 *
 * KEYCLOCK FEATURES
 * - Single Sign-On and Single Logout - sign in once
 * - Identity brockering and social logging- g,fb
 * - User Federation - intergrates with existing user db
 * - FineGrained Auth Services
 *
 * Realm - a way to isolate users (with credentials , roles, pemm)
 * Client scopes- default roles
 * Groups - way to manage common roles, pemm
 */

