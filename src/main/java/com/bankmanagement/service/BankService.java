package com.bankmanagement.service;

import com.bankmanagement.dto.BankDto;
import com.bankmanagement.entity.Bank;

import java.util.List;

public interface BankService {
    BankDto saveBank(BankDto bankDto);

    List<BankDto> getAllBank();

    String getBankById(Long bankId);

    BankDto updateBankById(BankDto bankDto);

    String deleteBankById(Long bankId);
}
