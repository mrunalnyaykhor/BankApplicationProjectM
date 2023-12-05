package com.bankmanagement.service;

import com.bankmanagement.dto.BankDto;
import com.bankmanagement.entity.Bank;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface BankService {
    BankDto saveBank(BankDto bankDto);

    List<BankDto> getAllBank() throws ExecutionException, InterruptedException;

    String getBankById(Long bankId);

    BankDto updateBankById(BankDto bankDto);

    String deleteBankById(Long bankId);
}
