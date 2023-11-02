package com.bankmanagement.integration;

import com.bankmanagement.repository.CustomerRepository;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;


@WebMvcTest

public class CustomerControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CustomerRepository customerRepository;

    @AfterEach
    void deleteEntities() {

    }


}
