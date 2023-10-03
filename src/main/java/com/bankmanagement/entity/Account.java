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
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="bankId",referencedColumnName = "bankId")
    private Bank bank;

}
