package com.bankmanagement.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Setter
@Getter
public class UserTransactionDto {
    private long transactionId;
    private double amount;
    private Long accountNumberFrom;
    private Long accountNumberTo;
    private  String ifscCode;
    private String name;
    private Date date;

}
