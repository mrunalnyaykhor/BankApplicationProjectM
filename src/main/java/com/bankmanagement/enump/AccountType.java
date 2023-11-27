package com.bankmanagement.enump;

public enum AccountType {
    SAVING("Saving",5000.00), CURRENT("Current",10000.00);
    private final String value;
    private final double amount;
    AccountType(String value,double amount) {
        this.value=value;
        this.amount = amount;

    }
    public double getAmount() {
        return amount;
    }

    public String getValue() {
        return value;
    }

}
