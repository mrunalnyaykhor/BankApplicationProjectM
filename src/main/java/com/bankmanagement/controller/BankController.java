package com.bankmanagement.controller;

import com.bankmanagement.constant.ApplicationConstant;
import com.bankmanagement.constant.UrlConstant;
import com.bankmanagement.dto.BankDto;
import com.bankmanagement.entity.Bank;
import com.bankmanagement.service.BankService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
public class BankController {
    Logger logger = LoggerFactory.getLogger(BankController.class);
    @Autowired
    private BankService bankservice;
    @PostMapping(UrlConstant.SAVE_BANK)
    public ResponseEntity<BankDto> saveBanks(@Valid @RequestBody BankDto bankDto){
        return ResponseEntity.ok(bankservice.saveBank(bankDto));
    }
    @GetMapping(UrlConstant.GET_ALL_BANK)
    public ResponseEntity<List<BankDto>> getAllBank() throws ExecutionException, InterruptedException {
        logger.info(ApplicationConstant.GET_ALL_CUSTOMER);

        return ResponseEntity.ok(bankservice.getAllBank());
    }
    @GetMapping(UrlConstant.GET_BANK)
    public ResponseEntity<String> getBankId(@PathVariable Long bankId){

        return ResponseEntity.ok( bankservice.getBankById(bankId));
    }
    @PutMapping(UrlConstant.BANK_UPDATE)
    public ResponseEntity<BankDto> updateBank(@Valid @RequestBody BankDto bankDto){
        return ResponseEntity.ok(bankservice.updateBankById(bankDto));
    }
    @DeleteMapping(UrlConstant.BANK_DELETE)
    public ResponseEntity<String> deleteBank(@PathVariable Long bankId)
    {
        return ResponseEntity.ok(bankservice.deleteBankById(bankId));
    }

}
