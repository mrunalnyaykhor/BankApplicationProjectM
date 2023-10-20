package com.bankmanagement.service;

import antlr.TokenStreamRewriteEngine;
import com.bankmanagement.dto.CustomerDto;
import com.bankmanagement.entity.Bank;
import com.bankmanagement.entity.Customer;
import com.bankmanagement.exception.BankException;
import com.bankmanagement.exception.CustomerException;
import com.bankmanagement.repository.CustomerRepository;
import com.bankmanagement.service.serviceimpl.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)

public class CustomerServiceTest {
    @Autowired
    private CustomerServiceImpl customerServiceImpl;
    @InjectMocks
    private CustomerServiceImpl customerService;
    @Mock
    private CustomerRepository customerRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @DisplayName("JUnit test for getAllCustomer method")
    @Test
    public void getAllCustomerTest() {
        Bank bank = Bank.builder().bankId(1L).bankName("SBI").branchName("SBIMOhadi").ifscCode("SBIN123").address("Mohadi").build();
        Customer customer1 = Customer.builder().bank(bank).firstName("Virat").lastName("Kohli").age(32).email("viratkohli@gmail.com").aadhaarNumber("222233444455l").contactNumber(9876543234L).address("Mumbai").panCardNumber("AAAS234KKL").dateOfBirth("1987-09-20").build();
        Mockito.when(customerRepository.findAll()).thenReturn(List.of(customer1));
        assertEquals(1, customerService.getAllCustomer().size());
    }
    @Test
    public void getAllCustomerFailureTest(){
        Bank bank = Bank.builder().bankId(1L).bankName("SBI").branchName("SBIMOhadi").ifscCode("SBIN123").address("Mohadi").build();
        Customer customer1 = Customer.builder().bank(bank).firstName("Virat").lastName("Kohli").age(32).email("viratkohli@gmail.com").aadhaarNumber("222233444455l").contactNumber(9876543234L).address("Mumbai").panCardNumber("AAAS234KKL").dateOfBirth("1987-09-20").build();
       List<Customer> customerList = new ArrayList<>();
        Mockito.doReturn(customerList).when(customerRepository).findAll();
        assertThrows(CustomerException.class, () -> {
            customerService.getAllCustomer();
        });
    }

}
