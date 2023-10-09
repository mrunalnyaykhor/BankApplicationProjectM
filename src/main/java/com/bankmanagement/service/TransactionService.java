package com.bankmanagement.service;

import com.bankmanagement.dto.TransactionDto;
import com.bankmanagement.entity.Transaction;
import com.bankmanagement.exception.AccountException;

public interface TransactionService {






    String transferMoney(TransactionDto transactionDto);
}
