# MicroServices (MonoRepo)

**ApiGateway**
- Entry point of incoming requests (ROUTING)
- TODO(Impl Security)
- Traffic control - loadbalancing ,ratelimiting, caching
- Observability (Monitoring, logging)

### Circuit breaker
- In-case of potentially-failing method calls - circuit breaker can be applied when a certain threshold is reached
- [Learn more](https://martinfowler.com/bliki/CircuitBreaker.html)
- [resilience4j doc](https://resilience4j.readme.io/docs/circuitbreaker) - (Circuit breaker, rate limiting, retry mechanism, bulk-heads)
- [medium](https://nirajtechi.medium.com/circuit-breaker-in-microservices-and-spring-boot-example-4ad76c7a33e6)
- The goal is to improve fault tolerance -> Make the system handle failures/ continue operating despite of failures
- The main goal is to handle faults when they occur when calling remote microservices
- The purpose is to prevent cascade of failures by providing a fallback mechanism whn services are down or experiencing issues
- Cascading failures - failure of one service leads to failure of other service 

- Orchestration - service disc, failure handling
- Dependencies: 
1. Gateway - To route APIS, provide security, monitoring/metrics, resiliency (Acts as a traffic cop)
2. Eureka Client - Enable it to read data from the DiscoveryClient
3. Config client - Read from config server
4. Actuator - For application metrics (exposes operational information about the running application)


**ConfigServer**

- Dependencies: Config Server
- Centralize all configurations here

**DiscoveryServer (Eureka) - service-registry**

- Each new microService must be registered to the discovery server
- Dependencies include: 
1. Eureka Server
2. Config Client
3. SpringActuator(For metrics)
- ApiGateway uses discovery server to determine the running services

**Business Services**
1. ProductService
2. OrderService
3. PaymentService (Paypal)

**ZIPKIN**

- For distributed tracing
- Run the latest image`docker run -d -p 9411:9411 openzipkin/zipkin`