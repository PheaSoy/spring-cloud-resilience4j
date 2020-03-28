package com.example.demo.service;

import com.example.demo.model.http.request.FundTransferRequest;
import com.example.demo.model.http.response.AccountResponse;
import com.example.demo.model.http.response.BaseStatus;
import com.example.demo.model.http.response.FundTransferResponse;
import com.example.demo.model.http.response.WalletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class PaymentAccountService {

    private final AccountService accountService;
    private final WalletService walletService;

    public PaymentAccountService(AccountService accountService,WalletService walletService){
        this.accountService = accountService;
        this.walletService  = walletService;
    }

    public FundTransferResponse createPayment(FundTransferRequest transferFundRequest) {
        AccountResponse accounts =accountService.checkAccount(transferFundRequest);
        WalletResponse walletResponse = walletService.checkWallet(transferFundRequest);
        FundTransferResponse response = new FundTransferResponse();
        response.setMsg("S0001");
        response.setMsg("Success.");
        response.setBaseStatus(BaseStatus.SUCCESS);
        response.setAccounts(accounts);
        return response;
    }

}
