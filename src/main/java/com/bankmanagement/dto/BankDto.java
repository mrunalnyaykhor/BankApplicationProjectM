package com.bankmanagement.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BankDto {
    private Long bankId;
    @NotBlank(message = "Bank Name should not be blank")
    private String bankName;
    @NotBlank(message = "BranchName should not be blank")
    private String branchName;
    @Pattern(regexp = "^[A-Z]{4}[0][A-Z0-9]{6}$",message = "ifsc Code proper format")
    @NotBlank(message = "ifscCode should not be blank")
    private String ifscCode;
    @NotBlank(message = "Address should not be null")
    private String address;

    public BankDto(long l, String sbi, String sbiMohadi, String sbin1234, String mohadi) {
    }
}
