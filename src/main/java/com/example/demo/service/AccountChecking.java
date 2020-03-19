package com.example.demo.service;

import com.example.demo.model.Account;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@Log4j2
public class AccountChecking {


    @Value("${externalService.url}")
    private String externalUrl;

    @Autowired
    RestTemplate restTemplate;

    public Optional<Account> checkAccount(String name) {
        log.info("Connecting to account service.");
        Optional<Account> account = Optional.empty();
        try {
            account = Optional.ofNullable(restTemplate.getForEntity(externalUrl + "/accounts", Account.class).getBody());
        } catch (Exception ex) {
            throw new RuntimeException("BAM!");
        }
        return account;
    }
}
