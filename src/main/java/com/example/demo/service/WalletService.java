package com.example.demo.service;

import com.example.demo.exception.PaymentFailBecauseOfWalletException;
import com.example.demo.model.http.request.FundTransferRequest;
import com.example.demo.model.http.response.BaseStatus;
import com.example.demo.model.http.response.WalletResponse;
import org.springframework.stereotype.Service;

@Service
public class WalletService {

    private final HttpConnection httpConnection;

    public WalletService(HttpConnection httpConnection) {
        this.httpConnection = httpConnection;
    }


    public WalletResponse checkWallet(FundTransferRequest fundTransferRequest){
        WalletResponse walletResponse = httpConnection.connect_To_WalletService(fundTransferRequest);
        if(walletResponse.getBaseStatus().equals(BaseStatus.FAIL)){
            throw  new PaymentFailBecauseOfWalletException(walletResponse);
        }
        return walletResponse;
    }
}
