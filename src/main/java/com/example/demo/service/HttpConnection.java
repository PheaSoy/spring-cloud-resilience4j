package com.example.demo.service;

import com.example.demo.model.http.request.FundTransferRequest;
import com.example.demo.model.http.response.AccountResponse;
import com.example.demo.model.http.response.BaseStatus;
import com.example.demo.model.http.response.WalletResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class HttpConnection {

    private final RestTemplate restTemplate;
    private final CircuitBreaker circuitBreakerSlow;

    @Value("${account-service.url}")
    private String accountServiceUrl;

    @Value("${wallet-service.url}")
    private String walletServiceUrl;

    public HttpConnection(RestTemplate restTemplate, CircuitBreakerFactory circuitBreakerFactory) {
        this.restTemplate = restTemplate;
        this.circuitBreakerSlow = circuitBreakerFactory.create("slow");
    }

    public AccountResponse connect_To_AccountService(FundTransferRequest fundTransferRequest) {
        List<String> listAccountsNo = Arrays.asList(fundTransferRequest.getAccounts().getFromAccount().getAccountNo(),
                fundTransferRequest.getAccounts().getToAccount().getAccountNo());
        return circuitBreakerSlow.run(() -> postHttpToAccount(listAccountsNo), throwable -> accountFallBack(throwable));
    }

    public WalletResponse connect_To_WalletService(FundTransferRequest fundTransferRequest) {
        return circuitBreakerSlow.run(() -> postToWalletService(fundTransferRequest), throwable -> walletFallBack(throwable));
    }

    public AccountResponse postHttpToAccount(List<String> listAccounts) {

        AccountResponse accounts = null;
        log.info("Connect to: {}", accountServiceUrl);

        try {
            String payload = new ObjectMapper().writeValueAsString(listAccounts);
            ResponseEntity<AccountResponse> accountResponse = restTemplate.postForEntity(accountServiceUrl + "/accounts/", payload, AccountResponse.class);
            accounts = accountResponse.getBody();
        } catch (JsonProcessingException ex) {
        } catch (Exception ex) {
            throw ex;
        }
        log.info("Account list responses:{}", accounts);
        return accounts;
    }


    public WalletResponse postToWalletService(FundTransferRequest fundTransferRequest) {

        WalletResponse walletResponse = null;
        log.info("Connect to: {}", walletServiceUrl);

        try {
            String payload = new ObjectMapper().writeValueAsString(fundTransferRequest);
            ResponseEntity<WalletResponse> accountResponse = restTemplate.postForEntity(walletServiceUrl + "/wallets/transactions", payload, WalletResponse.class);
            walletResponse = accountResponse.getBody();
        } catch (JsonProcessingException ex) {
        } catch (Exception ex) {
            throw ex;
        }
        log.info("Account list responses:{}", walletResponse);
        return walletResponse;
    }

    public AccountResponse accountFallBack(Throwable throwable) {
        AccountResponse accounts = new AccountResponse();
        accounts.setBaseStatus(BaseStatus.FAIL);
        accounts.setMsgCode("103");
        accounts.setMsg("Account service unavailable");
        return accounts;
    }

    public WalletResponse walletFallBack(Throwable throwable) {
        WalletResponse walletResponse = new WalletResponse();
        walletResponse.setBaseStatus(BaseStatus.FAIL);
        walletResponse.setMsgCode("105");
        walletResponse.setMsg("Wallet service unavailable");
        return walletResponse;
    }

}
