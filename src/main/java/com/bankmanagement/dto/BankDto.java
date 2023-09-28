package com.bankmanagement.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Setter
@Getter
public class BankDto {

    private Long bankId;
    private String bankName;
    private String branchName;
    private String ifscCode;
    private String address;
}
