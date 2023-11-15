package com.bankmanagement.integration;

import com.bankmanagement.BankManagementApplication;
import com.bankmanagement.dto.AccountDto;
import com.bankmanagement.dto.TransactionDto;
import com.bankmanagement.entity.Account;
import com.bankmanagement.entity.Transaction;
import com.bankmanagement.repository.TransactionRepository;
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

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = BankManagementApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class TransactionIntegrationTest {
    private static TransactionDto transactionDto;
    private static Transaction transaction;
    private static Account account;
    private static AccountDto accountDto;
    private final HttpHeaders headers = new HttpHeaders();
    private final TestRestTemplate restTemplate = new TestRestTemplate();
    @Autowired
    ObjectMapper objectMapper;
    @LocalServerPort
    private int port;
    @Autowired
    private TransactionRepository transactionRepository;

    @BeforeEach
    public void setUp() throws IOException {
        transaction = objectMapper.readValue(new ClassPathResource("transaction.json").getInputStream(), Transaction.class);
        transactionDto = objectMapper.readValue(new ClassPathResource("transactionDto.json").getInputStream(), TransactionDto.class);
        account = objectMapper.readValue(new ClassPathResource("account.json").getInputStream(), Account.class);
        accountDto = objectMapper.readValue(new ClassPathResource("accountDto.json").getInputStream(), AccountDto.class);
    }

    private String mapToJson(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }

    private String formFullURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

    @Test
    @Sql(statements = "INSERT INTO Bank(BANK_ID, BANK_NAME, BRANCH_NAME, IFSC_CODE, ADDRESS) VALUES (1, 'SBI', 'SBIMohadi', 'SBIN0035961', 'Mohadi'),(2, 'SBI', 'SBIMohadi', 'SBIN0035962', 'Mohadi')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "INSERT INTO Customer(CUSTOMER_ID, FIRST_NAME, LAST_NAME, AADHAAR_NUMBER, AGE, CONTACT_NUMBER, DATE_OF_BIRTH, EMAIL, PAN_CARD_NUMBER, ADDRESS,BANK_ID) VALUES (1, 'Aman', 'SHARMA', 555677787765, 36, 9876785435, '1987-08-25' ,'rohitsharma@gmail.com', 'BNZAB2318J', 'Mohadi',1),(2, 'Rohit', 'SHARMA', 555677787764, 33, 8876785435, '1985-08-25' ,'rohitsharmag@gmail.com', 'BNZAB2318H', 'Mohadi',2)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "INSERT INTO Account(ACCOUNT_ID,AADHAAR_NUM,ACCOUNT_NUMBER,AGE,AMOUNT,FIRST_NAME,LAST_NAME,BLOCKED,CONTACT_NUMBER,PAN_CARD_NUMBER,DATE_OF_BIRTH,EMAIL,BANK_ID,CUSTOMER_ID)VALUES(1,555677787765,44440,36,50090,'Aman','SHARMA',false,9876785435,'BNZAB2318J','1987-08-25','rohitsharma@gmail.com',1,1), (2,555677787764,44441,33,50091,'Rohit','SHARMA',false,8876785435,'BNZAB2318H','1985-08-25','rohitsharmag@gmail.com',2,2)")
    public void transferMoney() {
        HttpEntity<TransactionDto> entity = new HttpEntity<>(transactionDto, headers);
        ResponseEntity<String> response = restTemplate.exchange(formFullURLWithPort("/transferMoney"), HttpMethod.PUT, entity, String.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    @Sql(statements = "INSERT INTO Bank(BANK_ID, BANK_NAME, BRANCH_NAME, IFSC_CODE, ADDRESS) VALUES (1, 'SBI', 'SBIMohadi', 'SBIN0035961', 'Mohadi'),(2, 'SBI', 'SBIMohadi', 'SBIN0035962', 'Mohadi')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "INSERT INTO Customer(CUSTOMER_ID, FIRST_NAME, LAST_NAME, AADHAAR_NUMBER, AGE, CONTACT_NUMBER, DATE_OF_BIRTH, EMAIL, PAN_CARD_NUMBER, ADDRESS,BANK_ID) VALUES (1, 'Aman', 'SHARMA', 555677787765, 36, 9876785435, '1987-08-25' ,'rohitsharma@gmail.com', 'BNZAB2318J', 'Mohadi',1),(2, 'Rohit', 'SHARMA', 555677787764, 33, 8876785435, '1985-08-25' ,'rohitsharmag@gmail.com', 'BNZAB2318H', 'Mohadi',2)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "INSERT INTO Account(ACCOUNT_ID,AADHAAR_NUM,ACCOUNT_NUMBER,AGE,AMOUNT,FIRST_NAME,LAST_NAME,BLOCKED,CONTACT_NUMBER,PAN_CARD_NUMBER,DATE_OF_BIRTH,EMAIL,BANK_ID,CUSTOMER_ID)VALUES(1,555677787765,44440,36,50090,'Aman','SHARMA',false,9876785435,'BNZAB2318J','1987-08-25','rohitsharma@gmail.com',1,1), (2,555677787764,44441,33,50091,'Rohit','SHARMA',false,8876785435,'BNZAB2318H','1985-08-25','rohitsharmag@gmail.com',2,2)")
    @Sql(statements = "INSERT INTO Transaction(TRANSACTION_ID,ACCOUNT_NUMBER_FROM,ACCOUNT_NUMBER_TO,AMOUNT,DESCRIPTION,IFSC_CODE,NAME,TRANSACTION_DATE)VALUES(1,44440,44441,7600,'Hello','SBIN0035962','Rohit','2023-11-15')")
    public void getMiniStatement() {

        String statement = "/statement/44440/1";

        String getAccountResponse = restTemplate.getForObject(formFullURLWithPort(statement), String.class);
        assertNotNull(getAccountResponse, "Response body should not be null");
    }

}







