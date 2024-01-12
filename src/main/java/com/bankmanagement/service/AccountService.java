package com.bankmanagement.service;

import com.bankmanagement.dto.AccountDto;
import com.bankmanagement.entity.Account;
import com.bankmanagement.exception.AccountException;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface AccountService {

    ResponseEntity<String> saveAccount(AccountDto accountdto) throws ExecutionException, InterruptedException;

    List<Account> getAllAccount() throws AccountException;

    ResponseEntity<String> updateAccountById(Account account) throws AccountException;

    String deleteAccountById(Long accountId) throws AccountException;

    Double getBalance(Long accountId) throws AccountException;

    String withdrawalAmountById(Long accountId, Double amount) throws AccountException;

    String deposit(Long accountId, Double amount) throws AccountException;

    String isBlocked(Long accountId) throws AccountException;

    AccountDto getAccountById(Long accountId) throws AccountException;

    String accountStatus(Long accountId) throws AccountException;

    List<Account> getAllSavingAccount();

    AccountDto getMyAccountDetails(Long accountNumber);



}
