package com.bankmanagement.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
    @NotNull(message = "Aadhaar Number should be 12 digit")
    @Digits(message="AadhaarNumber should contain 12 digits.", fraction = 0, integer = 12)

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
    @Column(insertable =  false, updatable = false)
    private Long bankId;



}
