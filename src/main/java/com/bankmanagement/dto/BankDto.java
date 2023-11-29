package com.bankmanagement.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BankDto {
    @NotNull(message = "bank Id is mandatory")
    private Long bankId;
    @NotBlank(message = "Bank Name is mandatory")
    private String bankName;
    @NotBlank(message = "BranchName is mandatory")
    private String branchName;
    @Pattern(regexp = "^[A-Z]{4}[0][A-Z0-9]{6}$", message = "ifsc Code should be 11 digit")
    @NotBlank(message = "ifscCode is mandatory")
    private String ifscCode;
    @NotBlank(message = "Address is mandatory")
    private String address;


}
