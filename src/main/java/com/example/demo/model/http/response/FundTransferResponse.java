package com.example.demo.model.http.response;

import com.example.demo.model.BaseResponse;
import lombok.Data;

import java.time.LocalDate;

@Data
public class FundTransferResponse extends BaseResponse {

    String txnId;
    LocalDate txnDate;
    Double amount;
    Double fee;
    AccountResponse accounts;

}
