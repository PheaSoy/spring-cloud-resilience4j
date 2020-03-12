package com.example.demo.service;

import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Service;

@Service

public class GreetingService {

    private final CircuitBreakerFactory circuitBreakerFactory;

    public GreetingService(CircuitBreakerFactory circuitBreakerFactory) {
        this.circuitBreakerFactory = circuitBreakerFactory;
    }

    public String greet(String name) {
        return circuitBreakerFactory.create("slow").run(() -> slow(name), throwable -> (fallback(throwable)));
    }

    public String slow(String name) {
        if (name.contains("x")) throw new RuntimeException("BAM!");
        try {
            if (name.contains("y")) Thread.sleep(5000);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return String.format("Hello %s, welcome to the test", name);
    }

    public String fallback(Throwable throwable) {
        return "Fallback message";
    }
}
