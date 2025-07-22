package com.emmutua.orderservice.config;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4jBulkheadConfigurationBuilder;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4jBulkheadProvider;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

import static io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.SlidingWindowType.COUNT_BASED;

@Configuration
public class CircuitBreaker {

    @Bean
    public Customizer<Resilience4JCircuitBreakerFactory> globalCustomConfiguration() {
        return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
                .timeLimiterConfig(timeLimiterConfig())
                .circuitBreakerConfig(circuitBreakerConfig())
                .build()
        );
    }

    /**
     * Bulkhead helps limit number of concurrent executions
     * @return
     */
//    @Bean
//    public Customizer<Resilience4jBulkheadProvider> defaultBulkheadCustomizer() {
//        return provider -> provider.configureDefault(id -> new Resilience4jBulkheadConfigurationBuilder()
//                .bulkheadConfig(BulkheadConfig.custom().maxConcurrentCalls(4).build())
//                .threadPoolBulkheadConfig(ThreadPoolBulkheadConfig.custom().coreThreadPoolSize(1).maxThreadPoolSize(1).build())
//                .build()
//        );
//    }
    /**
     * The config below shows that if 50 % of the last 10 calls are failure or timeout is @slowCallDurationThreshold = 500, the CB will transition from CLOSED TO OPEN
     *  After 1000 ms in OPEN state it can transition to HALF_OPEN and permit 5 calls, if these calls are = > then it goes to OPEN otherwise CLOSED
     *  We have something called BULKHEAD it is used to isolate services to limit failure -> you can add the number of concurrent calls
     */

    @Bean
    public CircuitBreakerConfig circuitBreakerConfig(){
        return CircuitBreakerConfig.custom()
                .failureRateThreshold(50) //the percentage in which the cb should go to open state
                .waitDurationInOpenState(Duration.ofMillis(1000))
                .slowCallDurationThreshold(Duration.ofMillis(500))
                .minimumNumberOfCalls(5)
                .slidingWindowSize(10) //configure the size of sliding window used to measure the outcome of calls when the cb is closed
                .slidingWindowType(COUNT_BASED) //what does it mean ??
                .build();
    }

    @Bean
    public TimeLimiterConfig timeLimiterConfig(){
        return TimeLimiterConfig.custom()
                .timeoutDuration(Duration.ofSeconds(5))
                .build();
    }


}
