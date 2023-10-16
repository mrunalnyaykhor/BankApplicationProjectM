package com.bankmanagement.service;
import com.bankmanagement.dto.TransactionDto;
import com.bankmanagement.entity.Account;

import javax.security.auth.login.AccountException;
import java.util.Date;
import java.util.List;

public interface TransactionService {

    String transferMoney(TransactionDto transactionDto) throws AccountException;


    List<TransactionDto> findTransaction(Long accountNumber, long transactionDate);
}
