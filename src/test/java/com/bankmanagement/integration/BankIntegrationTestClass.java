package com.bankmanagement.integration;

import com.bankmanagement.BankManagementApplication;
import com.bankmanagement.config.TestConfig;
import com.bankmanagement.dto.BankDto;
import com.bankmanagement.entity.Bank;
import com.bankmanagement.repository.BankRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@ContextConfiguration(classes = TestConfig.class)
@SpringBootTest(classes = BankManagementApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BankIntegrationTestClass {
    private static BankDto bankDto;
    private static Bank bank;
    private static TestRestTemplate restTemplate;
    private final HttpHeaders headers = new HttpHeaders();
    @Autowired
    ObjectMapper objectMapper;
    @LocalServerPort
    private int port;
    @Autowired
    private BankRepository bankRepository;


    @BeforeAll
    public static void init() {
        restTemplate = new TestRestTemplate();
    }

    @BeforeEach
    public void setUp() throws IOException {
        bank = objectMapper.readValue(new ClassPathResource("bank.json").getInputStream(), Bank.class);
        bankDto = objectMapper.readValue(new ClassPathResource("bankDto.json").getInputStream(), BankDto.class);

    }


    private String formFullURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

    @Test
    public void testForSaveBank() {
        String URIToSaveBank = "/saveBank";
        HttpEntity<BankDto> entity = new HttpEntity<>(bankDto, headers);
        ResponseEntity<String> response = restTemplate.exchange(formFullURLWithPort(URIToSaveBank), HttpMethod.POST, entity, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertEquals(1, bankRepository.findAll().size());

    }

    @Test
    @Sql(statements = "INSERT INTO Bank(BANK_ID, BANK_NAME, BRANCH_NAME, IFSC_CODE, ADDRESS) VALUES (2, 'SBI', 'SBIMohadi', 'SBIN0035961', 'Lakhni')"
            , executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM BANK WHERE BANK_NAME ='SBIMohadi'", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void testForgetAllBank() {
        List bankList1 = restTemplate.getForObject(formFullURLWithPort("/getAllBank"), List.class);
        assertEquals(1, bankRepository.findAll().size());
        assertEquals(1, bankList1.size());
    }
    @Test
    public void testForgetAllBankFailureTest() {
       // Note Bank.json file set null all fields
        Bank bank1 = new Bank();
        HttpEntity<Bank> entity = new HttpEntity<>(bank1, headers);
        ResponseEntity<String> response = restTemplate.exchange(formFullURLWithPort("/getAllBank"),HttpMethod.GET,entity, String.class);

        assertEquals(400, response.getStatusCodeValue());

    }

    @Test
    @Sql(statements = "INSERT INTO Bank(BANK_ID, BANK_NAME, BRANCH_NAME, IFSC_CODE, ADDRESS) VALUES (2, 'SBI', 'SBIMohadi', 'SBIN0035961', 'Mohadi')"
            , executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM BANK WHERE BANK_NAME ='SBIMohadi'", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void getBankById() {
        ResponseEntity<String> forEntity = restTemplate.getForEntity(formFullURLWithPort("/getBankById/2"), String.class);
        assertThat(forEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
    @Test
    @Sql(statements = "INSERT INTO Bank(BANK_ID, BANK_NAME, BRANCH_NAME, IFSC_CODE, ADDRESS) VALUES (2, 'SBI', 'SBIMohadi', 'SBIN0035961', 'Mohadi')"
            , executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM BANK WHERE BANK_NAME ='SBIMohadi'", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void getBankByIdFailureTest() {
        ResponseEntity<String> forEntity = restTemplate.getForEntity(formFullURLWithPort("/getBankById/4"), String.class);
        assertThat(forEntity.getStatusCodeValue()).isEqualTo(400);
    }

    @Test
    public void deleteBank() {
        String deleteUri = "/deleteBank/" + bank.getBankId();
        HttpEntity<BankDto> entity = new HttpEntity<>(bankDto, headers);
        ResponseEntity<String> response = restTemplate.exchange(formFullURLWithPort(deleteUri), HttpMethod.DELETE, entity, String.class);
        assertEquals(400, response.getStatusCodeValue());


    }

    @Test
    public void deleteBankFailureTest() {

        String URIToSaveBank = "/deleteBank/78";
        HttpEntity<BankDto> entity = new HttpEntity<>(bankDto, headers);
        ResponseEntity<String> response = restTemplate.exchange(formFullURLWithPort(URIToSaveBank), HttpMethod.DELETE, entity, String.class);
        assertEquals(400, response.getStatusCodeValue());


    }

    @Test
    @Sql(statements = "INSERT INTO Bank(BANK_ID, BANK_NAME, BRANCH_NAME, IFSC_CODE, ADDRESS) VALUES (1, 'SBI', 'SBIMohadi', 'SBIN0035961', 'Mohadi')"
            , executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM BANK WHERE BANK_NAME ='SBIMohadi'", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testForUpdateBank() {

        String URIToUpdateBank = "/updateBank";
        HttpEntity<BankDto> entity = new HttpEntity<>(bankDto, headers);
        ResponseEntity<String> response = restTemplate.exchange(formFullURLWithPort(URIToUpdateBank), HttpMethod.PUT, entity, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @Sql(statements = "INSERT INTO Bank(BANK_ID, BANK_NAME, BRANCH_NAME, IFSC_CODE, ADDRESS) VALUES (1, 'SBI', 'SBIMohadi', 'SBIN0060703', 'Mohadi')"
            , executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM BANK WHERE BANK_NAME ='SBIMohadi'", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testForSaveBankFailWhenIFSCCODEEXIST() {

        String URIToUpdateBank = "/saveBank";
        HttpEntity<BankDto> entity = new HttpEntity<>(bankDto, headers);
        ResponseEntity<String> response = restTemplate.exchange(formFullURLWithPort(URIToUpdateBank), HttpMethod.POST, entity, String.class);
        assertEquals(400, response.getStatusCodeValue());


    }


}
