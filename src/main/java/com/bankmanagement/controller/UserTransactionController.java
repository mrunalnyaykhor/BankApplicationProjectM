package com.bankmanagement.controller;

import com.bankmanagement.dto.UserTransactionDto;
import com.bankmanagement.entity.Account;
import com.bankmanagement.entity.UserTransaction;
import com.bankmanagement.service.AccountService;
import com.bankmanagement.service.UserTransactionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.security.auth.login.AccountException;

@RestController
public class UserTransactionController {
    @Autowired
    private UserTransactionService userTransactionService;
    @Autowired
    private AccountService accountService;

    @PostMapping("/transactionAmount")
    public ResponseEntity<UserTransactionDto> transactionAmount(@RequestBody UserTransactionDto userTransactionDto, @PathVariable Long accountId){

        return ResponseEntity.ok(userTransactionService.transactionAmount(userTransactionDto,accountId));
    }
    @PostMapping("amount/depositAmount")
    public UserTransaction depositAmount(@RequestBody UserTransaction userTransaction) throws AccountException {
        return userTransactionService.depositAmount(userTransaction);
    }



}
