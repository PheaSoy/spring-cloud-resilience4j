package com.example.demo.model;

import com.example.demo.model.http.response.BaseStatus;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class BaseResponse {

    private String msgCode;
    private String msg;
    BaseStatus baseStatus;
}
