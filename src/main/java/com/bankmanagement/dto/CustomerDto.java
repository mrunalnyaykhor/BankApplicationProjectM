package com.bankmanagement.dto;


import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {
    @NotNull(message = "customerId is mandatory")
    private Long customerId;
    @NotBlank(message = "First Name is mandatory")
    private String firstName;
    @NotBlank(message = "Last Name is mandatory")
    private String lastName;
    @NotNull(message = "Contact Number is mandatory")
    private Long contactNumber;
    @NotNull(message = "age is mandatory")
    private int age;
    @NotBlank(message = "Email is mandatory")
    @Email(message = "email should be a valid")
    private String email;

    @NotNull(message = "aadhaar Number is Mandatory")
    @Pattern(regexp = "^\\d{12}$", message = "aadhaarNumber should be 12 digit")
    private String aadhaarNumber;
    @NotNull(message = "PanCard Number is Mandatory")
    @Pattern(regexp = "[A-Z]{5}[0-9]{4}[A-Z]{1}", message = "panCardNumber is INVALID")
    private String panCardNumber;

    @NotBlank(message = "date Of Birth is Mandatory")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Date of birth should be in YYYY-MM-DD pattern")
    private String dateOfBirth;

    @NotBlank(message = "Address should be mandatory")
    private String address;
    @NotNull(message = "Bank Id is mandatory")
    private Long bankId;
}
