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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<String> saveBank(BankDto bankDto) {

        if (bankRepository.existsByIfscCode(bankDto.getIfscCode())) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST).body(ApplicationConstant.IFSC_CODE_ALREADY_EXIST);

        } else if (bankDto.getIfscCode().length()!=11) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ApplicationConstant.IFSC_CODE_LENGTH_NOT_PROPER);
        }
            Bank bank = new Bank();
            BeanUtils.copyProperties(bankDto, bank);
            bankRepository.save(bank);
            log.info(ApplicationConstant.BANK_SAVE_SUCCESSFULLY);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApplicationConstant.BANK_SAVE_SUCCESSFULLY);
    }

    @Override
    public List<Bank> getAllBank()  {
        bankRepository.findAll().stream().findAny().orElseThrow(() ->
                new BankException(ApplicationConstant.BANK_NOT_AVAILABLE));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(bankRepository.findAll().stream().filter(Objects::nonNull).collect(Collectors.toList())).getBody();
    }
    @Override
    public BankDto getBankById(Long bankId) {
        Optional<Bank> byId = Optional.ofNullable(bankRepository.findById(bankId).orElseThrow(() -> new BankException(ApplicationConstant.BANK_NOT_AVAILABLE)));
            Bank bank=  byId.get();
            BankDto bankDto = new BankDto();
            BeanUtils.copyProperties(bank, bankDto);
            return bankDto;

    }

    @Override
    public ResponseEntity<String> updateBankById(Bank bank) {
        bankRepository.findById(bank.getBankId()).orElseThrow(() -> new BankException(ApplicationConstant.BANK_NOT_AVAILABLE));
        bankRepository.save(bank);
        log.info(ApplicationConstant.BANK_UPDATE);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApplicationConstant.BANK_UPDATE);

    }

    @Override
    public ResponseEntity<String> deleteBankById(Long bankId) {
        bankRepository.findById(bankId).orElseThrow(()-> new BankException(ApplicationConstant.BANK_NOT_AVAILABLE));
        bankRepository.deleteById(bankId);
        log.info(ApplicationConstant.BANK_DELETE);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApplicationConstant.BANK_DELETE);
    }

}
