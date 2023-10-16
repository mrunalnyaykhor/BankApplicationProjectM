package com.bankmanagement.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

@Setter
@Getter
public class CustomerDto {
 @NotBlank(message = "first Name is mandatory")
    private String firstName;
   @NotBlank(message = "Name is mandatory")
    private String lastName;
 @NotNull(message = "Contact Number should be 10 digit")
 //@Size(min=10 ,max = 10 ,message="Contact Number should be 10 digit")
    private Long contactNumber;
   @NotNull(message = "age is mandatory")
    private int age;
    @Email(message = "email should be a valid email format")
    private String email;

    @NotNull(message = "aadhaar Number is Mandatory")
    private String aadhaarNumber;

   @Pattern(regexp = "[A-Z]{5}[0-9]{4}[A-Z]{1}",message = "panCardNumber should not be null")

    private String panCardNumber;

    @NotBlank(message = "date Of Birth is Mandatory")
    private String dateOfBirth;

   @NotBlank(message = "Address should be mandatory")
    private String address;

    private Long bankId;
}
