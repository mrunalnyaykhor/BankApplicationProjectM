package com.bankmanagement.controller;

import com.bankmanagement.constant.UrlConstant;
import com.bankmanagement.dto.AccountDto;
import com.bankmanagement.dto.TransactionDto;
import com.bankmanagement.entity.Transaction;
import com.bankmanagement.entity.Withdrawal;
import com.bankmanagement.exception.AccountException;
import com.bankmanagement.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = { "http://localhost:4200/" })
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @PutMapping(UrlConstant.TRANSFER_MONEY)
    public ResponseEntity<ResponseEntity<String>> transferMoney(@Valid @RequestBody TransactionDto transactionDto) throws AccountException {
        return ResponseEntity.ok(transactionService.transferMoney(transactionDto));
    }

    @PutMapping(UrlConstant.WITHDRAWAL_MONEY)
    public ResponseEntity<ResponseEntity<String>> withdrawalMoney(@RequestBody Withdrawal withdrawal) throws AccountException {
        return ResponseEntity.ok(transactionService.withdrawalMoney(withdrawal));

    }
    }



