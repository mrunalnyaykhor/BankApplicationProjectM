package com.bankmanagement.dto;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Date;

@Setter
@Getter
public class TransactionDto {
    private long transactionId;
    @NotNull
    private double amount;
    @NotNull
    private Long accountNumberFrom;
    @NotNull
    private Long accountNumberTo;
    private LocalDate transactionDate;
    @NotBlank(message = "Description is required")
    private String description;
    @NotBlank(message = "ifsc code is mandatory")
    private  String ifscCode;
    @NotBlank(message = "name is mandatory")
    private String name;

}
