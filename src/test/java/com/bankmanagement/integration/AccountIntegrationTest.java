package com.bankmanagement.integration;

import com.bankmanagement.BankManagementApplication;
import com.bankmanagement.dto.AccountDto;
import com.bankmanagement.dto.BankDto;
import com.bankmanagement.dto.CustomerDto;
import com.bankmanagement.entity.Account;
import com.bankmanagement.entity.Bank;
import com.bankmanagement.entity.Customer;
import com.bankmanagement.repository.AccountRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.*;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = BankManagementApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class AccountIntegrationTest {
     AccountDto accountDto;
    Account account;
     Bank bank;
     Customer customer;
     CustomerDto customerDto;
     BankDto bankDto;


    private static Map deposit;
    private final HttpHeaders headers = new HttpHeaders();
    private final TestRestTemplate restTemplate = new TestRestTemplate();
    @Autowired
    ObjectMapper objectMapper;

    @LocalServerPort
    private int port;
    @Autowired
    private AccountRepository accountRepository;

    @BeforeEach
    public void setUp() throws IOException {
        account = objectMapper.readValue(new ClassPathResource("account.json").getInputStream(), Account.class);
        accountDto = objectMapper.readValue(new ClassPathResource("accountDto.json").getInputStream(), AccountDto.class);
        customerDto= objectMapper.readValue(new ClassPathResource("customerDto.json").getInputStream(),CustomerDto.class);
        deposit = objectMapper.readValue(new ClassPathResource("deposit.json").getInputStream(), Map.class);
        customer = objectMapper.readValue(new ClassPathResource("customer.json").getInputStream(), Customer.class);
        bank = objectMapper.readValue(new ClassPathResource("bank.json").getInputStream(), Bank.class);
        bankDto = objectMapper.readValue(new ClassPathResource("bankDto.json").getInputStream(), BankDto.class);
    }

    private String mapToJson(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }

    private String formFullURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }


    @Test
    public void accountSaveIntegrationTest() {
        String URIToSaveBank = "/saveBank";
        HttpEntity<BankDto> entity4 = new HttpEntity<>(bankDto, headers);
        ResponseEntity<BankDto> response4 = restTemplate.exchange(formFullURLWithPort(URIToSaveBank), HttpMethod.POST, entity4, BankDto.class);
        assertThat(response4.getStatusCode()).isEqualTo(HttpStatus.OK);
        Long bankId = response4.getBody().getBankId();

        HttpEntity<CustomerDto> entity = new HttpEntity<>(customerDto, headers);
        ResponseEntity<CustomerDto> response = restTemplate.exchange(formFullURLWithPort("/saveCustomer/" + bank.getBankId()), HttpMethod.POST, entity, CustomerDto.class);
        Long customerId = response.getBody().getCustomerId();



        HttpEntity<AccountDto> entity2 = new HttpEntity<>(accountDto, headers);
        ResponseEntity<String> response2 = restTemplate.exchange(formFullURLWithPort("/saveAccount/"+customerId+"/"+bankId), HttpMethod.POST, entity2, String.class);
        Assertions.assertThat(response2.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @Sql(statements = "INSERT INTO Bank(BANK_ID, BANK_NAME, BRANCH_NAME, IFSC_CODE, ADDRESS) VALUES (1, 'SBI', 'SBIMohadi', 'SBIN0035961', 'Mohadi')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "INSERT INTO Customer(CUSTOMER_ID, FIRST_NAME, LAST_NAME, AADHAAR_NUMBER, AGE, CONTACT_NUMBER, DATE_OF_BIRTH, EMAIL, PAN_CARD_NUMBER, ADDRESS,BANK_ID) VALUES (1, 'Aman', 'SHARMA', 555677787765, 36, 9876785435, '1987-08-25' ,'rohitsharma@gmail.com', 'BNZAB2318J', 'Mohadi',1)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements ="INSERT INTO Account(ACCOUNT_ID,FIRST_NAME,LAST_NAME,AADHAAR_NUM,AGE,BLOCKED,CONTACT_NUMBER,AMOUNT,ACCOUNT_NUMBER,PAN_CARD_NUMBER,EMAIL)VALUES(1,'Aman','SHARMA',555677787765,36,false,9876785435,'1987-08-25',500000,546765,'BNZAA2318J','rohitsharma@gmail.com')" )
    @Sql(statements = "DELETE FROM Customer WHERE FIRST_NAME ='Aman'", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getAllAccountTest() {
        String getAllAccount = "/getAllAccount";

        List<Account> accountList = restTemplate.getForObject(formFullURLWithPort(getAllAccount), List.class);

        assertNotNull(accountList, "Response body should not be null");
        assertEquals(1, accountList.size());
    }


    @Test
    public void getAccountById() {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(formFullURLWithPort("/getAccountById/" + account.getAccountId()), String.class);
        assertNotNull(responseEntity, "Response body should not be null");
    }

    @Test
    public void updateAccountById() {
        HttpEntity<AccountDto> entity = new HttpEntity<>(accountDto, headers);
        ResponseEntity<String> response = restTemplate.exchange(formFullURLWithPort("/accounts/" + account.getAccountId()), HttpMethod.PUT, entity, String.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void deleteAccountIntegrationTest() {

        Optional<Account> customer1 = accountRepository.findById(account.getAccountId());
        restTemplate.delete(formFullURLWithPort("/deleteAccountIdById/" + account.getAccountId()));
    }

    @Test
    public void balanceCheckAccountId() {
        Optional<Account> byId = accountRepository.findById(account.getAccountId());
        ResponseEntity<String> response = restTemplate.getForEntity(formFullURLWithPort("/checkAmountById/45/"), String.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    }


    @Test
    public void depositAmount() {

        HttpEntity<Map> account1 = new HttpEntity<>(deposit, headers);
        ResponseEntity<Map> response = restTemplate.exchange(formFullURLWithPort("/deposit/45"), HttpMethod.POST, account1, Map.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void blockAccountOrNotCheck() {
        Optional<Account> byId = accountRepository.findById(account.getAccountId());
        ResponseEntity<String> response = restTemplate.getForEntity(formFullURLWithPort("/blockAccountOrNot/45/"), String.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    }


}

