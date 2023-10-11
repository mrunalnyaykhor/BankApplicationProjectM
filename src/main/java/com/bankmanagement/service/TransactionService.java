package com.bankmanagement.service;
import com.bankmanagement.dto.TransactionDto;
import com.bankmanagement.entity.Account;

import java.util.Date;
import java.util.List;

public interface TransactionService {

    String transferMoney(TransactionDto transactionDto);


    List<TransactionDto> findTransaction(Long accountNumber, long transactionDate);
}
