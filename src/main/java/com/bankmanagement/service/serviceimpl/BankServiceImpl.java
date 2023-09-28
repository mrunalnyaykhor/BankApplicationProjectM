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
    private BankRepository bankRepository;
    @Override
    public BankDto saveBank(BankDto bankDto) {
        Bank bank = new Bank();
        BeanUtils.copyProperties(bank,bankDto);
        bankRepository.save(bank);
        return bankDto;
    }

    @Override
    public List<BankDto> getAllBank() {
        if(bankRepository.findAll().isEmpty())
            throw new BankException("Bank not Available");
        return bankRepository.findAll().stream().filter(Objects::nonNull)
                .map(bank ->{
                    BankDto bankDto = new BankDto();
                    BeanUtils.copyProperties(bank,bankDto);
                    return bankDto;

                }).collect(Collectors.toList());


    }

    @Override
    public List<BankDto> getBankById(Long bankId) {
        Optional<Bank> bank = bankRepository.findById(bankId);
        if(bank.isEmpty())
            throw new BankException("Bank Does Not exist");
        return bankRepository.findById(bankId).stream().filter(Objects::nonNull)
                .map(bank1 -> {
                    BankDto bankDto = new BankDto();
                    BeanUtils.copyProperties(bank1,bankDto);
                    return bankDto;
                }).collect(Collectors.toList());

    }

    @Override
    public BankDto updateBankById(BankDto bankDto, Long bankId) {
        Optional<Bank> bankOptional = bankRepository.findById(bankId);
        if(bankOptional.isEmpty())
        {
            throw new BankException("Bank Not Available");
        }
       if(bankOptional.isPresent()){
           Bank bank = bankOptional.get();
           BeanUtils.copyProperties(bankDto,bank);
           bankRepository.save(bank);
       }
       return bankDto;

    }

    @Override
    public void deleteBankById(Long bankId) {
        Optional<Bank> bankOptional = bankRepository.findById(bankId);
        if(bankOptional.isEmpty())
        {
            throw new BankException("Bank Not Available");
        }
        if(bankOptional.isPresent())
        {
            bankRepository.deleteById(bankId);
        }

    }
}
