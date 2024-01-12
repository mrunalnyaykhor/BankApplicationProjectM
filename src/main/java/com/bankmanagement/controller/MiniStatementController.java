package com.bankmanagement.controller;

import com.bankmanagement.constant.UrlConstant;
import com.bankmanagement.dto.TransactionDto;
import com.bankmanagement.entity.Transaction;
import com.bankmanagement.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = { "http://localhost:4200/" })
public class MiniStatementController {
    @Autowired
    private TransactionService transactionService;

    @GetMapping(UrlConstant.MINI_STATEMENT_DAY_WISE)
    public ResponseEntity<List<TransactionDto>> getMiniStatement(@Valid @PathVariable Long accountNumber, @PathVariable long days) {
      return  ResponseEntity.ok(transactionService.findTransaction(accountNumber,days));
    }
    @GetMapping(UrlConstant.GET_ALL_TRANSACTION)
    public ResponseEntity<List<Transaction>> getAllTransaction(){
        return ResponseEntity.ok(transactionService.getAllTransaction());
    }
}
