package com.bankmanagement.controller;

import com.bankmanagement.dto.TransactionDto;
import com.bankmanagement.service.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class MiniStateMentControllerTest {
    @Autowired
    ObjectMapper objectMapper;
    TransactionDto transactionDto;
    @InjectMocks
    private MiniStatementController miniStatementController;
    @Mock
    private TransactionService transactionService;

    @BeforeEach
    void setUp() throws IOException {
        transactionDto = objectMapper.readValue(new ClassPathResource("transactionDto.json").getInputStream(), TransactionDto.class);

    }

    @Test
    public void getMiniStatementAPITest() {

        LocalDate transactionDate = transactionDto.getTransactionDate();
        Long dayOfMonth = (long) transactionDate.getDayOfMonth();

        ResponseEntity<List<TransactionDto>> miniStatement = miniStatementController.getMiniStatement(transactionDto.getAccountNumberFrom(), dayOfMonth);


    }

}
