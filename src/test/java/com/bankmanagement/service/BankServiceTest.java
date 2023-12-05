package com.bankmanagement.service;

import com.bankmanagement.constant.ApplicationConstant;
import com.bankmanagement.dto.BankDto;
import com.bankmanagement.entity.Bank;
import com.bankmanagement.exception.BankException;
import com.bankmanagement.repository.BankRepository;
import com.bankmanagement.service.serviceimpl.BankServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class BankServiceTest {
    Bank bank = Bank.builder().bankName("SBI").branchName("SBIMOhadi")
            .ifscCode("SBIN1233478").address("Mohadi").build();
    Bank bank1 = Bank.builder().bankId(2L).bankName("HDFC").branchName("HDFCMOhadi")
            .ifscCode("HDFC1234567").address("Mohadi").build();
    BankDto bankdto = BankDto.builder().bankId(3L).bankName("SBI").branchName("SBIMohadi")
            .ifscCode("SBIN1273478")
            .address("Mohadi").build();
    BankDto bankdto1 = BankDto.builder().bankId(3L).bankName("ICICI").branchName("SBIMohadi")
            .ifscCode("SBIN123")
            .address("Mohadi").build();
    @InjectMocks
    private BankServiceImpl bankService;
    @Mock
    private BankRepository bankRepository;

    @DisplayName("JUnit test for saveBankTest method")
    @Test
    public void saveBankTest() {
        when(bankRepository.existsByIfscCode(anyString())).thenReturn(false);
        BankDto response = bankService.saveBank(bankdto);
        assertEquals("SBI", response.getBankName());
        assertEquals("SBIN1273478", response.getIfscCode());
    }
    @Test
    public void saveBankTest_Fail_When_BankIFSC_Code_LENGTH_NOTPROPER() {
        when(bankRepository.existsByIfscCode(bankdto1.getIfscCode())).thenReturn(false);

        assertThrows(BankException.class, () -> {
            bankService.saveBank(bankdto1);
        });
    }

    @DisplayName("Junit TestCase for failure when ifscCode is not match")
    @Test
    public void exceptionWhenIfscCodeIsNotExist() {
        Mockito.when(bankRepository.existsByIfscCode(anyString())).thenReturn(true);
        assertThrows(BankException.class, () -> {
            bankService.saveBank(bankdto);
        });
    }

    @DisplayName("Junit TestCase forfailure when bank is empty")
    @Test
    public void exceptionWhenBankIsNull() {
        List<Bank> bankList = new ArrayList<>();
        Mockito.when(bankRepository.findAll()).thenReturn(bankList);
        assertThrows(BankException.class, () -> {
            bankService.getAllBank();
        });
    }

    @DisplayName("JUnit test for getAllBanks method")
    @Test
    public void getBankList() throws ExecutionException, InterruptedException {

        Mockito.when(bankRepository.findAll()).thenReturn(List.of(bank, bank1));
        List<BankDto> bankList = bankService.getAllBank();
        assertThat(bankList).isNotNull(); //assertNotNull asserts that the object is not null.
        assertThat(bankList.size()).isEqualTo(2);
    }

    @DisplayName("Junit Test for getBankById method")
    @Test
    public void getBankByIdTest() {
        Long id = bank.getBankId();
        Mockito.when(bankRepository.findById(id)).thenReturn(Optional.of(bank)); //Optional.ofNullable throw NoSuchElement
        assertEquals(ApplicationConstant.BANK_GET_SUCCESSFULLY ,  bankService.getBankById(id));


    }

    @DisplayName("Junit Test for getBankById is empty method")
    @Test
    public void getBankByIdNullTest() {
        Mockito.when(bankRepository.findById(Mockito.any())).thenReturn(Optional.empty());
        assertThrows(BankException.class, () -> {
            bankService.getBankById(ArgumentMatchers.anyLong());
        });
    }

    @DisplayName("Test case for DeleteBankByIdTest method")
    @Test
    public void deleteBankByIdTest() {
        Long id = bank1.getBankId();
        Mockito.when(bankRepository.findById(id)).thenReturn(Optional.of(bank1));
        bankService.deleteBankById(id);
        verify(bankRepository, Mockito.times(1)).deleteById(id);

    }

    @DisplayName("Test case for updateBankByIdTest method")
    @Test
    public void updateBankByIdTest() {
        BankDto bankdto = BankDto.builder().bankId(3L).bankName("SBI").branchName("SBIMohadi")
                .ifscCode("SBIN1273478")
                .address("Mohadi").build();

        Mockito.when(bankRepository.findById(bankdto.getBankId())).thenReturn(Optional.of(bank));
        BankDto bankDto = bankService.updateBankById(bankdto);

        assertThat(bankDto.getBankId()).isNotNull();
        assertThat(bankDto.getBankId()).isEqualTo(3);


    }



}


