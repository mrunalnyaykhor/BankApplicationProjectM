package com.bankmanagement.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

@Setter
@Getter
public class AccountDto {
    @NotBlank(message = "First Name is Mandatory")
    private String firstName;
    @NotBlank(message = "last Name is Mandatory")
    private String lastName;
    @NotNull(message = "contact number is mandatory")
    private Long contactNumber;
    @NotNull(message = "amount is mandatory")
    private double amount;
    @NotNull(message = "Age is Mandatory")
    private int age;
    @NotNull(message = "Aadhaar Number is Mandatory")
    private Long aadhaarNumber;
    @NotBlank(message = "Aadhaar Number is Mandatory")
    private String panCardNumber;
    @NotBlank(message = "Date of Birth is Mandatory")
    private String dateOfBirth;
    private long accountNumber; //Randomly generate account no.
    private boolean isBlocked = false;

    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",
            flags = Pattern.Flag.CASE_INSENSITIVE)
    private String email;
    private Long customerId;

    private Long bankId;



}
