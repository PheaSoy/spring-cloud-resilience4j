package com.example.demo.exception;

public class AccountInvalid extends BusinessException{
    public AccountInvalid() {
    }

    public AccountInvalid(String message) {
        super(message);
    }
}
