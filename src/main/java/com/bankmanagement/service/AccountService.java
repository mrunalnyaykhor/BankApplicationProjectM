package com.bankmanagement.service;

import com.bankmanagement.dto.AccountDto;
import com.bankmanagement.entity.Account;

import javax.security.auth.login.AccountException;
import java.util.List;

public interface AccountService {

    AccountDto saveAccount(AccountDto accountdto,Long customerId,Long bankId);

    List<AccountDto> getAllAccount() throws AccountException;


    String updateAccountById(AccountDto accountDto, Long accountId) throws AccountException;

    String deleteAccountById(Long accountId) throws AccountException;

    List<Double> getBalance(Long accountId) throws AccountException;

    AccountDto depositAmount(AccountDto accountDto, Long accountId) throws AccountException;

    Account withdrawalAmountById(Long accountId,Double amount) throws AccountException;
    Account deposit(Long accountId, Double amount) throws AccountException;



}
