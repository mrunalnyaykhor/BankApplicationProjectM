package com.bankmanagement.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class UserTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long transactionId;
    private double amount;
    private Long accountNumberFrom;
    private Long accountNumberTo;
    private  String ifscCode;
    private String name;
    private Date date;


    public UserTransaction(double amount, Long accountNumberFrom, Long accountNumberTo, String ifscCode, String name, Date date) {
    }
}
