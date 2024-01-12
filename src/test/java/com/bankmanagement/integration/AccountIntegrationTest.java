package com.bankmanagement.integration;

import com.bankmanagement.BankManagementApplication;
import com.bankmanagement.constant.ApplicationConstant;
import com.bankmanagement.dto.AccountDto;
import com.bankmanagement.dto.BankDto;
import com.bankmanagement.dto.CustomerDto;
import com.bankmanagement.entity.Account;
import com.bankmanagement.entity.Bank;
import com.bankmanagement.entity.Customer;
import com.bankmanagement.repository.AccountRepository;
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
import java.util.Objects;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = BankManagementApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class AccountIntegrationTest {
    private static Map deposit;
    private static Map withdrawal;
    private final HttpHeaders headers = new HttpHeaders();
    private final TestRestTemplate restTemplate = new TestRestTemplate();
    AccountDto accountDto;
    Account account;
    Bank bank;
    Customer customer;
    CustomerDto customerDto;
    BankDto bankDto;
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
        customerDto = objectMapper.readValue(new ClassPathResource("customerDto.json").getInputStream(), CustomerDto.class);
        withdrawal = objectMapper.readValue(new ClassPathResource("withdrawal.json").getInputStream(), Map.class);
        deposit = objectMapper.readValue(new ClassPathResource("deposit.json").getInputStream(), Map.class);
        customer = objectMapper.readValue(new ClassPathResource("customer.json").getInputStream(), Customer.class);
        bank = objectMapper.readValue(new ClassPathResource("bank.json").getInputStream(), Bank.class);
        bankDto = objectMapper.readValue(new ClassPathResource("bankDto.json").getInputStream(), BankDto.class);
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
        BankDto bankId = Objects.requireNonNull(response4.getBody());

        HttpEntity<CustomerDto> entity = new HttpEntity<>(customerDto, headers);
        ResponseEntity<CustomerDto> response3 = restTemplate.exchange(formFullURLWithPort("/saveCustomer/" + bankId), HttpMethod.POST, entity, CustomerDto.class);
        Long customerId = Objects.requireNonNull(response3.getBody()).getCustomerId();


        HttpEntity<AccountDto> entity2 = new HttpEntity<>(accountDto, headers);
        ResponseEntity<String> response2 = restTemplate.exchange(formFullURLWithPort("/saveAccount/" + customerId + "/" + bankId), HttpMethod.POST, entity2, String.class);
        Assertions.assertThat(response2.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void accountSaveIntegrationTest_Failure_When_BankNotExist() {
        String URIToSaveBank = "/saveBank";
        HttpEntity<BankDto> entity4 = new HttpEntity<>(bankDto, headers);
        ResponseEntity<BankDto> response4 = restTemplate.exchange(formFullURLWithPort(URIToSaveBank), HttpMethod.POST, entity4, BankDto.class);
        assertThat(response4.getStatusCode()).isEqualTo(HttpStatus.OK);
        Long bankId = null;

        HttpEntity<CustomerDto> entity = new HttpEntity<>(customerDto, headers);
        ResponseEntity<CustomerDto> response = restTemplate.exchange(formFullURLWithPort("/saveCustomer/" + bankId), HttpMethod.POST, entity, CustomerDto.class);
        Long customerId = response.getBody().getCustomerId();


        HttpEntity<AccountDto> entity2 = new HttpEntity<>(accountDto, headers);
        ResponseEntity<String> response2 = restTemplate.exchange(formFullURLWithPort("/saveAccount/" + customerId + "/" + bankId), HttpMethod.POST, entity2, String.class);
        Assertions.assertThat(response2.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void accountSaveIntegrationTest_Failure_When_CustomerNotExist() {
        String URIToSaveBank = "/saveBank";
        HttpEntity<BankDto> entity4 = new HttpEntity<>(bankDto, headers);
        ResponseEntity<BankDto> response4 = restTemplate.exchange(formFullURLWithPort(URIToSaveBank), HttpMethod.POST, entity4, BankDto.class);
        assertThat(response4.getStatusCode()).isEqualTo(HttpStatus.OK);
        Long bankId = Objects.requireNonNull(response4.getBody()).getBankId();

        HttpEntity<CustomerDto> entity = new HttpEntity<>(customerDto, headers);
        restTemplate.exchange(formFullURLWithPort("/saveCustomer/" + bank.getBankId()), HttpMethod.POST, entity, CustomerDto.class);
        Long customerId1 = null;

        HttpEntity<AccountDto> entity2 = new HttpEntity<>(accountDto, headers);
        ResponseEntity<String> response2 = restTemplate.exchange(formFullURLWithPort("/saveAccount/" + customerId1 + "/" + bankId), HttpMethod.POST, entity2, String.class);
        Assertions.assertThat(response2.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }


    @Test
    @Sql(statements = "INSERT INTO Bank(BANK_ID, BANK_NAME, BRANCH_NAME, IFSC_CODE, ADDRESS) VALUES (1, 'SBI', 'SBIMohadi', 'SBIN0035961', 'Mohadi')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "INSERT INTO Customer(CUSTOMER_ID, FIRST_NAME, LAST_NAME, AADHAAR_NUMBER, AGE, CONTACT_NUMBER, DATE_OF_BIRTH, EMAIL, PAN_CARD_NUMBER, ADDRESS,BANK_ID) VALUES (1, 'Aman', 'SHARMA', 555677787765, 36, 9876785435, '1987-08-25' ,'rohitsharma@gmail.com', 'BNZAB2318J', 'Mohadi',1)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "INSERT INTO Account(ACCOUNT_ID,AADHAAR_NUM,ACCOUNT_NUMBER,AGE,AMOUNT,FIRST_NAME,LAST_NAME,BLOCKED,CONTACT_NUMBER,PAN_CARD_NUMBER,DATE_OF_BIRTH,EMAIL,BANK_ID,CUSTOMER_ID)VALUES(1,555677787765,546765,36,500000,'Aman','SHARMA',false,9876785435,'BNZAA2318J','1987-08-25','rohitsharma@gmail.com',1,1)")
    @Sql(statements = "DELETE FROM Account WHERE FIRST_NAME ='Aman'", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)

    public void getAllAccountTest() {
        String getAllAccount = "/getAllAccount";

        List accountList = restTemplate.getForObject(formFullURLWithPort(getAllAccount), List.class);

        assertNotNull(accountList, "Response body should not be null");
        assertEquals(1, accountList.size());
    }

    @Test


    public void getAllAccountTest_failureWhen_Account_Not_Exist() {
        String getAllAccount = "/getAllAccount";

        HttpEntity<Account> entity = new HttpEntity<>(account, headers);
        //      List<String> accountList = Collections.singletonList(restTemplate.getForObject(formFullURLWithPort(getAllAccount), String.class));
        ResponseEntity<String> response = restTemplate.exchange(formFullURLWithPort(getAllAccount), HttpMethod.GET, entity, String.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

    }


    @Test
    @Sql(statements = "INSERT INTO Bank(BANK_ID, BANK_NAME, BRANCH_NAME, IFSC_CODE, ADDRESS) VALUES (1, 'SBI', 'SBIMohadi', 'SBIN0035961', 'Mohadi')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "INSERT INTO Customer(CUSTOMER_ID, FIRST_NAME, LAST_NAME, AADHAAR_NUMBER, AGE, CONTACT_NUMBER, DATE_OF_BIRTH, EMAIL, PAN_CARD_NUMBER, ADDRESS,BANK_ID) VALUES (1, 'Aman', 'SHARMA', 555677787765, 36, 9876785435, '1987-08-25' ,'rohitsharma@gmail.com', 'BNZAB2318J', 'Mohadi',1)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "INSERT INTO Account(ACCOUNT_ID,AADHAAR_NUM,ACCOUNT_NUMBER,AGE,AMOUNT,FIRST_NAME,LAST_NAME,BLOCKED,CONTACT_NUMBER,PAN_CARD_NUMBER,DATE_OF_BIRTH,EMAIL,BANK_ID,CUSTOMER_ID)VALUES(1,555677787765,546765,36,500000,'Aman','SHARMA',false,9876785435,'BNZAA2318J','1987-08-25','rohitsharma@gmail.com',1,1)")
    @Sql(statements = "DELETE FROM Account WHERE FIRST_NAME ='Aman'", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getAccountById() {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(formFullURLWithPort("/getAccountById/1"), String.class);
        assertNotNull(responseEntity, ApplicationConstant.RESPONSE_SHOULD_NOT_NULL);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @Sql(statements = "INSERT INTO Bank(BANK_ID, BANK_NAME, BRANCH_NAME, IFSC_CODE, ADDRESS) VALUES (1, 'SBI', 'SBIMohadi', 'SBIN0035961', 'Mohadi')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "INSERT INTO Customer(CUSTOMER_ID, FIRST_NAME, LAST_NAME, AADHAAR_NUMBER, AGE, CONTACT_NUMBER, DATE_OF_BIRTH, EMAIL, PAN_CARD_NUMBER, ADDRESS,BANK_ID) VALUES (1, 'Aman', 'SHARMA', 555677787765, 36, 9876785435, '1987-08-25' ,'rohitsharma@gmail.com', 'BNZAB2318J', 'Mohadi',1)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "INSERT INTO Account(ACCOUNT_ID,AADHAAR_NUM,AGE,AMOUNT,FIRST_NAME,LAST_NAME,BLOCKED,CONTACT_NUMBER,PAN_CARD_NUMBER,DATE_OF_BIRTH,EMAIL,BANK_ID,CUSTOMER_ID)VALUES(1,555677787765,36,500000,'Aman','SHARMA',false,9876785435,'BNZAA2318J','1987-08-25','rohitsharma@gmail.com',1,1)")
    @Sql(statements = "DELETE FROM Account WHERE FIRST_NAME ='Aman'", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void updateAccountById() {
        HttpEntity<AccountDto> entity = new HttpEntity<>(accountDto, headers);
        ResponseEntity<String> response = restTemplate.exchange(formFullURLWithPort("/updateAccount"), HttpMethod.PUT, entity, String.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @Sql(statements = "INSERT INTO Bank(BANK_ID, BANK_NAME, BRANCH_NAME, IFSC_CODE, ADDRESS) VALUES (1, 'SBI', 'SBIMohadi', 'SBIN0035961', 'Mohadi')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "INSERT INTO Customer(CUSTOMER_ID, FIRST_NAME, LAST_NAME, AADHAAR_NUMBER, AGE, CONTACT_NUMBER, DATE_OF_BIRTH, EMAIL, PAN_CARD_NUMBER, ADDRESS,BANK_ID) VALUES (1, 'Aman', 'SHARMA', 555677787765, 36, 9876785435, '1987-08-25' ,'rohitsharma@gmail.com', 'BNZAB2318J', 'Mohadi',1)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "INSERT INTO Account(ACCOUNT_ID,AADHAAR_NUM,ACCOUNT_NUMBER,AGE,AMOUNT,FIRST_NAME,LAST_NAME,BLOCKED,CONTACT_NUMBER,PAN_CARD_NUMBER,DATE_OF_BIRTH,EMAIL,BANK_ID,CUSTOMER_ID)VALUES(1,555677787765,546765,36,500000,'Aman','SHARMA',false,9876785435,'BNZAA2318J','1987-08-25','rohitsharma@gmail.com',1,1)")
    @Sql(statements = "DELETE FROM Account WHERE FIRST_NAME ='Aman'", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void deleteAccountIntegrationTest() {


        restTemplate.delete(formFullURLWithPort("/deleteAccountIdById/1"), HttpMethod.DELETE, String.class);
    }

    @Test
    @Sql(statements = "INSERT INTO Bank(BANK_ID, BANK_NAME, BRANCH_NAME, IFSC_CODE, ADDRESS) VALUES (1, 'SBI', 'SBIMohadi', 'SBIN0035961', 'Mohadi'),(2, 'SBI', 'SBIMohadi', 'SBIN0035962', 'Mohadi')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "INSERT INTO Customer(CUSTOMER_ID, FIRST_NAME, LAST_NAME, AADHAAR_NUMBER, AGE, CONTACT_NUMBER, DATE_OF_BIRTH, EMAIL, PAN_CARD_NUMBER, ADDRESS,BANK_ID) VALUES (1, 'Aman', 'SHARMA', 555677787765, 36, 9876785435, '1987-08-25' ,'rohitsharma@gmail.com', 'BNZAB2318J', 'Mohadi',1),(2, 'Rohit', 'SHARMA', 555677787764, 33, 8876785435, '1985-08-25' ,'rohitsharmag@gmail.com', 'BNZAB2318H', 'Mohadi',2)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "INSERT INTO Account(ACCOUNT_ID,AADHAAR_NUM,ACCOUNT_NUMBER,AGE,AMOUNT,FIRST_NAME,LAST_NAME,BLOCKED,CONTACT_NUMBER,PAN_CARD_NUMBER,DATE_OF_BIRTH,EMAIL,BANK_ID,CUSTOMER_ID)VALUES(1,555677787765,44440,36,50090,'Aman','SHARMA',false,9876785435,'BNZAB2318J','1987-08-25','rohitsharma@gmail.com',1,1), (2,555677787764,44441,33,50091,'Rohit','SHARMA',false,8876785435,'BNZAB2318H','1985-08-25','rohitsharmag@gmail.com',2,2)")
    @Sql(statements = "DELETE FROM Account WHERE FIRST_NAME ='Aman'", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void balanceCheckAccountId() {

        ResponseEntity<Double> response = restTemplate.getForEntity(formFullURLWithPort("/checkAmountById/1"), Double.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    }


    @Test
    @Sql(statements = "INSERT INTO Bank(BANK_ID, BANK_NAME, BRANCH_NAME, IFSC_CODE, ADDRESS) VALUES (1, 'SBI', 'SBIMohadi', 'SBIN0035961', 'Mohadi'),(2, 'SBI', 'SBIMohadi', 'SBIN0035962', 'Mohadi')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "INSERT INTO Customer(CUSTOMER_ID, FIRST_NAME, LAST_NAME, AADHAAR_NUMBER, AGE, CONTACT_NUMBER, DATE_OF_BIRTH, EMAIL, PAN_CARD_NUMBER, ADDRESS,BANK_ID) VALUES (1, 'Aman', 'SHARMA', 555677787765, 36, 9876785435, '1987-08-25' ,'rohitsharma@gmail.com', 'BNZAB2318J', 'Mohadi',1),(2, 'Rohit', 'SHARMA', 555677787764, 33, 8876785435, '1985-08-25' ,'rohitsharmag@gmail.com', 'BNZAB2318H', 'Mohadi',2)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "INSERT INTO Account(ACCOUNT_ID,AADHAAR_NUM,ACCOUNT_NUMBER,AGE,AMOUNT,FIRST_NAME,LAST_NAME,BLOCKED,CONTACT_NUMBER,PAN_CARD_NUMBER,DATE_OF_BIRTH,EMAIL,BANK_ID,CUSTOMER_ID)VALUES(1,555677787765,44440,36,50090,'Aman','SHARMA',false,9876785435,'BNZAB2318J','1987-08-25','rohitsharma@gmail.com',1,1), (2,555677787764,44441,33,50091,'Rohit','SHARMA',false,8876785435,'BNZAB2318H','1985-08-25','rohitsharmag@gmail.com',2,2)")
    public void depositAmount() {
        HttpEntity<Map<String, Account>> account1 = new HttpEntity<>(deposit, headers);
        ResponseEntity<String> response = restTemplate.exchange(formFullURLWithPort("/deposit/1"), HttpMethod.POST, account1, String.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @Sql(statements = "INSERT INTO Bank(BANK_ID, BANK_NAME, BRANCH_NAME, IFSC_CODE, ADDRESS) VALUES (1, 'SBI', 'SBIMohadi', 'SBIN0035961', 'Mohadi'),(2, 'SBI', 'SBIMohadi', 'SBIN0035962', 'Mohadi')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "INSERT INTO Customer(CUSTOMER_ID, FIRST_NAME, LAST_NAME, AADHAAR_NUMBER, AGE, CONTACT_NUMBER, DATE_OF_BIRTH, EMAIL, PAN_CARD_NUMBER, ADDRESS,BANK_ID) VALUES (1, 'Aman', 'SHARMA', 555677787765, 36, 9876785435, '1987-08-25' ,'rohitsharma@gmail.com', 'BNZAB2318J', 'Mohadi',1),(2, 'Rohit', 'SHARMA', 555677787764, 33, 8876785435, '1985-08-25' ,'rohitsharmag@gmail.com', 'BNZAB2318H', 'Mohadi',2)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "INSERT INTO Account(ACCOUNT_ID,AADHAAR_NUM,ACCOUNT_NUMBER,AGE,AMOUNT,FIRST_NAME,LAST_NAME,BLOCKED,CONTACT_NUMBER,PAN_CARD_NUMBER,DATE_OF_BIRTH,EMAIL,BANK_ID,CUSTOMER_ID)VALUES(1,555677787765,44440,36,50090,'Aman','SHARMA',false,9876785435,'BNZAB2318J','1987-08-25','rohitsharma@gmail.com',1,1), (2,555677787764,44441,33,50091,'Rohit','SHARMA',false,8876785435,'BNZAB2318H','1985-08-25','rohitsharmag@gmail.com',2,2)")
    public void depositAmount_Failure_When_AmountLess() {

        HttpEntity<Map<String, Account>> account1 = new HttpEntity<>(deposit, headers);
        ResponseEntity<String> response = restTemplate.exchange(formFullURLWithPort("/deposit/1"), HttpMethod.POST, account1, String.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @Sql(statements = "INSERT INTO Bank(BANK_ID, BANK_NAME, BRANCH_NAME, IFSC_CODE, ADDRESS) VALUES (1, 'SBI', 'SBIMohadi', 'SBIN0035961', 'Mohadi'),(2, 'SBI', 'SBIMohadi', 'SBIN0035962', 'Mohadi')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "INSERT INTO Customer(CUSTOMER_ID, FIRST_NAME, LAST_NAME, AADHAAR_NUMBER, AGE, CONTACT_NUMBER, DATE_OF_BIRTH, EMAIL, PAN_CARD_NUMBER, ADDRESS,BANK_ID) VALUES (1, 'Aman', 'SHARMA', 555677787765, 36, 9876785435, '1987-08-25' ,'rohitsharma@gmail.com', 'BNZAB2318J', 'Mohadi',1),(2, 'Rohit', 'SHARMA', 555677787764, 33, 8876785435, '1985-08-25' ,'rohitsharmag@gmail.com', 'BNZAB2318H', 'Mohadi',2)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "INSERT INTO Account(ACCOUNT_ID,AADHAAR_NUM,ACCOUNT_NUMBER,AGE,AMOUNT,FIRST_NAME,LAST_NAME,BLOCKED,CONTACT_NUMBER,PAN_CARD_NUMBER,DATE_OF_BIRTH,EMAIL,BANK_ID,CUSTOMER_ID)VALUES(1,555677787765,44440,36,50090,'Aman','SHARMA',false,9876785435,'BNZAB2318J','1987-08-25','rohitsharma@gmail.com',1,1), (2,555677787764,44441,33,50091,'Rohit','SHARMA',false,8876785435,'BNZAB2318H','1985-08-25','rohitsharmag@gmail.com',2,2)")
    public void withdrawalAmountIntegrationTest() {
        HttpEntity<Map<String, Account>> account2 = new HttpEntity<>(withdrawal, headers);
        ResponseEntity<String> response = restTemplate.exchange(formFullURLWithPort("/1/withdrawalAmount"), HttpMethod.POST, account2, String.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @Sql(statements = "INSERT INTO Bank(BANK_ID, BANK_NAME, BRANCH_NAME, IFSC_CODE, ADDRESS) VALUES (1, 'SBI', 'SBIMohadi', 'SBIN0035961', 'Mohadi'),(2, 'SBI', 'SBIMohadi', 'SBIN0035962', 'Mohadi')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "INSERT INTO Customer(CUSTOMER_ID, FIRST_NAME, LAST_NAME, AADHAAR_NUMBER, AGE, CONTACT_NUMBER, DATE_OF_BIRTH, EMAIL, PAN_CARD_NUMBER, ADDRESS,BANK_ID) VALUES (1, 'Aman', 'SHARMA', 555677787765, 36, 9876785435, '1987-08-25' ,'rohitsharma@gmail.com', 'BNZAB2318J', 'Mohadi',1),(2, 'Rohit', 'SHARMA', 555677787764, 33, 8876785435, '1985-08-25' ,'rohitsharmag@gmail.com', 'BNZAB2318H', 'Mohadi',2)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "INSERT INTO Account(ACCOUNT_ID,AADHAAR_NUM,ACCOUNT_NUMBER,AGE,AMOUNT,FIRST_NAME,LAST_NAME,BLOCKED,CONTACT_NUMBER,PAN_CARD_NUMBER,DATE_OF_BIRTH,EMAIL,BANK_ID,CUSTOMER_ID)VALUES(1,555677787765,44440,36,50090,'Aman','SHARMA',false,9876785435,'BNZAB2318J','1987-08-25','rohitsharma@gmail.com',1,1), (2,555677787764,44441,33,50091,'Rohit','SHARMA',false,8876785435,'BNZAB2318H','1985-08-25','rohitsharmag@gmail.com',2,2)")
    public void blockAccountOrNotCheck() {
        ResponseEntity<String> response = restTemplate.getForEntity(formFullURLWithPort("/blockAccountOrNot/1"), String.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    }

    @Test
    @Sql(statements = "INSERT INTO Bank(BANK_ID, BANK_NAME, BRANCH_NAME, IFSC_CODE, ADDRESS) VALUES (1, 'SBI', 'SBIMohadi', 'SBIN0035961', 'Mohadi'),(2, 'SBI', 'SBIMohadi', 'SBIN0035962', 'Mohadi')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "INSERT INTO Customer(CUSTOMER_ID, FIRST_NAME, LAST_NAME, AADHAAR_NUMBER, AGE, CONTACT_NUMBER, DATE_OF_BIRTH, EMAIL, PAN_CARD_NUMBER, ADDRESS,BANK_ID) VALUES (1, 'Aman', 'SHARMA', 555677787765, 36, 9876785435, '1987-08-25' ,'rohitsharma@gmail.com', 'BNZAB2318J', 'Mohadi',1),(2, 'Rohit', 'SHARMA', 555677787764, 33, 8876785435, '1985-08-25' ,'rohitsharmag@gmail.com', 'BNZAB2318H', 'Mohadi',2)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "INSERT INTO Account(ACCOUNT_ID,AADHAAR_NUM,ACCOUNT_NUMBER,AGE,AMOUNT,FIRST_NAME,LAST_NAME,BLOCKED,CONTACT_NUMBER,PAN_CARD_NUMBER,DATE_OF_BIRTH,EMAIL,BANK_ID,CUSTOMER_ID)VALUES(1,555677787765,44440,36,50090,'Aman','SHARMA',false,9876785435,'BNZAB2318J','1987-08-25','rohitsharma@gmail.com',1,1), (2,555677787764,44441,33,50091,'Rohit','SHARMA',false,8876785435,'BNZAB2318H','1985-08-25','rohitsharmag@gmail.com',2,2)")
    public void blockAccountOrNotCheck_Failure_WhenAccountNotPresent() {
        ResponseEntity<String> response = restTemplate.getForEntity(formFullURLWithPort("/blockAccountOrNot/44"), String.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

    }


}

