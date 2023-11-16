package com.bankmanagement.integration;

import com.bankmanagement.BankManagementApplication;
import com.bankmanagement.dto.BankDto;
import com.bankmanagement.dto.CustomerDto;
import com.bankmanagement.entity.Bank;
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
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
    BankDto bankDto;
    Bank bank;
    @LocalServerPort
    private int port;
    @Autowired
    private BankRepository bankRepository;

    @BeforeEach
    public void setUp() throws IOException {
        customer = objectMapper.readValue(new ClassPathResource("customer.json").getInputStream(), Customer.class);
        customerDto = objectMapper.readValue(new ClassPathResource("customerDto.json").getInputStream(), CustomerDto.class);
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
    public void customerSaveIntegrationTest() {

        String URIToSaveCustomer = "/saveCustomer/1";
        HttpEntity<CustomerDto> entity = new HttpEntity<>(customerDto, headers);
        ResponseEntity<String> response = restTemplate.exchange(formFullURLWithPort(URIToSaveCustomer), HttpMethod.POST, entity, String.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertEquals(1, customerRepository.findAll().size());

    }

    @Test
    @Sql(statements = "INSERT INTO Bank(BANK_ID, BANK_NAME, BRANCH_NAME, IFSC_CODE, ADDRESS) VALUES (1, 'SBI', 'SBIMohadi', 'SBIN0035961', 'Mohadi')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "INSERT INTO Customer(CUSTOMER_ID, FIRST_NAME, LAST_NAME, AADHAAR_NUMBER, AGE, CONTACT_NUMBER, DATE_OF_BIRTH, EMAIL, PAN_CARD_NUMBER, ADDRESS,BANK_ID) VALUES (1, 'Aman', 'SHARMA', 555677787765, 36, 9876785435, '1987-08-25' ,'rohitsharma@gmail.com', 'BNZAB2318J', 'Mohadi',1)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM Customer WHERE FIRST_NAME ='ROHIT'", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void failureCustomerSaveIntegrationTest_WhenAadhaarCardNumberExist() {

        String URIToSaveCustomer = "/saveCustomer/1";
        HttpEntity<CustomerDto> entity = new HttpEntity<>(customerDto, headers);
        ResponseEntity<String> response = restTemplate.exchange(formFullURLWithPort(URIToSaveCustomer), HttpMethod.POST, entity, String.class);
        Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(400);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

    }

    @Test
    @Sql(statements = "INSERT INTO Bank(BANK_ID, BANK_NAME, BRANCH_NAME, IFSC_CODE, ADDRESS) VALUES (1, 'SBI', 'SBIMohadi', 'SBIN0035962', 'Mohadi')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "INSERT INTO Customer(CUSTOMER_ID, FIRST_NAME, LAST_NAME, AADHAAR_NUMBER, AGE, CONTACT_NUMBER, DATE_OF_BIRTH, EMAIL, PAN_CARD_NUMBER, ADDRESS,BANK_ID) VALUES (1, 'Aman', 'SHARMA', 555674487765, 36, 2876785435, '1987-08-25' ,'rohitsharma@gmail.com', 'BNZAB2318J', 'Mohadi',1)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM Customer WHERE FIRST_NAME ='ROHIT'", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void failureCustomerSaveIntegrationTest_WhenContactNotValid() {

        String URIToSaveCustomer = "/saveCustomer/1";
        HttpEntity<CustomerDto> entity = new HttpEntity<>(customerDto, headers);
        ResponseEntity<String> response = restTemplate.exchange(formFullURLWithPort(URIToSaveCustomer), HttpMethod.POST, entity, String.class);
        assertThat(response.getStatusCodeValue()).isEqualTo(400);


    }


    @Test
    @Sql(statements = "INSERT INTO Bank(BANK_ID, BANK_NAME, BRANCH_NAME, IFSC_CODE, ADDRESS) VALUES (1, 'SBI', 'SBIMohadi', 'SBIN0035961', 'Mohadi')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "INSERT INTO Customer(CUSTOMER_ID, FIRST_NAME, LAST_NAME, AADHAAR_NUMBER, AGE, CONTACT_NUMBER, DATE_OF_BIRTH, EMAIL, PAN_CARD_NUMBER, ADDRESS,BANK_ID) VALUES (1, 'Aman', 'SHARMA', 555677787765, 36, 9876785435, '1987-08-25' ,'rohitsharma@gmail.com', 'BNZAB2318J', 'Mohadi',1)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM Customer WHERE FIRST_NAME ='ROHIT'", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getAllCustomerTest() {

        List<Customer> customerList = restTemplate.getForObject(formFullURLWithPort("/getAllCustomer"), List.class);
        assertNotNull(customerList);
        assertEquals(1, customerList.size());

    }

    @Test
    @Sql(statements = "INSERT INTO Bank(BANK_ID, BANK_NAME, BRANCH_NAME, IFSC_CODE, ADDRESS) VALUES (1, 'SBI', 'SBIMohadi', 'SBIN0035961', 'Mohadi')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
//    @Sql(statements = "INSERT INTO Customer(CUSTOMER_ID, FIRST_NAME, LAST_NAME, AADHAAR_NUMBER, AGE, CONTACT_NUMBER, DATE_OF_BIRTH, EMAIL, PAN_CARD_NUMBER, ADDRESS,BANK_ID) VALUES (1, 'Aman', 'SHARMA', 555677787765, 36, 9876785435, '1987-08-25' ,'rohitsharma@gmail.com', 'BNZAB2318J', 'Mohadi',1)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
//    @Sql(statements = "DELETE FROM Customer WHERE FIRST_NAME ='ROHIT'", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void failureWhen_CustomerData_NotPresent_getAllCustomerTest() {

        HttpEntity<Customer> entity = new HttpEntity<>(customer, headers);

        ResponseEntity<String> response = restTemplate.exchange(formFullURLWithPort("/getAllCustomer"), HttpMethod.GET, entity, String.class);
        assertThat(response.getStatusCodeValue()).isEqualTo(400);

    }

    @Test
//    @Sql(statements = "INSERT INTO Bank(BANK_ID, BANK_NAME, BRANCH_NAME, IFSC_CODE, ADDRESS) VALUES (1, 'SBI', 'SBIMohadi', 'SBIN0035961', 'Mohadi')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
//    @Sql(statements = "INSERT INTO Customer(CUSTOMER_ID, FIRST_NAME, LAST_NAME, AADHAAR_NUMBER, AGE, CONTACT_NUMBER, DATE_OF_BIRTH, EMAIL, PAN_CARD_NUMBER, ADDRESS,BANK_ID) VALUES (1, 'Aman', 'SHARMA', 555677787765, 36, 9876785435, '1987-08-25' ,'rohitsharma@gmail.com', 'BNZAB2318J', 'Mohadi',1)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
//    @Sql(statements = "DELETE FROM Customer WHERE FIRST_NAME ='ROHIT'", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void failureWhen_Bank_Data_NotPresent_getAllCustomerTest() {

        HttpEntity<Customer> entity = new HttpEntity<>(customer, headers);

        ResponseEntity<String> response = restTemplate.exchange(formFullURLWithPort("/getAllCustomer"), HttpMethod.GET, entity, String.class);
        assertThat(response.getStatusCodeValue()).isEqualTo(400);

    }

    @Test
    @Sql(statements = "INSERT INTO Bank(BANK_ID, BANK_NAME, BRANCH_NAME, IFSC_CODE, ADDRESS) VALUES (1, 'SBI', 'SBIMohadi', 'SBIN0035961', 'Mohadi')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "INSERT INTO CUSTOMER(CUSTOMER_ID, FIRST_NAME, LAST_NAME, AADHAAR_NUMBER, AGE, CONTACT_NUMBER, DATE_OF_BIRTH, EMAIL, PAN_CARD_NUMBER, ADDRESS, BANK_ID) VALUES (1, 'ROHIT', 'SHARMA', 555677787765, 36, 9876785435, '1987-08-25' ,'rohitsharma@gmail.com', 'BNZAB2318J', 'Mohadi', 1)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM CUSTOMER WHERE FIRST_NAME ='ROHIT'", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getCustomerById() {

        String URIToGetCustomer = "/getCustomerById/1";
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(formFullURLWithPort(URIToGetCustomer), String.class);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        assertNotNull(responseEntity, "Response body should not be null");

    }

    @Test
    @Sql(statements = "INSERT INTO Bank(BANK_ID, BANK_NAME, BRANCH_NAME, IFSC_CODE, ADDRESS) VALUES (1, 'SBI', 'SBIMohadi', 'SBIN0035961', 'Mohadi')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "INSERT INTO CUSTOMER(CUSTOMER_ID, FIRST_NAME, LAST_NAME, AADHAAR_NUMBER, AGE, CONTACT_NUMBER, DATE_OF_BIRTH, EMAIL, PAN_CARD_NUMBER, ADDRESS, BANK_ID) VALUES (1, 'ROHIT', 'SHARMA', 555677787765, 36, 9876785435, '1987-08-25' ,'rohitsharma@gmail.com', 'BNZAB2318J', 'Mohadi', 1)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM CUSTOMER WHERE FIRST_NAME ='ROHIT'", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void failure_WhenCustomerId_Not_Present_getCustomerById() {

        String URIToGetCustomer = "/getCustomerById/13";
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(formFullURLWithPort(URIToGetCustomer), String.class);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(400);


    }

    @Test
    @Sql(statements = "INSERT INTO Bank(BANK_ID, BANK_NAME, BRANCH_NAME, IFSC_CODE, ADDRESS) VALUES (1, 'SBI', 'SBIMohadi', 'SBIN0035961', 'Mohadi')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "INSERT INTO CUSTOMER(CUSTOMER_ID, FIRST_NAME, LAST_NAME, AADHAAR_NUMBER, AGE, CONTACT_NUMBER, DATE_OF_BIRTH, EMAIL, PAN_CARD_NUMBER, ADDRESS, BANK_ID) VALUES (1, 'ROHIT', 'SHARMA', 555677787765, 36, 9876785435, '1987-08-25' ,'rohitsharma@gmail.com', 'BNZAB2318J', 'Mohadi', 1)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM CUSTOMER WHERE FIRST_NAME ='ROHIT'", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void updateCustomerById() {
        HttpEntity<CustomerDto> entity = new HttpEntity<>(customerDto, headers);
        ResponseEntity<String> response = restTemplate.exchange(formFullURLWithPort("/customer/1"), HttpMethod.PUT, entity, String.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @Sql(statements = "INSERT INTO Bank(BANK_ID, BANK_NAME, BRANCH_NAME, IFSC_CODE, ADDRESS) VALUES (1, 'SBI', 'SBIMohadi', 'SBIN0035961', 'Mohadi')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "INSERT INTO CUSTOMER(CUSTOMER_ID, FIRST_NAME, LAST_NAME, AADHAAR_NUMBER, AGE, CONTACT_NUMBER, DATE_OF_BIRTH, EMAIL, PAN_CARD_NUMBER, ADDRESS, BANK_ID) VALUES (1, 'ROHIT', 'SHARMA', 555677787765, 36, 9876785435, '1987-08-25' ,'rohitsharma@gmail.com', 'BNZAB2318J', 'Mohadi', 1)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM CUSTOMER WHERE FIRST_NAME ='ROHIT'", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void failure_WhenCustomerId_Not_Present_updateCustomerById() {
        HttpEntity<CustomerDto> entity = new HttpEntity<>(customerDto, headers);
        ResponseEntity<String> response = restTemplate.exchange(formFullURLWithPort("/customer/13"), HttpMethod.PUT, entity, String.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @Sql(statements = "INSERT INTO Bank(BANK_ID, BANK_NAME, BRANCH_NAME, IFSC_CODE, ADDRESS) VALUES (1, 'SBI', 'SBIMohadi', 'SBIN0035961', 'Mohadi')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "INSERT INTO CUSTOMER(CUSTOMER_ID, FIRST_NAME, LAST_NAME, AADHAAR_NUMBER, AGE, CONTACT_NUMBER, DATE_OF_BIRTH, EMAIL, PAN_CARD_NUMBER, ADDRESS, BANK_ID) VALUES (1, 'ROHIT', 'SHARMA', 555677787765, 36, 9876785435, '1987-08-25' ,'rohitsharma@gmail.com', 'BNZAB2318J', 'Mohadi', 1)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM CUSTOMER WHERE FIRST_NAME ='ROHIT'", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void deleteCustomerIntegrationTest() {
        HttpEntity<CustomerDto> entity = new HttpEntity<>(customerDto, headers);
        ResponseEntity<String> response = restTemplate.exchange(formFullURLWithPort("/deleteCustomerById/1"), HttpMethod.DELETE, entity, String.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @Sql(statements = "INSERT INTO Bank(BANK_ID, BANK_NAME, BRANCH_NAME, IFSC_CODE, ADDRESS) VALUES (1, 'SBI', 'SBIMohadi', 'SBIN0035961', 'Mohadi')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "INSERT INTO CUSTOMER(CUSTOMER_ID, FIRST_NAME, LAST_NAME, AADHAAR_NUMBER, AGE, CONTACT_NUMBER, DATE_OF_BIRTH, EMAIL, PAN_CARD_NUMBER, ADDRESS, BANK_ID) VALUES (1, 'ROHIT', 'SHARMA', 555677787765, 36, 9876785435, '1987-08-25' ,'rohitsharma@gmail.com', 'BNZAB2318J', 'Mohadi', 1)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM CUSTOMER WHERE FIRST_NAME ='ROHIT'", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void failure_WhenCustomerId_Not_Present_deleteCustomerById() {
        HttpEntity<CustomerDto> entity = new HttpEntity<>(customerDto, headers);
        ResponseEntity<String> response = restTemplate.exchange(formFullURLWithPort("/deleteCustomerById/15"), HttpMethod.DELETE, entity, String.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }


}
