package com.bankmanagement.dto;

import com.bankmanagement.enump.AccountType;
import lombok.*;

import javax.validation.constraints.NotNull;


@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {


private Long accountId;
    @NotNull(message = "amount should be mandatory")
    private double amount;
    @NotNull(message = "accountNumber should be mandatory")

    private long accountNumber; //Randomly generate account no.
    private boolean isBlocked = false;

    private Long customerId;

    private Long bankId;
    private AccountType accountType ;









}
