package com.bankmanagement.service;

import com.bankmanagement.dto.AccountDto;

import javax.security.auth.login.AccountException;
import java.util.List;

public interface AccountService {

    String saveAccount(AccountDto accountdto) ;

    List<AccountDto> getAllAccount() throws AccountException;

    String updateAccountById(AccountDto accountDto) throws AccountException;

    String deleteAccountById(Long accountId) throws AccountException;

    Double getBalance(Long accountId) throws AccountException;

    String withdrawalAmountById(Long accountId, Double amount) throws AccountException;

    String deposit(Long accountId, Double amount) throws AccountException;

    String isBlocked(Long accountId) throws AccountException;

    AccountDto getAccountById(Long accountId) throws AccountException;

    String accountStatus(Long accountId) throws AccountException;
}
