package com.bankmanagement.entity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

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
    private boolean blocked;
    private int age;
    private Double aadhaarNumber;
    private String panCardNumber;
    private String dateOfBirth;
    private long accountNumber;
    private String email;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="bankId",referencedColumnName = "bankId")
    private Bank bank;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="customerId",referencedColumnName = "customerId")
    private Customer customer;



}
