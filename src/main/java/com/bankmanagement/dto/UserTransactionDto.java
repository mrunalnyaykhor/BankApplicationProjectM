package com.bankmanagement.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Setter
@Getter
public class UserTransactionDto {
    private long transactionId;
    private double amount;
    private long accountNumberFrom;
    private long accountNumberTo;
    private  String ifscCode;
    private String name;
    private Date date;

}
