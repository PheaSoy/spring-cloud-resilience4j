package com.example.demo.model.http.request;

import com.example.demo.model.http.response.AccountResponse;
import lombok.Data;

@Data
public class FundTransferRequest {
    String amount;
    String currency;
    AccountResponse accounts;
    String transactionType;
}
