package com.bankmanagement.dto;

import com.bankmanagement.enump.AccountType;
import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;



@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {
    @NotNull(message = "accountId is mandatory")
    private Long accountId;

    @NotNull(message = "amount is mandatory")
    @DecimalMin(value = "0.01", message = "Amount must be greater than zero")
    private double amount;
    @NotNull(message = "accountNumber should be mandatory")

    @NotNull(message = "isBlocked must not be null")

    private boolean isBlocked = false;
    @NotNull(message = "customerId should be mandatory")

    private Long customerId;
    @NotNull(message = "bankId should be mandatory")

    private Long bankId;

    @NotNull(message = "Account type must not be null")
   @Enumerated(EnumType.STRING)
    private AccountType accountType;


}
