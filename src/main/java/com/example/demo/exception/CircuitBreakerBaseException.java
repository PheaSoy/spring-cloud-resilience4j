package com.example.demo.exception;

import java.util.concurrent.TimeoutException;

public class CircuitBreakerBaseException extends Exception{

    public CircuitBreakerBaseException() {
    }

    public CircuitBreakerBaseException(String message) {
        super(message);
    }
}
