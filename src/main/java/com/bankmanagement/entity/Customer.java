package com.bankmanagement.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
@Setter
@Getter
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long customerId;
    private String firstName;
    private String middleName;
    private String lastName;
    private Long contactNumber;
    private int age;
    private String email;
    private double customerSalary;
    private String aadhaarNumber;
    private String panCardNumber;
    private String dateOfBirth;
    private String customerAddress;
    private long customerAccountNumber;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="bankId",referencedColumnName = "bankId")
    private  Bank bank;


}
