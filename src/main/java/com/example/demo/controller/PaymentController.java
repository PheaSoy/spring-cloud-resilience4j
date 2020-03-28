package com.example.demo.controller;

import com.example.demo.model.Account;
import com.example.demo.model.http.request.FundTransferRequest;
import com.example.demo.model.http.response.FundTransferResponse;
import com.example.demo.service.PaymentAccountService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
public class PaymentController {

    @Autowired
    PaymentAccountService paymentAccountService;

    @PostMapping("/orders")
    public FundTransferResponse greet(@RequestBody FundTransferRequest transferFundRequest){
        log.info("Accept account transfer request with body:{}",transferFundRequest);
        return paymentAccountService.createPayment(transferFundRequest);
    }

}
