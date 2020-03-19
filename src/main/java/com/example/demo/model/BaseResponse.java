package com.example.demo.model;

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
}
