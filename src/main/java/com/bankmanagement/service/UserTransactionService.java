package com.bankmanagement.service;
import com.bankmanagement.dto.UserTransactionDto;
import com.bankmanagement.entity.UserTransaction;
import javax.security.auth.login.AccountException;

public interface UserTransactionService {

    UserTransactionDto transactionAmount(UserTransactionDto userTransactionDto, Long accountId);

    UserTransaction depositAmount(UserTransaction userTransaction) throws AccountException;


}
