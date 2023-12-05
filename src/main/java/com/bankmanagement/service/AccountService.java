package com.bankmanagement.service;

import com.bankmanagement.dto.AccountDto;
import com.bankmanagement.entity.Account;
import com.bankmanagement.exception.AccountException;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

public interface AccountService {

    String saveAccount(AccountDto accountdto) throws ExecutionException, InterruptedException;

    List<AccountDto> getAllAccount() throws AccountException;

    String updateAccountById(AccountDto accountDto) throws AccountException;

    String deleteAccountById(Long accountId) throws AccountException;

    Double getBalance(Long accountId) throws AccountException;

    String withdrawalAmountById(Long accountId, Double amount) throws AccountException;

    String deposit(Long accountId, Double amount) throws AccountException;

    String isBlocked(Long accountId) throws AccountException;

    AccountDto getAccountById(Long accountId) throws AccountException;

    String accountStatus(Long accountId) throws AccountException;

    List<Account> getAllSavingAccount();
}
