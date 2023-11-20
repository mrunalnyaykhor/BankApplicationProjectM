package com.bankmanagement.controller;

import com.bankmanagement.constant.UrlConstant;
import com.bankmanagement.dto.TransactionDto;
import com.bankmanagement.service.AccountService;
import com.bankmanagement.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountException;
import javax.validation.Valid;

@RestController
public class TransactionController {
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private AccountService accountService;

    @PutMapping(UrlConstant.TRANSFER_MONEY)
    public ResponseEntity<String> transferMoney(@Valid @RequestBody TransactionDto transactionDto) throws AccountException {
        String s = transactionService.transferMoney(transactionDto);
        return new ResponseEntity<>(s, HttpStatus.CREATED);
    }

}
