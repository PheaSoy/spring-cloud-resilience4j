package com.example.demo.exception;

import com.example.demo.model.BaseResponse;

public class PaymentFailException extends BusinessException {

    private BaseResponse baseResponse;

    public PaymentFailException() {
        super();
    }

    public PaymentFailException(String message) {
        super(message);
    }

    public PaymentFailException(BaseResponse baseResponse) {
    this.baseResponse = baseResponse;
    }
}
