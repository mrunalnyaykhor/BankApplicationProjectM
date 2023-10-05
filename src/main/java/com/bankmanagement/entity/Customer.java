package com.bankmanagement.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
    @NotNull
    private String panCardNumber;
    @NotNull
    private String dateOfBirth;
    @NotNull
    private String address;
    @NotNull
    private long accountNumber;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="bankId",referencedColumnName = "bankId")
    private  Bank bank;


}
