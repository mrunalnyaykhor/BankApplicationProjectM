package com.bankmanagement.controller;

import com.bankmanagement.dto.TransactionDto;
import com.bankmanagement.service.serviceimpl.TransactionServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.security.auth.login.AccountException;
import java.io.IOException;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc

public class TransactionControllerTest {
    @Autowired
    ObjectMapper objectMapper;
    TransactionDto transactionDto;
    @InjectMocks
    private TransactionController transactionController;
    @Mock
    private TransactionServiceImpl transactionService;

    @BeforeEach
    void setUp() throws IOException {
        transactionDto = objectMapper.readValue(new ClassPathResource("transactionDto.json").getInputStream(), TransactionDto.class);

    }

    @Test
    public void transferMoneyAPITest() throws AccountException {

        ResponseEntity<String> stringResponseEntity = transactionController.transferMoney(transactionDto);

    }


}
