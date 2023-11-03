package com.bankmanagement.integration;

import com.bankmanagement.dto.CustomerDto;
import com.bankmanagement.entity.Bank;
import com.bankmanagement.entity.Customer;
import com.bankmanagement.repository.CustomerRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.*;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;


//@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class CustomerControllerIntegrationTest {
    private final HttpHeaders headers = new HttpHeaders();
    private final TestRestTemplate restTemplate = new TestRestTemplate();
    @Autowired
    ObjectMapper objectMapper;
    CustomerDto customerDto;
    Customer customer;
    Bank bank;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CustomerRepository customerRepository;
    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() throws IOException {
        customerDto = objectMapper.readValue(new ClassPathResource("customerDto.json").getInputStream(), CustomerDto.class);
        customer = objectMapper.readValue(new ClassPathResource("customer.json").getInputStream(), Customer.class);
        bank = objectMapper.readValue(new ClassPathResource("bank.json").getInputStream(), Bank.class);
    }

    @AfterEach
    void deleteEntities() {
        customerRepository.deleteAll();
    }

    private String mapToJson(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }

    private String formFullURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

    //    @PostMapping("/saveCustomer/{bankId}")
//    public ResponseEntity<CustomerDto> saveCustomer(@Valid @RequestBody CustomerDto customerDto, @PathVariable Long bankId)
//    {
//        return ResponseEntity.ok( customerService.saveCustomer(customerDto,bankId));
//
//    }
    @Test
    public void customerSaveIntegrationTest() throws JsonProcessingException {
        String s = this.mapToJson(customerDto);

        String URIToSaveBank = "/saveCustomer/1";
        HttpEntity<CustomerDto> entity = new HttpEntity<>(customerDto, headers);
        ResponseEntity<String> response = restTemplate.exchange(formFullURLWithPort(URIToSaveBank), HttpMethod.POST, entity, String.class);
        String responseInJson = response.getBody();
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }


}
