package com.bankmanagement.integration;

import com.bankmanagement.BankManagementApplication;
import com.bankmanagement.dto.BankDto;
import com.bankmanagement.entity.Bank;
import com.bankmanagement.repository.BankRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = BankManagementApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BankIntegrationTestClass {

    private static BankDto bankDto;
    private static Bank bank;
    private final HttpHeaders headers = new HttpHeaders();
    private final TestRestTemplate restTemplate = new TestRestTemplate();
    @Autowired
    ObjectMapper objectMapper;
    @LocalServerPort
    private int port;
    @Autowired
    private BankRepository bankRepository;

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
    public void testForSaveBank() throws JsonProcessingException {

        String URIToSaveBank = "/saveBank";
        HttpEntity<BankDto> entity = new HttpEntity<>(bankDto, headers);
        ResponseEntity<String> response = restTemplate.exchange(formFullURLWithPort(URIToSaveBank), HttpMethod.POST, entity, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);


    }


    @Test
    public void testForgetAllBank() throws JsonProcessingException {
        String getBank = "/getAllBank";

        String getBankResponse = restTemplate.getForObject(formFullURLWithPort(getBank), String.class);

        assertNotNull(getBankResponse, "Response body should not be null");

    }

    @Test
    public void getBankById() {
        ResponseEntity<String> forEntity = restTemplate.getForEntity(formFullURLWithPort("/getBankById/" + bank.getBankId()), String.class);
        assertThat(forEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void deleteBank() {

        Bank bank1 = Bank.builder().bankId(54L).bankName("SBI").branchName("SBIMOhadi").ifscCode("SBIN123").address("Mohadi").build();
        Bank bank2 = bankRepository.findByBankId(bank1.getBankId());
        restTemplate.delete(formFullURLWithPort("/deleteBank/" + bank1.getBankId()));

        Bank bankAfterDeletion = bankRepository.findByBankId(bank1.getBankId());

        assertNull(bankAfterDeletion, "Bank should be deleted");

    }

    @Test
    public void testForUpdateBank() throws JsonProcessingException {

        String URIToUpdateBank = "/updateBank/" + bankDto.getBankId();
        HttpEntity<BankDto> entity = new HttpEntity<>(bankDto, headers);
        ResponseEntity<String> response = restTemplate.exchange(formFullURLWithPort(URIToUpdateBank), HttpMethod.PUT, entity, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);


    }


}
