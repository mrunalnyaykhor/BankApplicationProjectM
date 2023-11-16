package com.bankmanagement.controller;

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
    @PostMapping("/saveBank")
    public ResponseEntity<BankDto> saveBanks(@RequestBody BankDto bankDto){
        return ResponseEntity.ok(bankservice.saveBank(bankDto));
    }
    @GetMapping("/getAllBank")
    public ResponseEntity<List<BankDto>> getAllBank(){

        return ResponseEntity.ok(bankservice.getAllBank());
    }
    @GetMapping("/getBankById/{bankId}")
    public ResponseEntity<Bank> getBankId(@PathVariable Long bankId){
        Bank bankId1 = bankservice.getBankById(bankId);
        return ResponseEntity.ok(bankId1);
    }
    @PutMapping("/updateBank/{bankId}")
    public ResponseEntity<BankDto> updateBank(@Valid @RequestBody BankDto bankDto,@PathVariable Long bankId){
        return ResponseEntity.ok(bankservice.updateBankById(bankDto,bankId));
    }
    @DeleteMapping("/deleteBank/{bankId}")
    public ResponseEntity<String> deleteBank(@PathVariable Long bankId)
    {

        return ResponseEntity.ok(bankservice.deleteBankById(bankId));
    }

}
