package com.bankmanagement.controller;

import com.bankmanagement.dto.AccountDto;
import com.bankmanagement.dto.BankDto;
import com.bankmanagement.dto.CustomerDto;
import com.bankmanagement.entity.Account;
import com.bankmanagement.entity.Bank;
import com.bankmanagement.entity.Customer;
import com.bankmanagement.service.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.security.auth.login.AccountException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@Slf4j
public class AccountControllerTest {

    @Autowired
    ObjectMapper objectMapper;
    AccountDto accountDto;
    Account account;
    Customer customer;
    BankDto bankDto;
    Bank bank;
    private static Map deposit;
    private static Map withdrawal;
    CustomerDto customerDto;

    @InjectMocks
    private AccountController accountController;
    @Mock
    private AccountService accountService;

    @BeforeEach
    void setUp() throws IOException {
        accountDto = objectMapper.readValue(new ClassPathResource("accountDto.json").getInputStream(), AccountDto.class);
        account = objectMapper.readValue(new ClassPathResource("account.json").getInputStream(), Account.class);
        customerDto = objectMapper.readValue(new ClassPathResource("customerDto.json").getInputStream(), CustomerDto.class);
        customer = objectMapper.readValue(new ClassPathResource("customer.json").getInputStream(), Customer.class);
        bankDto = objectMapper.readValue(new ClassPathResource("bankDto.json").getInputStream(), BankDto.class);
        bank = objectMapper.readValue(new ClassPathResource("bank.json").getInputStream(), Bank.class);
        withdrawal = objectMapper.readValue(new ClassPathResource("withdrawal.json").getInputStream(), Map.class);
        deposit = objectMapper.readValue(new ClassPathResource("deposit.json").getInputStream(), Map.class);
    }

    @Test
    public void createAccountAPITest(){
        log.info("createAccountAPITest");
        Mockito.when(accountService.saveAccount(accountDto)).thenReturn(accountDto.toString());
        ResponseEntity<String> customerDtoResponseEntity = accountController.saveAccount(accountDto);
        assertEquals(HttpStatus.OK, customerDtoResponseEntity.getStatusCode());
    }

    @Test
    public void getAllAccountAPITest() throws AccountException {

        ResponseEntity<List<AccountDto>> accountDtoDtoResponseEntity = accountController.getAllAccountDetails();
        assertEquals(HttpStatus.OK, accountDtoDtoResponseEntity.getStatusCode());
    }
    @Test
    public void get_AccountById_APITest() throws AccountException {

        ResponseEntity<AccountDto> accountDtoDtoResponseEntity = accountController.getAccountById(account.getAccountId());
        assertEquals(HttpStatus.OK, accountDtoDtoResponseEntity.getStatusCode());
    }

    @Test
    public void updateAccountByIdAPITest() throws AccountException {
        ResponseEntity<String> accountDtoResponseEntity = accountController.updateAccountDto(accountDto);
        assertEquals(HttpStatus.OK, accountDtoResponseEntity.getStatusCode());
    }

    @Test
    public void deleteAccountByIdAPITest() throws AccountException {
        accountController.deleteAccountById(account.getAccountId());

    }

    @Test
    public void balanceCheckAPITest() throws AccountException {
        ResponseEntity <Double> responseEntity = accountController.balanceCheck(account.getAccountId());
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void depositAmountAPITest() throws AccountException {

        ResponseEntity<String> stringResponseEntity = accountController.depositAmount(account.getAccountId(), deposit);
        Assertions.assertThat(stringResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }


    @Test
    public void withdrawalAmountAPITest() throws AccountException {

        ResponseEntity<String> stringResponseEntity = accountController.withdrawalAmount(account.getAccountId(), withdrawal);
        Assertions.assertThat(stringResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }


    @Test
    public void blockAmountCheck() throws AccountException {
        ResponseEntity<String> stringResponseEntity = accountController.blockAccountCheck(account.getAccountId());
        Assertions.assertThat(stringResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void depositAmountTest() throws AccountException {

        ResponseEntity<String> stringResponseEntity = accountController.depositAmount(account.getAccountId(), deposit);
        Assertions.assertThat(stringResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }


}





