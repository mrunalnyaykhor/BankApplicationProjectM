package com.bankmanagement.integration;

import com.bankmanagement.BankManagementApplication;
import com.bankmanagement.dto.BankDto;
import com.bankmanagement.dto.CustomerDto;
import com.bankmanagement.dto.TransactionDto;
import com.bankmanagement.entity.Bank;
import com.bankmanagement.entity.Customer;
import com.bankmanagement.entity.Transaction;
import com.bankmanagement.repository.BankRepository;
import com.bankmanagement.repository.CustomerRepository;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = BankManagementApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class TransactionIntegrationTest {
    private static TransactionDto transactionDto;
    private static Transaction transaction;
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

    }

    private String mapToJson(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }

    private String formFullURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

}
