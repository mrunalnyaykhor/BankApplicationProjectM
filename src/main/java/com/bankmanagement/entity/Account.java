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
    @Column(name = "firstName")
    private String firstName;
    @Column(name = "lastName")
    private String lastName;
    @Column(name = "amount")
    private double amount;
    @Column(name = "contactNumber")
    private Long contactNumber;
    @Column(name = "blocked")
    private boolean isBlocked = false;
    @Column(name = "age")
    private int age;
    @Column(name = "aadhaarNum")
    private Long aadhaarNumber;
    @Column(name = "panCardNumber")
    private String panCardNumber;
    @Column(name = "dateOfBirth")
    private String dateOfBirth;
    @Column(name = "accountNumber")
    private long accountNumber;
    @Column(name = "email")
    private String email;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="bankId",referencedColumnName = "bankId")
    private Bank bank;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="customerId",referencedColumnName = "customerId")
    private Customer customer;



}
