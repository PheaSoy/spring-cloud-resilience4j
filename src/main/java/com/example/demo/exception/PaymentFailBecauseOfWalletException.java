package com.example.demo.exception;

import com.example.demo.model.BaseResponse;

public class PaymentFailBecauseOfWalletException extends PaymentFailException {

    public PaymentFailBecauseOfWalletException() {
    }

    public PaymentFailBecauseOfWalletException(String message) {
        super(message);
    }

    public PaymentFailBecauseOfWalletException(BaseResponse baseResponse) {
        super(baseResponse);
    }
}
