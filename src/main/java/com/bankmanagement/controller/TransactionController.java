package com.bankmanagement.controller;

import com.bankmanagement.dto.TransactionDto;
import com.bankmanagement.service.AccountService;
import com.bankmanagement.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class TransactionController {
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private AccountService accountService;

    @PutMapping("/transferMoney")
    public ResponseEntity<String> transferMoney(@Valid @RequestBody TransactionDto transactionDto) {
        String s = transactionService.transferMoney(transactionDto);
        return new ResponseEntity<>(s, HttpStatus.CREATED);
    }

}
