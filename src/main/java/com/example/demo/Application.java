package com.example.demo;

import com.example.demo.exception.BusinessException;
import com.example.demo.exception.CircuitBreakerBaseException;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreaker;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.sql.Time;
import java.time.Duration;
import java.util.concurrent.TimeoutException;
import java.util.function.Function;


@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    CircuitBreakerFactory circuitBreakerFactory() {
        Resilience4JCircuitBreakerFactory factory = new Resilience4JCircuitBreakerFactory();
        factory
                .configureDefault(s -> new Resilience4JConfigBuilder(s)
                        .timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofMillis(300)).build())
                        .circuitBreakerConfig(CircuitBreakerConfig.ofDefaults())
                        .circuitBreakerConfig(
                                CircuitBreakerConfig.custom().slidingWindowSize(10)
                                        .waitDurationInOpenState(Duration.ofMillis(300))
                                        .permittedNumberOfCallsInHalfOpenState(5)
                                        .recordExceptions(TimeoutException.class)
                                        .recordException(throwable -> throwable instanceof CircuitBreakerBaseException)
                                        .ignoreException(throwable -> throwable instanceof BusinessException)
                                        .build())
                        .build());
        return factory;
    }
}


