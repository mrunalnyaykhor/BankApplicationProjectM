package com.bankmanagement.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Setter
@Getter
public class TransactionDto {
    private long transactionId;
    private double transferAmount;
    private Long accountNumberFrom;
    private Long accountNumberTo;
    private  String ifscCode;
    private String name;
    private Date date;

}
