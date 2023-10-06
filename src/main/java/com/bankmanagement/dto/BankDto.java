package com.bankmanagement.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Setter
@Getter
public class BankDto {

    private Long bankId;
    @NotBlank(message = "Bank Name should not be blank")
    @Size(min=2, message="Name should have at least 2 characters")
    private String bankName;
    @NotBlank(message = "BranchName should not be blank")
    @Size(min=2, message="Name should have at least 2 characters")
    private String branchName;
    @NotBlank(message = "ifscCode should not be blank")
    @Size(min=4, message="IFSC Code should have at least 2 characters")
    private String ifscCode;
    @NotBlank(message = "Address should not be null")
    @Size(min=2, message="Address should have at least 2 characters")
    private String address;
}
