package com.bankmanagement.service;

import com.bankmanagement.dto.TransactionDto;
import com.bankmanagement.entity.Transaction;
import com.bankmanagement.exception.AccountException;

public interface TransactionService {

    TransactionDto transactionAmount(TransactionDto transactionDto, Long accountId);




    String transferMoney(TransactionDto transactionDto);
}
