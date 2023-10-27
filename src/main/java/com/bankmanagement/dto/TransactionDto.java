package com.bankmanagement.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto {
    @NotNull
    private double amount;
    @NotNull(message = "accountNumberFrom is Mandatory")
    private Long accountNumberFrom;
    @NotNull(message = "accountNumberTo Is Mandatory")

    private Long accountNumberTo;

     private LocalDate transactionDate;
     @NotBlank(message = "Description is required")
    private String description;

    @NotBlank(message = "ifsc code is mandatory")
    private String ifscCode;
    @NotBlank(message = "name is mandatory")
    private String name;

}
