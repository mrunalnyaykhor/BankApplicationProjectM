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
    @Column(name = "firstName", nullable = false, length = 20)
    private String firstName;
    @Column(name = "lastName", nullable = false, length = 20)
    private String lastName;
    @Column(name = "amount", nullable = false)
    private double amount;
    @Column(name = "blocked", nullable = false)
    private boolean blocked;
    @Column(name = "age", nullable = false)
    private int age;
    @Column(name = "aadhaarNum", nullable = false, length=12)
    private Double aadhaarNumber;
    @Column(name = "panCardNumber", nullable = false)
    private String panCardNumber;
    @Column(name = "dateOfBirth", nullable = false)
    private String dateOfBirth;
    @Column(name = "bankName", nullable = false, length = 11)
    private long accountNumber;
    @Column(name = "email", nullable = false, length = 20)
    private String email;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="bankId",referencedColumnName = "bankId")
    private Bank bank;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="customerId",referencedColumnName = "customerId")
    private Customer customer;



}
