package com.bankmanagement.controller;

import com.bankmanagement.constant.UrlConstant;
import com.bankmanagement.dto.TransactionDto;
import com.bankmanagement.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
public class MiniStatementController {
    @Autowired
    private TransactionService transactionService;

    @GetMapping(UrlConstant.MINI_STATEMENT_DAY_WISE)
    public ResponseEntity<List<TransactionDto>> getMiniStatement(@PathVariable Long accountNumber, @PathVariable long days) {

      return  ResponseEntity.ok(transactionService.findTransaction(accountNumber,days));
    }
}
