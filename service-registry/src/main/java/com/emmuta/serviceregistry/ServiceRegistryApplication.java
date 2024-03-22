package com.emmuta.serviceregistry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer //enable this application act as a service registry, enable applications, services talk smooth without really having to master the ip or the port nums
public class ServiceRegistryApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceRegistryApplication.class, args);
	}

}

/**
 * Dealing with microservices having the same configs
 * you can store those configs in github and then create a config server that wll provide configs to diffrent microservices
 *
 */