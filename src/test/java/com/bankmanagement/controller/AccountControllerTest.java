package com.bankmanagement.controller;

import com.bankmanagement.dto.AccountDto;
import com.bankmanagement.dto.BankDto;
import com.bankmanagement.dto.CustomerDto;
import com.bankmanagement.entity.Account;
import com.bankmanagement.entity.Bank;
import com.bankmanagement.entity.Customer;
import com.bankmanagement.service.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
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

import javax.security.auth.login.AccountException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc

public class AccountControllerTest {
    @Autowired
    ObjectMapper objectMapper;
    AccountDto accountDto;
    Account account;
    Customer customer;
    BankDto bankDto;
    Bank bank;
    CustomerDto customerDto;
    private MockMvc mvc;
    @InjectMocks
    private AccountController accountController;
    @Mock
    private AccountService accountService;

    @BeforeEach
    void setUp() throws IOException {
        accountDto = objectMapper.readValue(new ClassPathResource("AccountDto.json").getInputStream(), AccountDto.class);
        account = objectMapper.readValue(new ClassPathResource("Account.json").getInputStream(), Account.class);
        customerDto = objectMapper.readValue(new ClassPathResource("customerDto.json").getInputStream(), CustomerDto.class);
        customer = objectMapper.readValue(new ClassPathResource("customer.json").getInputStream(), Customer.class);
        bankDto = objectMapper.readValue(new ClassPathResource("BankDto.json").getInputStream(), BankDto.class);
        bank = objectMapper.readValue(new ClassPathResource("Bank.json").getInputStream(), Bank.class);

    }

    @Test
    public void createAccountAPITest() throws AccountException {
        Mockito.when(accountService.saveAccount(accountDto, customer.getCustomerId(), bank.getBankId())).thenReturn(accountDto);
        ResponseEntity<AccountDto> customerDtoResponseEntity = accountController.saveAccount(accountDto, customerDto.getCustomerId(), bankDto.getBankId());
        assertEquals(HttpStatus.OK, customerDtoResponseEntity.getStatusCode());
    }

    @Test
    public void getAllAccountAPITest() throws AccountException {

        ResponseEntity<List<AccountDto>> accountDtoDtoResponseEntity = accountController.getAllAccountDetails();
        assertEquals(HttpStatus.OK, accountDtoDtoResponseEntity.getStatusCode());
    }

    @Test
    public void updateAccountByIdAPITest() throws AccountException {
        ResponseEntity<String> accountDtoResponseEntity = accountController.updateAccountDto(accountDto, account.getAccountId());
        assertEquals(HttpStatus.OK, accountDtoResponseEntity.getStatusCode());
    }

    @Test
    public void deleteAccountByIdAPITest() throws AccountException {
        accountController.deleteAccountById(accountDto.getAccountId());

    }

    @Test
    public void balanceCheckAPITest() throws AccountException {
        ResponseEntity<List<Double>> listResponseEntity = accountController.balanceCheck(account.getAccountId());

    }

    @Test
    public void depositAmountAPITest() throws AccountException {
        Map<String, Double> request = new HashMap<>();
        ResponseEntity<String> listResponseEntity = accountController.depositAmount(account.getAccountId(), request);

    }


    @Test
    public void withdrawalAmountAPITest() throws AccountException {
        Map<String, Double> request = new HashMap<>();
        ResponseEntity<String> stringResponseEntity = accountController.withdrawalAmount(account.getAccountId(), request);

    }


    @Test
    public void blockAmountCheck() throws AccountException {
        ResponseEntity<String> stringResponseEntity = accountController.blockAccountCheck(account.getAccountId());

    }

    @Test
    public void depositAmountTest() throws AccountException {
        Map<String, Double> request = new HashMap<>();

        ResponseEntity<String> stringResponseEntity = accountController.depositAmount(account.getAccountId(), request);
    }


}





