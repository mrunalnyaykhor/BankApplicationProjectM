package com.bankmanagement.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;

@Setter
@Getter
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long customerId;
    @Column(name = "firstName")
    private String firstName;
    @Column(name = "lastName")
    private String lastName;
    @Column(name = "contactNumber")
    private Long contactNumber;
    @Column(name = "age")
    private int age;
    @Column(name = "email")
    private String email;
    @Column(name = "aadhaarNumber")
    private String aadhaarNumber;
    @Column(name = "panCardNumber")
    private String panCardNumber;
    @Column(name = "dateOfBirth")
    private String dateOfBirth;
    @Column(name = "address")
    private String address;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bankId", referencedColumnName = "bankId")
    private Bank bank;


}
