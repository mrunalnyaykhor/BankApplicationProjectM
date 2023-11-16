package com.bankmanagement.service;

import com.bankmanagement.dto.BankDto;
import com.bankmanagement.entity.Bank;

import java.util.List;

public interface BankService {
    BankDto saveBank(BankDto bankDto);

    List<BankDto> getAllBank();

    Bank getBankById(Long bankId);

    BankDto updateBankById(BankDto bankDto, Long bankId);

    String deleteBankById(Long bankId);
}
