package com.bankmanagement.dto;

import com.bankmanagement.entity.Account;
import com.bankmanagement.entity.Bank;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import java.util.Date;
@Setter
@Getter
public class AccountDto {
    private Long accountId;
    private String firstName;
    private String middleName;
    private String lastName;
    private Long contactNumber;
    private double amount;
    private int age;
    private double customerSalary;
    private String aadhaarNumber;
    private String panCardNumber;
    private String dateOfBirth;
    private String customerAddress;
    private long customerAccountNumber;
    private String email;
    private Double balance;
    @OneToMany(cascade = CascadeType.ALL)
    private Bank bank;



}
