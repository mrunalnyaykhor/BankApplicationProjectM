package com.bankmanagement.constant;

import com.bankmanagement.dto.BankDto;
import com.bankmanagement.dto.CustomerDto;
import com.bankmanagement.entity.Customer;

public class ApplicationConstant {

    public static final String BANK_SAVE_SUCCESSFULLY = "Bank save successfully";
    public static final String IFSC_CODE_ALREADY_EXIST = "A bank with IFSC code %s already exists.";
    public static final String BANK_NOT_AVAILABLE = "Bank not Available ";
    public static final String BANK_ID_ALREADY_PRESENT="Bank iD already present";
    public static final String IFSC_CODE_LENGTH_NOT_PROPER = "IFSC Code length should be 11 digit";
    public static final String ACCOUNT_NOT_FOUND = "Account is not present";
    public static final String ACCOUNT_NUMBER_ALREADY_PRESENT ="This Account Number Already Present";
    public static final String ACCOUNT_ALREADY_PRESENT = "Account already present";
    public static final String BANK_DELETE = "Bank Deleted Successfully";
    public static final String BANK_UPDATE = "bank update successfully";
    public static final String BANKS_GET_SUCCESSFULLY = "Banks get Successfully ";
    public static final String BANK_GET_SUCCESSFULLY = "The Bank Id  get Successfully";
    public static final String CUSTOMER_ID_ALREADY_PRESENT="Customer iD already present";


    public static final String CUSTOMER_SAVE = "customer Save successfully";
    public static final String CUSTOMER_ALREADY_PRESENT = "Customer Already present";
    public static final String AADHAAR_NUMBER_ALREADY_EXIST = "A Customer of AadhaarNumber Number  already exists.";
    public static final String EMAIL_ALREADY_EXIST = "A Customer of EmailId  already exists.";

    public static final String AADHAAR_NUMBER_SHOULD_BE_VALID = "Aadhaar Number should be  12 DIGIT";
    public static final String PANCARD_NUMBER_SHOULD_BE_VALID= "PanCard Number should be  10 DIGIT";
    public static final String NOT_VALID_AGE = "customer age is not valid \n Age should between 10 to 100 years old";
    public static final String CONTACT_NUMBER_INVALID = "customer contactNumber  is invalid";
    public static final String CUSTOMER_NOT_PRESENT = "Customer not present in DataBase";
    public static final String CUSTOMER_DELETE = "Customer Id  deleted successfully....!!";
    public static final String UPDATE_CUSTOMER_SUCCESSFULLY = "update customer successfully";

    public static final String BANK_AND_CUSTOMER_PRESENT = "Bank and customer present";
    public static final String Account_ID_ALREADY_PRESENT="Account id already present";

    public static final String ACCOUNT_IS_CREATED = "Account is created";
    public static final String WITHDRAWAL_AMOUNT_MORE_THAN_FIVE_HUNDRED = "Enter more than 500 rs for withdrawal";
    public static final String INSUFFICIENT_BALANCE = "Insufficient Balance";
    public static final String AMOUNT_WITHDRAWAL_SUCCESSFULLY = "Amount withdrawal successfully %.2f && Required Balance is %.2f";
    public static final String ENTER_MORE_THAN_HUNDRED_RUPEES = "Please Enter More than 100 rs";
    public static final String AMOUNT_DEPOSITED_SUCCESSFULLY = "amount deposited successfully";
    public static final String ACCOUNT_STATUS = "account status is %s";
    public static final String ACCOUNT_TYPE_STATUS = "The Account type status %s";

    public static final String ERROR_OCCURRED_WHILE_SAVING_INTO_THE_DATA_BASE = "error occurred while saving into the database";
    public static final String MINIMUM_BALANCE_FOR = "Minimum Balance for";
    public static final String ACCOUNT_CREATE = "Account create";
    public static final String CURRENT_ACCOUNT_IS_CREATED ="current Account is created";
    public static final String TRANSACTION_SUCCESSFUL= "transaction successful";
    public static final String ACCOUNT_ID_DELETED_SUCCESSFULLY ="account id deleted successfully";
    public static final String ACCOUNT_IS_BLOCKED ="You cannot send Money Your Account is Blocked Please Deposit Money in your Account";
    public static final String TO_ACCOUNT_IFSC_CODE_INCORRECT= "To account ifsc code is not correct";
    public static final String TO_ACCOUNT_NAME_IS_INCORRECT= "To account Name is not correct";
    public static final String BALANCE_IS_MINIMUM = "You Cannot Transfer Money Because Your Balance Amount is low ";
    public static final String CONTACT_IS_CORRECT = "contact Number is correct";
    public static final String GET_ALL_CUSTOMER = "get all customer";
    public static final String ACCOUNT_NUMBER_ALREADY_EXIST = "Account Number Already exist";

    public static final String ACCOUNT_ID_UPDATE_SUCCESSFULLY = "Account id update successfully";
    public static final String ACCOUNT_ID_DOES_NOT_EXIST = "Account Id does not exist";
    public static final String RESPONSE_SHOULD_NOT_NULL = "Response body should not be null";
}