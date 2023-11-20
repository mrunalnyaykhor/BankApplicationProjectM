package com.bankmanagement.controller;

import com.bankmanagement.constant.UrlConstant;
import com.bankmanagement.dto.BankDto;
import com.bankmanagement.entity.Bank;
import com.bankmanagement.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class BankController {
    @Autowired
    private BankService bankservice;
    @PostMapping(UrlConstant.SAVE_BANK)
    public ResponseEntity<BankDto> saveBanks(@RequestBody BankDto bankDto){
        return ResponseEntity.ok(bankservice.saveBank(bankDto));
    }
    @GetMapping(UrlConstant.GET_ALL_BANK)
    public ResponseEntity<List<BankDto>> getAllBank(){

        return ResponseEntity.ok(bankservice.getAllBank());
    }
    @GetMapping(UrlConstant.GET_BANK)
    public ResponseEntity<Bank> getBankId(@PathVariable Long bankId){
        Bank bankId1 = bankservice.getBankById(bankId);
        return ResponseEntity.ok(bankId1);
    }
    @PutMapping(UrlConstant.BANK_UPDATE)
    public ResponseEntity<BankDto> updateBank(@Valid @RequestBody BankDto bankDto,@PathVariable Long bankId){
        return ResponseEntity.ok(bankservice.updateBankById(bankDto,bankId));
    }
    @DeleteMapping(UrlConstant.BANK_DELETE)
    public ResponseEntity<String> deleteBank(@PathVariable Long bankId)
    {
        return ResponseEntity.ok(bankservice.deleteBankById(bankId));
    }

}
