package com.bankmanagement.service;

import com.bankmanagement.dto.BankDto;
import com.bankmanagement.entity.Bank;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface BankService {
    ResponseEntity<String> saveBank(BankDto bankDto);

    List<Bank> getAllBank() throws ExecutionException, InterruptedException;

    BankDto getBankById(Long bankId);

    ResponseEntity<String> updateBankById(Bank bank);

    ResponseEntity<String> deleteBankById(Long bankId);
}
