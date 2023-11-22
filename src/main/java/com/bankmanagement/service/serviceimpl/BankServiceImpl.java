package com.bankmanagement.service.serviceimpl;

import com.bankmanagement.constant.ApplicationConstant;
import com.bankmanagement.dto.BankDto;
import com.bankmanagement.entity.Bank;
import com.bankmanagement.exception.BankException;
import com.bankmanagement.repository.BankRepository;
import com.bankmanagement.service.BankService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BankServiceImpl implements BankService {
    @Autowired
    public BankRepository bankRepository;

    @Override
    public BankDto saveBank(BankDto bankDto) {
        if (bankRepository.existsByIfscCode(bankDto.getIfscCode())) {
            log.error("A bank with IFSC code %s already exists");
            throw new BankException(ApplicationConstant.IFSC_CODE_ALREADY_EXIST);

        } else {
            Bank bank = new Bank();
            BeanUtils.copyProperties(bankDto, bank);
            bankRepository.save(bank);
            log.info("bank save successfully");

        }
        return bankDto;
    }

    @Override
    public List<BankDto> getAllBank() {
        if (bankRepository.findAll().isEmpty()) {
            log.error("Bank Not Available");
            throw new BankException(ApplicationConstant.BANK_NOT_AVAILABLE);
        }
        log.info("get bank successfully");
        return bankRepository.findAll().stream().filter(Objects::nonNull)
                .map(bank -> {
                    BankDto bankDto = new BankDto();
                    BeanUtils.copyProperties(bank, bankDto);
                    return bankDto;

                }).collect(Collectors.toList());

    }

    @Override
    public Bank getBankById(Long bankId) {
        if(bankRepository.findById(bankId).isEmpty())
        {
            throw new BankException(ApplicationConstant.BANK_NOT_AVAILABLE);
        }
        log.info("bank Id get successfully");
        return bankRepository.findById(bankId).get();
    }

    @Override
    public BankDto updateBankById(BankDto bankDto, Long bankId)
    {
        Bank bank = bankRepository.findById(bankId).orElseThrow(() -> new BankException(ApplicationConstant.BANK_NOT_AVAILABLE));
        if (bankRepository.existsByIfscCode(bankDto.getIfscCode())) {
            log.info("A bank with IFSC code %s already exists");
            throw new BankException(ApplicationConstant.IFSC_CODE_ALREADY_EXIST);
        }
        BeanUtils.copyProperties(bankDto, bank);
            bankRepository.save(bank);
            log.info("bank update successfully");
            return bankDto;


    }

    @Override
    public String deleteBankById(Long bankId) {
        Optional<Bank> bankOptional = bankRepository.findById(bankId);
        if (bankOptional.isEmpty())
        {throw new BankException(ApplicationConstant.BANK_NOT_AVAILABLE);
        }
        bankOptional.ifPresent(bank -> bankRepository.deleteById(bankId));
        log.info("bank deleted successfully");
        return ApplicationConstant.BANK_DELETE;
    }

}
