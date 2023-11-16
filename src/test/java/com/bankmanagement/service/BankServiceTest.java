package com.bankmanagement.service;

import com.bankmanagement.dto.BankDto;
import com.bankmanagement.entity.Bank;
import com.bankmanagement.exception.BankException;
import com.bankmanagement.repository.BankRepository;
import com.bankmanagement.service.serviceimpl.BankServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class BankServiceTest {
    Bank bank = Bank.builder().bankId(1L).bankName("SBI").branchName("SBIMOhadi").ifscCode("SBIN123").address("Mohadi").build();
    Bank bank1 = Bank.builder().bankId(2L).bankName("HDFC").branchName("HDFCMOhadi").ifscCode("HDFC123").address("Mohadi").build();
    BankDto bankdto = BankDto.builder().bankId(1L).bankName("SBI").branchName("SBIMohadi")
            .ifscCode("SBIN1234")
            .address("Mohadi").build();
    @InjectMocks
    private BankServiceImpl bankService;
    @Mock
    private BankRepository bankRepository;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("JUnit test for saveBankTest method")
    @Test
    public void saveBankTest() {
        when(bankRepository.existsByIfscCode(anyString())).thenReturn(false);
        BankDto response = bankService.saveBank(bankdto);
        assertEquals("SBI", response.getBankName());
        assertEquals("SBIN1234", response.getIfscCode());
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
    public void getBankList() {

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
        Bank response = bankService.getBankById(id);
        assertThat(response).isNotNull();

        Assertions.assertThat(response.getBankId()).isEqualTo(1);
    }

    @DisplayName("Junit Test for getBankById is empty method")
    @Test
    public void getBankByIdNullTest() {

        Mockito.when(bankRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.empty()); //Optional.ofNullable throw NoSuchElement
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

    }

    @DisplayName("Test case for updateBankByIdTest method")
    @Test
    public void updateBankByIdTest() {

        Mockito.when(bankRepository.existsByIfscCode(bankdto.getIfscCode())).thenReturn(false);
        Mockito.when(bankRepository.findById(bank.getBankId())).thenReturn(Optional.of(bank));
        BankDto bankDto = bankService.updateBankById(bankdto, bank.getBankId());
        assertThat(bankDto).isNotNull();
        assertThat(bankDto.getBankId()).isEqualTo(1L);
        Mockito.doReturn(true).when(bankRepository).existsByIfscCode(ArgumentMatchers.anyString());
        assertThrows(BankException.class, () -> {
            bankService.updateBankById(bankdto, bank.getBankId());
        });

    }


}


