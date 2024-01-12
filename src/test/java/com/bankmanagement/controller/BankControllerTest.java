package com.bankmanagement.controller;

import com.bankmanagement.dto.BankDto;
import com.bankmanagement.entity.Bank;
import com.bankmanagement.service.serviceimpl.BankServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BankControllerTest {
    BankDto bankDto;
    Bank bank;
    @InjectMocks
    private BankController bankController;
    @Mock
    private BankServiceImpl bankService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() throws IOException {
        bankDto = objectMapper.readValue(new ClassPathResource("bankDto.json")
                .getInputStream(), BankDto.class);
        bank = objectMapper.readValue(new ClassPathResource("bank.json")
                .getInputStream(), Bank.class);
    }

    @DisplayName("Junit test case for save Bank")
    @Test
    public void createBank() {
        ResponseEntity<ResponseEntity<String>> bankDtoResponseEntity = bankController.saveBanks(bankDto);
        assertEquals(HttpStatus.OK, bankDtoResponseEntity.getStatusCode());


    }

    @DisplayName("Junit test case for save Bank")
    @Test
    public void getAllBanks() throws ExecutionException, InterruptedException {
        ResponseEntity<List<Bank>> allBank = bankController.getAllBank();
        assertEquals(HttpStatus.OK, allBank.getStatusCode());

    }

    @DisplayName("Junit test case  getBankById")
    @Test
    public void getAllBankById() {
        Bank bank = Bank.builder().bankId(1L).bankName("SBI").branchName("SBIMOhadi")
                .ifscCode("SBIN123").address("Mohadi").build();
    }

    @DisplayName("Junit test case for deleteBankById")
    @Test
    public void deleteBankById() {
        Bank bank = Bank.builder().bankId(1L).bankName("SBI").branchName("SBIMOhadi")
                .ifscCode("SBIN123").address("Mohadi").build();
        bankController.deleteBank(bank.getBankId());


    }

    @DisplayName("Junit test case for updateBankById")
    @Test
    public void updateBankById() {
        ResponseEntity<ResponseEntity<String>> bankDtoResponseEntity = bankController.updateBank(bank);
        assertEquals(HttpStatus.OK, bankDtoResponseEntity.getStatusCode());


    }

}
