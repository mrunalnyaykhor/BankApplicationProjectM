package com.bankmanagement.integration;

import com.bankmanagement.BankManagementApplication;
import com.bankmanagement.config.TestConfig;
import com.bankmanagement.dto.BankDto;
import com.bankmanagement.entity.Bank;
import com.bankmanagement.repository.BankRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.skyscreamer.jsonassert.JSONAssert;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

//@SpringBootTest(classes = BankManagementApplication.class,properties = "spring.config.name=TestConfig", webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@ContextConfiguration(classes = TestConfig.class)
@SpringBootTest(classes = BankManagementApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BankIntegrationTestClass {
    private static BankDto bankDto;
    private static Bank bank;
    private final HttpHeaders headers = new HttpHeaders();
    private static TestRestTemplate restTemplate;
    @Autowired
    ObjectMapper objectMapper;
    @LocalServerPort
    private int port;
    @Autowired
    private BankRepository bankRepository;

@BeforeAll
public static void init(){
    restTemplate = new TestRestTemplate();
}
    @BeforeEach
    public void setUp() throws IOException {
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
    public void testForSaveBank() {
        String URIToSaveBank = "/saveBank";
        HttpEntity<BankDto> entity = new HttpEntity<>(bankDto, headers);
        ResponseEntity<String> response = restTemplate.exchange(formFullURLWithPort(URIToSaveBank), HttpMethod.POST, entity, String.class);
       assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertEquals(1,bankRepository.findAll().size());

    }
    @Test
    @Sql(statements = "INSERT INTO Bank(BANK_ID, BANK_NAME, BRANCH_NAME, IFSC_CODE, ADDRESS) VALUES (2, 'SBI', 'SBIMohadi', 'SBIN0035961', 'Lakhni')"
,executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM BANK WHERE BANK_NAME ='SBIMohadi'",executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void testForgetAllBank()  {
        List<Bank> bankList1= restTemplate.getForObject(formFullURLWithPort("/getAllBank"),List.class);
        assertEquals(1,bankRepository.findAll().size());
        assertEquals(1,bankList1.size());


    }

    @Test
    @Sql(statements = "INSERT INTO Bank(BANK_ID, BANK_NAME, BRANCH_NAME, IFSC_CODE, ADDRESS) VALUES (2, 'SBI', 'SBIMohadi', 'SBIN0035961', 'Mohadi')"
            ,executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM BANK WHERE BANK_NAME ='SBIMohadi'",executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void getBankById() {
        ResponseEntity<String> forEntity = restTemplate.getForEntity(formFullURLWithPort("/getBankById/2"), String.class);
        assertThat(forEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void deleteBank() {

        Bank bank1 = Bank.builder().bankId(54L).bankName("SBI").branchName("SBIMOhadi").ifscCode("SBIN123").address("Mohadi").build();

        restTemplate.delete(formFullURLWithPort("/deleteBank/" + bank1.getBankId()));

        Bank bankAfterDeletion = bankRepository.findByBankId(bank1.getBankId());

        assertNull(bankAfterDeletion, "Bank should be deleted");

    }

    @Test
    @Sql(statements = "INSERT INTO Bank(BANK_ID, BANK_NAME, BRANCH_NAME, IFSC_CODE, ADDRESS) VALUES (2, 'SBI', 'SBIMohadi', 'SBIN0035961', 'Mohadi')"
            ,executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM BANK WHERE BANK_NAME ='SBIMohadi'",executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void testForUpdateBank()  {

        String URIToUpdateBank = "/updateBank/2";
        HttpEntity<BankDto> entity = new HttpEntity<>(bankDto, headers);
        ResponseEntity<String> response = restTemplate.exchange(formFullURLWithPort(URIToUpdateBank), HttpMethod.PUT, entity, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }


}
