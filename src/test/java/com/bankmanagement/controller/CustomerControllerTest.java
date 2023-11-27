package com.bankmanagement.controller;

import com.bankmanagement.dto.BankDto;
import com.bankmanagement.dto.CustomerDto;
import com.bankmanagement.entity.Customer;
import com.bankmanagement.service.serviceimpl.CustomerServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CustomerControllerTest {
    @Autowired
    ObjectMapper objectMapper;
    CustomerDto customerDto;
    Customer customer;

    BankDto bankdto = BankDto.builder().bankId(1L).bankName("SBI").branchName("SBIMOhadi").ifscCode("SBIN123").address("Mohadi").build();
    @InjectMocks
    private CustomerController customerController;
    @Mock
    private CustomerServiceImpl customerService;
    @Autowired
    private MockMvc mvc;

    @BeforeEach
    void setUp() throws IOException {
        customerDto = objectMapper.readValue(new ClassPathResource("customerDto.json").getInputStream(), CustomerDto.class);
        customer = objectMapper.readValue(new ClassPathResource("customer.json").getInputStream(), Customer.class);

    }

    @Test
    public void createCustomerAPITest() {
        Mockito.when(customerService.saveCustomer(customerDto)).thenReturn(String.valueOf(customerDto));
         ResponseEntity<String> customerDtoResponseEntity =customerController.saveCustomer(customerDto);
        assertEquals(HttpStatus.OK, customerDtoResponseEntity.getStatusCode());
    }

    @Test
    public void updateCustomerAPITest() {
        ResponseEntity<CustomerDto> customerDtoResponseEntity = customerController.updateCustomerDto(customerDto);
        assertEquals(HttpStatus.OK, customerDtoResponseEntity.getStatusCode());
    }

    @Test
    public void getAllCustomerAPITest() {
        ResponseEntity<List<CustomerDto>> customerDtoResponseEntity =customerController.getAllCustomer();
        assertEquals(HttpStatus.OK, customerDtoResponseEntity.getStatusCode());
    }

    @Test
    public void getCustomerByIdAPITest() {
        ResponseEntity<CustomerDto> customerDtoResponseEntity =customerController.getCustomerById(customerDto.getCustomerId());
        assertEquals(HttpStatus.OK, customerDtoResponseEntity.getStatusCode());
    }
    @Test
    public void deleteCustomerByIdAPITest() {
        customerController.deleteCustomerById(customer.getCustomerId());

    }
}
