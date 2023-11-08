package com.bankmanagement.service;

import com.bankmanagement.dto.AccountDto;

import javax.security.auth.login.AccountException;
import java.util.List;

public interface AccountService {

    AccountDto saveAccount(AccountDto accountdto,Long customerId,Long bankId) throws AccountException;
    List<AccountDto> getAllAccount() throws AccountException;
    String updateAccountById(AccountDto accountDto, Long accountId) throws AccountException;
    String deleteAccountById(Long accountId) throws AccountException;
    Double getBalance(Long accountId) throws AccountException;
    String withdrawalAmountById(Long accountId,Double amount) throws AccountException;
    String deposit(Long accountId, Double amount) throws AccountException;
    String isBlocked(Long accountId) throws AccountException;

    AccountDto getAccountById(Long accountId) throws AccountException;
}
