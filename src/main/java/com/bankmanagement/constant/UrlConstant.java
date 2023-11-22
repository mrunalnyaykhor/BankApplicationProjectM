package com.bankmanagement.constant;

public class UrlConstant {
    public static final String SAVE_CUSTOMER = "/saveCustomer/{bankId}";
    public static final String GET_BY_ID_OR_NAME_OR_MOBILENO_OR_EMAILID = "/getBy/{content}";
    public static final String GET_ALL_CUSTOMER = "/getAllCustomer";
    public static final String GET_CUSTOMER_BY_ID = "/getCustomerById/{customerId}";
    public static final String UPDATE_CUSTOMER = "/customer/{customerId}";
    public static final String DELETE_CUSTOMER = "/deleteCustomerById/{customerId}";
    public static final String GET_ALL_CUSTOMERS_WITHOUT_ACCOUNTS = "getAllCustomersWithoutAccount/{bankId}";

    public static final String SAVE_BANK = "/saveBank";

    public static final String GET_BANK = "/getBankById/{bankId}";
    public static final String BANK_UPDATE = "/updateBank/{bankId}";
    public static final String BANK_DELETE = "/deleteBank/{bankId}";
    public static final String GET_ALL_BANK = "/getAllBank";

    public static final String SAVE_ACCOUNT = "/saveAccount/{customerId}/{bankId}";
    public static final String GATE_ALL_ACCOUNT = "/getAllAccount";
    public static final String GATE_ACCOUNT_BY_ID = "/getAccountById/{accountId}";
    public static final String UPDATE_ACCOUNT_BY_ID = "/accounts/{accountId}";
    public static final String DELETE_ACCOUNT = "/deleteAccountIdById/{accountId}";
    public static final String BALANCE_CHECK = "/checkAmountById/{accountId}";
    public static final String DEPOSIT_AMOUNT = "/deposit/{accountId}";
    public static final String WITHDRAWAL_AMOUNT = "/{accountId}/withdrawalAmount";
    public static final String ACCOUNT_BLOCK_UNBLOCK_CHECK = "/blockAccountOrNot/{accountId}";

    public static final String TRANSFER_MONEY = "/transferMoney";
    public static final String MINI_STATEMENT_DAY_WISE = "/statement/{accountNumber}/{days}";
}
