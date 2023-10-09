package com.bankmanagement.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long customerId;
    private String firstName;
    private String lastName;
    private Long contactNumber;
    private int age;
    private String email;
    private String aadhaarNumber;
    private String panCardNumber;
    private String dateOfBirth;
    private String address;
    @NotNull
    private long accountNumber;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bankId", referencedColumnName = "bankId")
    private Bank bank;


}
