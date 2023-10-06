package com.bankmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
@Setter
@Getter
public class CustomerDto {
    @NotNull
    @Size(min=2, message="Name should have at least 2 characters")
    private String firstName;
    @NotBlank(message = "Name is mandatory")
    private String lastName;
    @NotNull(message = "contactNumber is mandatory")
    private Long contactNumber;
    @NotNull
    private int age;
    @NotBlank(message = "email is mandatory")
    private String email;
    @NotNull(message = "aadhaar Number is Mandatory")
    private String aadhaarNumber;
    @NotBlank(message = "panCardNumber should not be null")
    @Size(min=8, message="Name should have at least 8 characters")
    private String panCardNumber;
    @NotNull
    private String dateOfBirth;
    @NotBlank(message = "Address should be mandatory")
    @Size(min =2,message = "address should be 2 characters")
    private String address;
    @NotNull
    private long accountNumber;
    private Long bankId;
}
