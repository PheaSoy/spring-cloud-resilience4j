package com.example.demo.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.web.bind.annotation.GetMapping;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@SuperBuilder
public class Account{
    @NonNull
    private String accountNo;
    private boolean isDeleted;
    private String phoneNumber;
    private String name;
}
