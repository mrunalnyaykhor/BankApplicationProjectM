package com.bankmanagement.integration;

import com.bankmanagement.dto.BankDto;
import com.bankmanagement.entity.Bank;
import com.bankmanagement.repository.BankRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class BankIntegrationTest {
    @LocalServerPort
    private int port;

    private String baseUrl = "http://localhost";
    private static RestTemplate restTemplate;

    @Autowired
    private BankRepository bankRepository;

    public BankIntegrationTest(int port) {
        this.port = port;
    }

    @BeforeAll
    public static void init(){
        restTemplate= new RestTemplate();
    }
    @BeforeEach
    public void beforeSetUp(){
        baseUrl = baseUrl + ":" +port ;
    }
    @AfterEach
    public void afterSetUp(){
        bankRepository.deleteAll();
    }
    Bank bank = Bank.builder().bankId(1L).bankName("SBI").branchName("SBIMOhadi").ifscCode("SBIN123").address("Mohadi").build();
    Bank bank1 = Bank.builder().bankId(2L).bankName("HDFC").branchName("HDFCMOhadi").ifscCode("HDFC123").address("Mohadi").build();
    BankDto bankdto = BankDto.builder().bankId(1L).bankName("SBI").branchName("SBIMohadi")
            .ifscCode("SBIN1234")
            .address("Mohadi").build();
    @Test
    void createBank(){

   BankDto bankDto = restTemplate.postForObject(baseUrl+"/saveBank",bankdto, BankDto.class);
   assertNotNull(bankDto);
   assertThat(bankDto.getBankId()).isNotNull();
    }
}
