package com.bankmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Setter
@Getter
public class CustomerDto {
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
}
