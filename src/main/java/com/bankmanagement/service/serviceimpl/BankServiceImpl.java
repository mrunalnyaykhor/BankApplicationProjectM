package com.bankmanagement.service.serviceimpl;

import com.bankmanagement.dto.BankDto;
import com.bankmanagement.entity.Bank;
import com.bankmanagement.exception.BankException;
import com.bankmanagement.repository.BankRepository;
import com.bankmanagement.service.BankService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BankServiceImpl implements BankService {
    @Autowired
    public BankRepository bankRepository;

    @Override
    public BankDto saveBank(BankDto bankDto) {
        if (bankRepository.existsByIfscCode(bankDto.getIfscCode())) {
            throw new BankException("A bank with IFSC code %s already exists.".formatted(bankDto.getIfscCode()));

        } else {
            Bank bank = new Bank();
            BeanUtils.copyProperties(bankDto, bank);
            bankRepository.save(bank);

        }
        return bankDto;
    }

    @Override
    public List<BankDto> getAllBank() {
        if (bankRepository.findAll().isEmpty())
            throw new BankException("Bank not Available");
        return bankRepository.findAll().stream().filter(Objects::nonNull)
                .map(bank -> {
                    BankDto bankDto = new BankDto();
                    BeanUtils.copyProperties(bank, bankDto);
                    return bankDto;
                }).collect(Collectors.toList());
    }

    @Override
    public Bank getBankById(Long bankId) {
        return bankRepository.findById(bankId).get();
    }

    @Override
    public BankDto updateBankById(BankDto bankDto, Long bankId)
    {
        Bank bank = bankRepository.findById(bankId).orElseThrow(() -> new BankException("Bank Not Available"));
        if (bankRepository.existsByIfscCode(bankDto.getIfscCode())) {
            throw new BankException("A bank with IFSC code %s already exists.".formatted(bankDto.getIfscCode()));
        } else {
            BeanUtils.copyProperties(bankDto, bank);
            bankRepository.save(bank);
            return bankDto;

        }

    }

    @Override
    public void deleteBankById(Long bankId) {
        Optional<Bank> bankOptional = bankRepository.findById(bankId);
        if (bankOptional.isEmpty()) {throw new BankException("Bank Not Available");
        }
        bankOptional.ifPresent(bank -> bankRepository.deleteById(bankId));
    }
}
