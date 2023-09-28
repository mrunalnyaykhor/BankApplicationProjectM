package com.bankmanagement.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Setter
@Getter
public class UserTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long transactionId;
    private double amount;
    private long accountNumberFrom;
    private long accountNumberTo;
    private  String ifscCode;
    private String name;
    private Date date;

}
