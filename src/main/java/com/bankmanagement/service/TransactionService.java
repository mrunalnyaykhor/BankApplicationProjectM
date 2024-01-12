package com.bankmanagement.service;
import com.bankmanagement.dto.TransactionDto;
import com.bankmanagement.entity.Transaction;
import com.bankmanagement.entity.Withdrawal;
import com.bankmanagement.exception.AccountException;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TransactionService {

    ResponseEntity<String> transferMoney(TransactionDto transactionDto) throws AccountException;


    List<TransactionDto> findTransaction(Long accountNumber, long transactionDate);

    List<Transaction> getAllTransaction();


    ResponseEntity<String> withdrawalMoney(Withdrawal withdrawal);
}
