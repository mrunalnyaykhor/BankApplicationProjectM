package com.bankmanagement.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

@Setter
@Getter
public class AccountDto {
    private Long accountId;
    @NotBlank(message = "First Name is Mandatory")
    @Size(min = 2, message = "first Name at list 2 characters")
    private String firstName;
    @NotBlank(message = "last Name is Mandatory")
    @Size(min = 2, message = "last Name at list 2 characters")
    private String lastName;
    @NotNull(message = "contact number is mandatory")
    @Size(min = 10, message = "contact number should be 10 characters")
    private Long contactNumber;
    @NotNull(message = "amount is mandatory")
    private double amount;
    @NotNull(message = "Age is Mandatory")
    @Size(min = 18, message = "age should be greater than 18 years")
    private int age;
    @NotNull(message = "Aadhaar Number is Mandatory")
    @Size(min = 12, message = "Aadhaar should be 12 characters")
    private Double aadhaarNumber;
    @NotBlank(message = "Aadhaar Number is Mandatory")
    @Size(min = 4, message = "PanCard should be 4 characters")
    private String panCardNumber;
    @NotBlank(message = "Date of Birth is Mandatory")
    private String dateOfBirth;

    @NotNull(message = "Account Number is mandatory")
    private long accountNumber;

    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",
            flags = Pattern.Flag.CASE_INSENSITIVE)
    private String email;
    @NotNull(message = "Balance is mandatory")
    private Double balance;
    private Long customerId;

    private Long bankId;
//    @OneToMany(cascade = CascadeType.ALL)
//    private Bank bank;
//    @OneToMany(cascade = CascadeType.ALL)
//    private Customer customer;


}
