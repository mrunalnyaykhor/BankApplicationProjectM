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
        Optional<Bank> bankId = bankRepository.findById(bankDto.getBankId());
        if(bankId.isPresent()){
            throw new BankException(ApplicationConstant.BANK_ID_ALREADY_PRESENT);
        }
        if (bankRepository.existsByIfscCode(bankDto.getIfscCode())) {
            log.error(ApplicationConstant.IFSC_CODE_ALREADY_EXIST);
            throw new BankException(ApplicationConstant.IFSC_CODE_ALREADY_EXIST);

        } else if (bankDto.getIfscCode().length()!=11) {
            throw new BankException(ApplicationConstant.IFSC_CODE_LENGTH_NOT_PROPER);
        } else {
            Bank bank = new Bank();
            BeanUtils.copyProperties(bankDto, bank);
            bankRepository.save(bank);
            log.info(ApplicationConstant.BANK_SAVE_SUCCESSFULLY);

        }
        return bankDto;
    }

    @Override
    public List<BankDto> getAllBank() {
        bankRepository.findAll().stream().findAny().orElseThrow(()-> new BankException(ApplicationConstant.BANK_NOT_AVAILABLE));
        log.info(ApplicationConstant.BANKS_GET_SUCCESSFULLY);
        return bankRepository.findAll().stream().filter(Objects::nonNull)
                .map(bank -> {
                    BankDto bankDto = new BankDto();
                    BeanUtils.copyProperties(bank, bankDto);
                    return bankDto;

                }).collect(Collectors.toList());

    }

    @Override
    public String getBankById(Long bankId) {

            Bank bank = bankRepository.findById(bankId)
                    .orElseThrow(() -> new BankException(ApplicationConstant.BANK_NOT_AVAILABLE));
             return String.format(ApplicationConstant.BANK_GET_SUCCESSFULLY).formatted(bank.getBankId());

    }

    @Override
    public BankDto updateBankById(BankDto bankDto)
    {
        Bank bank = bankRepository.findById(bankDto.getBankId())
                .orElseThrow(() -> new BankException(ApplicationConstant.BANK_NOT_AVAILABLE));
        BeanUtils.copyProperties(bankDto, bank);
            bankRepository.save(bank);
            log.info(ApplicationConstant.BANK_UPDATE);
            return bankDto;
    }

    @Override
    public String deleteBankById(Long bankId) {
        bankRepository.findById(bankId).orElseThrow(()-> new BankException(ApplicationConstant.BANK_NOT_AVAILABLE));

        bankRepository.deleteById(bankId);
        log.info(ApplicationConstant.BANK_DELETE);
        return ApplicationConstant.BANK_DELETE;
    }

}
