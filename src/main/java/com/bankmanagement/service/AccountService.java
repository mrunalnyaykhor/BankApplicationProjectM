package com.bankmanagement.service;

import com.bankmanagement.dto.AccountDto;
import org.springframework.http.ResponseEntity;

import javax.security.auth.login.AccountException;
import java.util.List;

public interface AccountService {

    AccountDto saveAccount(AccountDto accountdto);

    List<AccountDto> getAllAccount() throws AccountException;

    List<AccountDto> accountFindById(Long accountId) throws AccountException;

    AccountDto updateAccountById(AccountDto accountDto, Long accountId);

    String deleteAccountById(Long accountId);

    ResponseEntity<List<AccountDto>> getBalanceById(Double balance);

    AccountDto depositAmount(AccountDto accountDto, Long accountId) throws AccountException;

    AccountDto withdrawalAmountById(Long accountId);
}
