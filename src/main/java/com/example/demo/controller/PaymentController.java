package com.example.demo.controller;

import com.example.demo.model.Account;
import com.example.demo.model.BaseResponse;
import com.example.demo.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {

    @Autowired
    UserAccountService userAccountService;

    @GetMapping("/orders")
    public Account greet(){
        String staticName = "yewin";
        return userAccountService.account(staticName).get();
    }

}
