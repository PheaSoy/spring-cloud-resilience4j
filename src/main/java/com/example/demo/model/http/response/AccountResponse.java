package com.example.demo.model.http.response;

import com.example.demo.model.Account;
import com.example.demo.model.BaseResponse;
import lombok.Data;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@Data
public class AccountResponse extends BaseResponse {
    Account toAccount;
    Account fromAccount;


}
