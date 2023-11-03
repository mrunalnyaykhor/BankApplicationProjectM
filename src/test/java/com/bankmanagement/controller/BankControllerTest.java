package com.bankmanagement.controller;

import com.bankmanagement.dto.BankDto;
import com.bankmanagement.dto.CustomerDto;
import com.bankmanagement.entity.Bank;
import com.bankmanagement.entity.Customer;
import com.bankmanagement.service.serviceimpl.BankServiceImpl;
import com.bankmanagement.service.serviceimpl.CustomerServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BankControllerTest {
    BankDto bankDto;
    Bank bank;
    BankDto bankDtoList1;
    @InjectMocks
    private BankController bankController;
    @Mock
    private BankServiceImpl bankService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() throws IOException {
        bankDto = objectMapper.readValue(new ClassPathResource("bankDto.json").getInputStream(), BankDto.class);
        bank = objectMapper.readValue(new ClassPathResource("bank.json").getInputStream(), Bank.class);
      //  bankDtoList1 = objectMapper.readValue(new ClassPathResource("bankDtoList.json").getInputStream(), BankDto.class);
    }

    @DisplayName("Junit test case for save Bank")
    @Test
    public void createBank() {
        ResponseEntity<BankDto> bankDtoResponseEntity = bankController.saveBanks(bankDto);
        assertEquals(HttpStatus.OK, bankDtoResponseEntity.getStatusCode());


    }

    @DisplayName("Junit test case for save Bank")
    @Test
    public void getAllBanks() {
        ResponseEntity<List<BankDto>> allBank = bankController.getAllBank();
        assertEquals(HttpStatus.OK, allBank.getStatusCode());

    }

    @DisplayName("Junit test case  getBankById")
    @Test
    public void getAllBankById() {
        Bank bank = Bank.builder().bankId(1L).bankName("SBI").branchName("SBIMOhadi").ifscCode("SBIN123").address("Mohadi").build();

//        ResponseEntity<List<BankDto>> bankById = bankController.getBankById(bank.getBankId());
//        assertEquals(HttpStatus.OK, bankById.getStatusCode());

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
        ResponseEntity<BankDto> bankDtoResponseEntity = bankController.updateBank(bankDto, 1L);
        assertEquals(HttpStatus.OK, bankDtoResponseEntity.getStatusCode());


    }

}
