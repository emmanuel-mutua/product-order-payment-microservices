# MicroServices (MonoRepo)

**ApiGateway**
- Entry point of incoming requests
- Dependencies: 
1. Gateway - To route APIS, provide security, monitoring/metrics, resiliency (Acts as a traffic cop)
2. Eureka Client - Enable it to read data from the DiscoveryClient
3. Config client - Read from config server
4. Actuator - For application metrics


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

**ZIPKIN**

- For distributed tracing
- Run the latest image`docker run -d -p 9411:9411 openzipkin/zipkin`