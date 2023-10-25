package com.bankmanagement.service;

import com.bankmanagement.dto.BankDto;
import com.bankmanagement.entity.Bank;
import com.bankmanagement.exception.BankException;
import com.bankmanagement.repository.BankRepository;
import com.bankmanagement.service.serviceimpl.BankServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class BankServiceTest {

    @InjectMocks
    private BankServiceImpl bankService;
    @Mock
    private BankRepository bankRepository;
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {

        MockitoAnnotations.openMocks(this);

    }

    @DisplayName("JUnit test for saveBankTest method")
    @Test
    public void saveBankTest() {

        BankDto bank1 = BankDto.builder().bankName("SBI").branchName("SBIMohadi")
                .ifscCode("SBIN1234")
                .address("Mohadi").build();
        when(bankRepository.existsByIfscCode(anyString())).thenReturn(false);

        BankDto response = bankService.saveBank(bank1);
        assertEquals("SBI", response.getBankName());
        assertEquals("SBIN1234", response.getIfscCode());

    }

    @DisplayName("JUnit test for getAllBanks method")
    @Test
    public void getBankList() {
        Bank bank = Bank.builder().bankId(1L).bankName("SBI").branchName("SBIMOhadi").ifscCode("SBIN123").address("Mohadi").build();
        Bank bank1 = Bank.builder().bankId(2L).bankName("HDFC").branchName("HDFCMOhadi").ifscCode("HDFC123").address("Mohadi").build();
        Mockito.when(bankRepository.findAll()).thenReturn(List.of(bank1, bank1));
        List<BankDto> bankList = bankService.getAllBank();

        assertThat(bankList).isNotNull();
        assertThat(bankList.size()).isEqualTo(2);
    }

    @DisplayName("Junit Test for getBankById method")
    @Test
    public void getBankByIdTest() {
        BankDto bank6 = BankDto.builder().bankId(1L).bankName("SBI").branchName("SBIMohadi")
                .ifscCode("SBIN1234")
                .address("Mohadi").build();
        Bank bank7 = Bank.builder().bankId(1L).bankName("SBI").branchName("SBIMohadi")
                .ifscCode("SBIN1234")
                .address("Mohadi").build();
        Long id = bank6.getBankId();

        Mockito.when(bankRepository.findById(id)).thenReturn(Optional.ofNullable(bank7));
        List<BankDto> response = bankService.getBankById(id);
        assertThat(response).isNotNull();
        assertThat(response.size()).isEqualTo(1);
    }
    @DisplayName("Test case for DeleteBankByIdTest method")
    @Test
    public void deleteBankByIdTest() {
        Bank bank1 = Bank.builder().bankId(1L).bankName("SBI").branchName("SBIMohadi")
                .ifscCode("SBIN1234")
                .address("Mohadi").build();
        Long id = bank1.getBankId();
        Mockito.when(bankRepository.findById(id)).thenReturn(Optional.ofNullable(bank1));
        bankService.deleteBankById(id);
        bankService.deleteBankById(10l);
    }

    @DisplayName("Test case for updateBankByIdTest method")
    @Test
    public void updateBankByIdTest() {
        BankDto bank1 = BankDto.builder().bankId(1L).bankName("SBI").branchName("SBIMohadi")
                .ifscCode("SBIN1234")
                .address("Mohadi").build();
        Bank bank = Bank.builder().bankId(1L).bankName("SBI").branchName("SBIMohadi")
                .ifscCode("SBIN1234")
                .address("Mohadi").build();
        Mockito.when(bankRepository.findById(bank.getBankId())).thenReturn(Optional.ofNullable(bank));

        Mockito.when(bankRepository.existsByIfscCode(bank1.getIfscCode())).thenReturn(false);
        BankDto bankDto = bankService.updateBankById(bank1, bank.getBankId());
        assertThat(bankDto).isNotNull();
        assertThat(bankDto.getBankId()).isEqualTo(1L);

        Mockito.doReturn(true).when(bankRepository).existsByIfscCode(ArgumentMatchers.anyString());
        assertThrows(BankException.class, () -> {
            bankService.updateBankById(bank1, bank.getBankId());
        });

    }


}


