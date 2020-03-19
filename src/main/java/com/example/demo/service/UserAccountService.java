package com.example.demo.service;

import com.example.demo.model.Account;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Log4j2
public class UserAccountService {

    @Autowired
    private AccountChecking accountChecking;

   // private final CircuitBreaker circuitBreakerSlow;

    private final CircuitBreakerFactory circuitBreakerFactory;

    public UserAccountService(CircuitBreakerFactory circuitBreakerFactory) {
        this.circuitBreakerFactory = circuitBreakerFactory;
       // this.circuitBreakerSlow = circuitBreakerFactory.create("slow");
    }

    public Optional<Account> account(String name) {
        return circuitBreakerFactory.create("slow-call").run(() -> accountChecking.checkAccount(name), this::fallback);
    }

    public Optional<Account> fallback(Throwable throwable) {
        log.error(throwable.getMessage());
        log.info("Execute a fallback function.");
        Optional<String> a = Optional.empty();
        return Optional.ofNullable(Account.builder().msg("Service Unavailable").msgCode("103").build());
    }

}
