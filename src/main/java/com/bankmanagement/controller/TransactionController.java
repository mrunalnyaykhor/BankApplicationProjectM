package com.bankmanagement.controller;

import com.bankmanagement.constant.UrlConstant;
import com.bankmanagement.dto.TransactionDto;
import com.bankmanagement.exception.AccountException;
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

    @PutMapping(UrlConstant.TRANSFER_MONEY)
    public ResponseEntity<String> transferMoney(@Valid @RequestBody TransactionDto transactionDto) throws AccountException {
        return new ResponseEntity<>(transactionService.transferMoney(transactionDto), HttpStatus.CREATED);
    }

}
