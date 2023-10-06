package com.bankmanagement.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
@Setter
@Getter
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long accountId;
    private String firstName;
    private String lastName;
    private double amount;
    private int age;
    private Double aadhaarNumber;
    private String panCardNumber;
    private String dateOfBirth;
    private String customerAddress;
    private long accountNumber;
    private String email;
    private Double balance;
    private String ifscCode;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="bankId",referencedColumnName = "bankId")
    private Bank bank;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="customerId",referencedColumnName = "customerId")
    private Customer customer;



}
