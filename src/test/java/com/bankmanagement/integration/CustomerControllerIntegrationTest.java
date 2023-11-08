package com.bankmanagement.integration;

import com.bankmanagement.BankManagementApplication;
import com.bankmanagement.dto.CustomerDto;
import com.bankmanagement.entity.Customer;
import com.bankmanagement.repository.BankRepository;
import com.bankmanagement.repository.CustomerRepository;
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

public class CustomerControllerIntegrationTest {
    private final HttpHeaders headers = new HttpHeaders();
    private final TestRestTemplate restTemplate = new TestRestTemplate();
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    CustomerRepository customerRepository;
    Customer customer;
    CustomerDto customerDto;
    @LocalServerPort
    private int port;
    @Autowired
    private BankRepository bankRepository;

    @BeforeEach
    public void setUp() throws IOException {
        customer = objectMapper.readValue(new ClassPathResource("customer.json").getInputStream(), Customer.class);
        customerDto = objectMapper.readValue(new ClassPathResource("customerDto.json").getInputStream(), CustomerDto.class);

    }

    private String mapToJson(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }

    private String formFullURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

    //    @AfterEach
//    void deleteEntities() {
//        customerRepository.deleteAll();
//    }L
    @Test
    public void customerSaveIntegrationTest() {

        String URIToSaveCustomer = "/saveCustomer/12";
        HttpEntity<CustomerDto> entity = new HttpEntity<>(customerDto, headers);
        ResponseEntity<String> response = restTemplate.exchange(formFullURLWithPort(URIToSaveCustomer), HttpMethod.POST, entity, String.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void testForgetAllCustomer() {
        String URIToSaveCustomer = "/saveCustomer/12";
        HttpEntity<CustomerDto> entity = new HttpEntity<>(customerDto, headers);
        ResponseEntity<String> response = restTemplate.exchange(formFullURLWithPort(URIToSaveCustomer), HttpMethod.POST, entity, String.class);
       // Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        String getAllCustomer = "/getAllCustomer";

        String getCustomerResponse = restTemplate.getForObject(formFullURLWithPort(getAllCustomer), String.class);

        assertNotNull(getCustomerResponse, "Response body should not be null");
    }

    @Test
    public void getCustomerById() {

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(formFullURLWithPort("/getCustomerById/" + customerDto.getCustomerId()), String.class);
        assertNotNull(responseEntity, "Response body should not be null");
    }

    @Test
    public void updateCustomerById() {
        HttpEntity<CustomerDto> entity = new HttpEntity<>(customerDto, headers);
        ResponseEntity<String> response = restTemplate.exchange(formFullURLWithPort("/customer/43"), HttpMethod.PUT, entity, String.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void deleteCustomerIntegrationTest() {

        Optional<Customer> customer1 = customerRepository.findById(customer.getCustomerId());
        restTemplate.delete(formFullURLWithPort("/deleteCustomerById/42"));
    }


}
