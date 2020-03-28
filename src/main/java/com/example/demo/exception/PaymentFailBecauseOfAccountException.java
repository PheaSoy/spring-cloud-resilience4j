package com.example.demo.exception;

import com.example.demo.model.BaseResponse;

public class PaymentFailBecauseOfAccountException extends PaymentFailException {

    public PaymentFailBecauseOfAccountException() {
    }

    public PaymentFailBecauseOfAccountException(String message) {
        super(message);
    }

    public PaymentFailBecauseOfAccountException(BaseResponse baseResponse) {
        super(baseResponse);
    }
}
