package com.bankmanagement.integration;

import com.bankmanagement.BankManagementApplication;
import com.bankmanagement.dto.BankDto;
import com.bankmanagement.dto.TransactionDto;
import com.bankmanagement.repository.BankRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.io.IOException;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)

@ExtendWith(SpringExtension.class)

@SpringBootTest(classes = BankManagementApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BankIntegrationTestClass {

    private final HttpHeaders headers = new HttpHeaders();
    private final TestRestTemplate restTemplate = new TestRestTemplate();

    @LocalServerPort
    private int port;
    @Autowired
    private BankRepository bankRepository;
    @Autowired
    ObjectMapper objectMapper;
    private static BankDto bankDto;
    @BeforeEach
    public void setUp() throws IOException {
       // bankDto = objectMapper.readValue(new ClassPathResource("BankDto.json").getInputStream(), BankDto.class);
        bankDto = objectMapper.readValue(new ClassPathResource("BankDto.json").getInputStream(), BankDto.class);

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
         BankDto bankDto1 = BankDto.builder().bankId(5L).bankName("SBI").branchName("SBKNPune").ifscCode("SLNP11304").address("Pardi_Nagpur").build();
        this.mapToJson(bankDto1);

        String URIToSaveBank = "/saveBank";
        HttpEntity<BankDto> entity = new HttpEntity<>(bankDto1,  headers);
        ResponseEntity<String> response = restTemplate.exchange(formFullURLWithPort(URIToSaveBank), HttpMethod.GET, entity, String.class);
         String responseInJson = response.getBody();
        assertEquals(HttpStatus.OK, response.getStatusCode());


    }


    @Test
    public void testForgetAllBank() throws JsonProcessingException {
        String inputJsonString = this.mapToJson(bankDto);
        //   String URIToSaveBank = "/saveBank";
        //   HttpEntity<BankDto> entity = new HttpEntity<>(bankDto, headers);
        //  ResponseEntity<String> response = restTemplate.exchange(formFullURLWithPort(URIToSaveBank), HttpMethod.POST, entity, String.class);
        String getBank = "/getBankById/1";

        String getBankResponse = restTemplate.getForObject(formFullURLWithPort(getBank), String.class);
        assertThat(getBankResponse).isEqualTo(inputJsonString);
    }

}
