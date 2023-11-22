package com.bankmanagement.constant;

import com.bankmanagement.dto.BankDto;
import com.bankmanagement.dto.CustomerDto;
import com.bankmanagement.entity.Customer;

public class ApplicationConstant {
    static BankDto bankDto;
    static CustomerDto customerDto;
    private static Customer customerId;
    public static final String MINIMUM_BALANCE_FOR = "Minimum Balance for";
    public static final String IFSC_CODE_ALREADY_EXIST = "A bank with IFSC code %s already exists.".formatted(bankDto.getIfscCode());
    public static final String BANK_NOT_AVAILABLE = "Bank not Available ";
    public static final String ACCOUNT_NOT_FOUND = "Account is not present";
    public static final String BANK_DELETE = "Bank Deleted Successfully";


    public static final String CUSTOMER_SAVE = "customer Save successfully";
    public static final String AADHAAR_NUMBER_ALREADY_EXIST = "A Customer of AadhaarNumber Number %s already exists.".formatted(customerDto.getAadhaarNumber());
    public static final String CONTACT_NUMBER_NOT_PROPER = "Customer contactNumber not proper format";
    public static final String CUSTOMER_NOT_PRESENT = "Customer not present in DataBase";
    public static final String CUSTOMER_DELETE = "Customer Id :%d deleted successfully....!!";
    public static final String UPDATE_CUSTOMER_SUCCESSFULLY = "update customer successfully";

    public static final String NAME_INCORRECT = "Cannot create Account Customer name is incorrect";
    public static final String LAST_NAME_INCORRECT = "Cannot create Account Customer Last Name is incorrect";

    public static final String PANCARD_INCORRECT = "Cannot create Account Customer panCardNumber is incorrect";
    public static final String CONTACT_INCORRECT = "Contact Incorrect";
    public static final String DATE_OF_BIRTH_INCORRECT = "Cannot create Account Customer dateOfBirth is incorrect";
    public static final String MORE_THAN_FIVE_HUNDRED = "Enter more than 500 rs for withdrawal";
    public static final String INSUFFICIENT_BALANCE = "Insufficient Balance";
    public static final String AMOUNT_WITHDRAWAL_SUCCESSFULLY = "Amount withdrawal successfully %.2f && Required Balance is %.2f";
    public static final String ENTER_MORE_THAN_HUNDRED_RUPEES = "Please Enter More than 100 rs";
    public static final String AMOUNT_DEPOSITED_SUCCESSFULLY = "Please Enter More than 100 rs";
    public static final String ACCOUNT_STATUS = "account status";

}