package com.bankmanagement.integration;

import com.bankmanagement.BankManagementApplication;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BankManagementApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class TransactionIntegrationTest {
}
