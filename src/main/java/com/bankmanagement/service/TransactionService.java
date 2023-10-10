package com.bankmanagement.service;
import com.bankmanagement.dto.TransactionDto;

import java.util.List;

public interface TransactionService {

    String transferMoney(TransactionDto transactionDto);


    List<TransactionDto> findTransaction(Long accountId);
}
