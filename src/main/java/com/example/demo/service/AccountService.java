package com.example.demo.service;

import com.example.demo.exception.AccountInvalid;
import com.example.demo.exception.PaymentFailBecauseOfAccountException;
import com.example.demo.exception.PaymentFailBecauseOfWalletException;
import com.example.demo.exception.PaymentFailException;
import com.example.demo.model.http.request.FundTransferRequest;
import com.example.demo.model.http.response.AccountResponse;
import com.example.demo.model.http.response.BaseStatus;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.function.Predicate;

@Service
@Log4j2
public class AccountService {

    private final HttpConnection httpConnection;

    public AccountService(HttpConnection httpConnection) {
        this.httpConnection = httpConnection;
    }

    public AccountResponse checkAccount(FundTransferRequest fundTransferRequest) {
        AccountResponse accounts = httpConnection.connect_To_AccountService(fundTransferRequest);
        if(accounts.getBaseStatus().equals(BaseStatus.FAIL)){
            throw new PaymentFailBecauseOfAccountException(accounts);
        }
        if (this.isAccountsValid().test(accounts)) throw  new AccountInvalid();
        return accounts;

    }

    public Predicate isAccountsValid() {
        Predicate<AccountResponse> fromAccCheckNotDeleted = listAccount -> listAccount.getFromAccount().isDeleted() == false;
        Predicate<AccountResponse> toAccCheckNotDeleted = listAccount -> listAccount.getToAccount().isDeleted() == false;
        Predicate<AccountResponse> toAccPhoneValid = listAccount -> !listAccount.getToAccount().getPhoneNumber().isEmpty();
       return fromAccCheckNotDeleted.and(toAccCheckNotDeleted).and(toAccPhoneValid).negate();
    }

}
