package com.bankmanagement.controller;

import com.bankmanagement.dto.BankDto;
import com.bankmanagement.entity.Bank;
import com.bankmanagement.service.serviceimpl.BankServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class BankControllerTest {
    BankDto bankDto;
    Bank bank;
    BankDto bankDtoList1;
    @InjectMocks
    private BankController bankController;
    @Mock
    private BankServiceImpl bankService;


    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() throws IOException {
        bankDto = objectMapper.readValue(new ClassPathResource("BankDto.json").getInputStream(), BankDto.class);
        bank = objectMapper.readValue(new ClassPathResource("Bank.json").getInputStream(), Bank.class);
        bankDtoList1 = objectMapper.readValue(new ClassPathResource("BankDtoList.json").getInputStream(), BankDto.class);
    }

    @DisplayName("Junit test case for save Bank")
    @Test
    public void createBank() {

        when(bankService.saveBank(any(BankDto.class))).thenReturn(bankDto);
        ResponseEntity<BankDto> bankDtoResponseEntity = bankController.saveBanks(bankDto);
        assertEquals(HttpStatus.OK, bankDtoResponseEntity.getStatusCode());


    }

    @DisplayName("Junit test case for save Bank")
    @Test
    public void getAllBanks() throws IOException {
        List<BankDto> bankDtoList = new ArrayList<>();
        bankDtoList.add(bankDtoList1);

        ResponseEntity<List<BankDto>> allBank = bankController.getAllBank();
        assertEquals(HttpStatus.OK, allBank.getStatusCode());

    }

    @DisplayName("Junit test case  getBankById")
    @Test
    public void getAllBankById() {
   Bank bank = Bank.builder().bankId(1L).bankName("SBI").branchName("SBIMOhadi").ifscCode("SBIN123").address("Mohadi").build();

        ResponseEntity<List<BankDto>> bankById = bankController.getBankById(bank.getBankId());
        assertEquals(HttpStatus.OK, bankById.getStatusCode());

    }
    @DisplayName("Junit test case for deleteBankById")
    @Test
    public void deleteBankById() {
        Bank bank = Bank.builder().bankId(1L).bankName("SBI").branchName("SBIMOhadi").ifscCode("SBIN123").address("Mohadi").build();
         bankController.deleteBank(bank.getBankId());


    }
    @DisplayName("Junit test case for updateBankById")
    @Test
    public void updateBankById() {
        ResponseEntity<BankDto> bankDtoResponseEntity = bankController.updateBank(bankDto,1l);
        assertEquals(HttpStatus.OK, bankDtoResponseEntity.getStatusCode());


    }

}
