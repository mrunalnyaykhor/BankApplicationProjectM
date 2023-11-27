package com.bankmanagement.dto;

import lombok.*;

import javax.validation.constraints.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {
    private Long customerId;
    @NotBlank(message = "First Name is mandatory")
    private String firstName;
    @NotBlank(message = "Last Name is mandatory")
    private String lastName;
    @NotNull(message = "Contact Number is mandatory")
    private Long contactNumber;
    @NotNull(message = "age is mandatory")
    private int age;
    @Email(message = "email should be a valid email format")
    private String email;

    @NotNull(message = "aadhaar Number is Mandatory")
    private String aadhaarNumber;

    @Pattern(regexp = "[A-Z]{5}[0-9]{4}[A-Z]{1}", message = "panCardNumber should not be null")
    private String panCardNumber;

    @NotBlank(message = "date Of Birth is Mandatory")
    private String dateOfBirth;

    @NotBlank(message = "Address should be mandatory")
    private String address;

    private Long bankId;
}
