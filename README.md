# MicroServices (MonoRepo)

## Design
![image](images/ER.drawio.png)

## Architecture
![Design_MS](images/ARCHITECTURE.png)

## Overview

## Advantages of Microservices Architecture

1. **Enhanced Scalability**
    - **Horizontal Scaling**: Easily scale out by adding more instances of a service to handle increased load. This approach ensures that the system can handle high traffic volumes and maintain availability.
    - **Vertical Scaling**: Improve the performance of a service by adding more resources (such as CPU, memory) to existing instances. This method is beneficial for optimizing resource-intensive services.

2. **Independent Deployment**
    - Microservices can be developed, tested, and deployed independently. This independence allows teams to deploy new features or updates without affecting other services, enabling continuous integration and continuous delivery (CI/CD).

3. **Improved Fault Tolerance**
    - **Isolation**: Each service operates independently, so a failure in one service is less likely to impact the overall system. This isolation helps prevent cascading failures.
    - **Circuit Breakers**: Implementing circuit breakers helps to prevent failures from spreading by providing fallback mechanisms and preventing repeated attempts to access a failing service.
    - **Health Checks**: Regular health checks ensure that services are functioning properly and can automatically remove or restart unhealthy instances, maintaining system stability.

4. **Technological Flexibility**
    - Microservices architecture allows the use of different technologies, programming languages, and frameworks for each service. This flexibility enables teams to choose the best tool for each specific task and incorporate new technologies without a complete system overhaul.

5. **Faster Development of Features**
    - The independent nature of microservices allows teams to work on different services simultaneously, speeding up the development process. This structure supports agile methodologies and rapid iteration, enabling faster delivery of new features and improvements.

The system is designed to provide scalable, secure, and resilient services by leveraging microservices architecture. Key components include:

- **API Gateway**: The single entry point for incoming requests, handling routing, security, traffic control (load balancing, rate limiting, caching), and observability (monitoring and logging).
- **Config Server**: Centralized configuration management for all microservices.
- **Discovery Server (Eureka)**: Service registry for dynamic discovery of microservices.
- **Business Services**: Core services including ProductService, OrderService, and PaymentService.
- **Zipkin**: Distributed tracing for monitoring and troubleshooting.
- **Message Broker**: Handles communication in a distributed environment (e.g., Kafka).

## Components

### API Gateway

The API Gateway acts as a traffic cop, managing all incoming requests and routing them to the appropriate microservices. Key functionalities include:

- **Routing**: Directing API calls to the correct microservice.
- **Security**: Implementing authentication and authorization.
- **Traffic Control**: Load balancing, rate limiting, and caching.
- **Observability**: Monitoring and logging for operational insights.

#### Circuit Breaker

To enhance fault tolerance, we use a circuit breaker pattern that prevents cascading failures by providing fallback mechanisms when services are down or experiencing issues.

- **Goal**: Handle failures gracefully and maintain system stability.
- **Dependencies**:
    1. **Gateway**: For routing, security, monitoring, metrics, and resiliency.
    2. **Eureka Client**: Reads data from the Discovery Client.
    3. **Config Client**: Fetches configuration from the Config Server.
    4. **Actuator**: Provides application metrics and operational information.

For more details, refer to the following resources:
- [Martin Fowler on Circuit Breaker](https://martinfowler.com/bliki/CircuitBreaker.html)
- [Resilience4j Documentation](https://resilience4j.readme.io/docs/circuitbreaker)
- [Medium Article on Circuit Breaker in Microservices](https://nirajtechi.medium.com/circuit-breaker-in-microservices-and-spring-boot-example-4ad76c7a33e6)

### Config Server

The Config Server centralizes configuration management for all microservices, ensuring consistency and ease of management.

- **Dependencies**:
    - Config Server
    - Config Client
    - Spring Actuator for metrics

### Discovery Server (Eureka)

The Eureka Discovery Server acts as a service registry where each microservice registers itself. This enables the API Gateway and other services to discover available services dynamically.

- **Dependencies**:
    - Eureka Server
    - Config Client
    - Spring Actuator for metrics

### Business Services

Our core business services include:

1. **ProductService**
2. **OrderService**
3. **PaymentService (Paypal)**

These services handle the business logic and are accessible through the API Gateway.

### Zipkin

Zipkin is used for distributed tracing, providing visibility into the flow of requests across microservices. It helps in monitoring and troubleshooting issues.

- **Setup**: `docker run -d -p 9411:9411 openzipkin/zipkin`

### Message Broker

A message broker, such as Kafka, is used for validating, storing, routing, and delivering messages to appropriate destinations. It facilitates communication in a distributed environment.

## Conclusion

This microservices architecture ensures a scalable, secure, and resilient system, capable of handling high traffic and maintaining operational integrity in the face of failures.
