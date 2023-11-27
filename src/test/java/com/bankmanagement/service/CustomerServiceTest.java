package com.bankmanagement.service;

import com.bankmanagement.dto.CustomerDto;
import com.bankmanagement.entity.Bank;
import com.bankmanagement.entity.Customer;
import com.bankmanagement.exception.CustomerException;
import com.bankmanagement.repository.BankRepository;
import com.bankmanagement.repository.CustomerRepository;
import com.bankmanagement.service.serviceimpl.CustomerServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {
    Bank bank = Bank.builder().bankId(1L).bankName("SBI").branchName("SBIMOhadi").ifscCode("SBIN123").address("Mohadi").build();
    Customer customer1 = Customer.builder().bank(bank).customerId(1L).firstName("Virat").lastName("Kohli").age(32).email("viratkohli@gmail.com").aadhaarNumber("222233444450l").contactNumber(9876543238L).address("MOhadi").panCardNumber("AAAS254KKL").dateOfBirth("1987-09-20").build();
    Optional<Bank> bankOptional = Optional.ofNullable(Bank.builder().bankId(1L).bankName("SBI").branchName("SBIMOhadi").ifscCode("SBIN123").address("Mohadi").build());
    CustomerDto customerdto = CustomerDto.builder().customerId(1l).bankId(1L).firstName("Virat").lastName("Kohli").age(32).email("viratkohli@gmail.com").aadhaarNumber("233333333333l").contactNumber(9876543234L).address("Mumbai").panCardNumber("AAAS234KKL").dateOfBirth("1987-09-20").build();
    @InjectMocks
    private CustomerServiceImpl customerService;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private BankRepository bankRepository;

    @DisplayName("JUnit test for getAllCustomer method")
    @Test
    public void getAllCustomerTest() {
        Mockito.when(customerRepository.findAll()).thenReturn(List.of(customer1));
        assertEquals(1, customerService.getAllCustomer().size());
    }

    @DisplayName("JUnit test for getCustomerById method")
    @Test
    public void getCustomerById() {
        Mockito.when(customerRepository.findById(1L)).thenReturn(Optional.of(customer1)); //Optional.of return empty
        assertEquals(1, customerService.customerFindById(1L));
    }

    @DisplayName("JUnit test for deleteCustomerTest method")
    @Test
    public void deleteCustomerTest() {
        Mockito.when(customerRepository.findById(1L)).thenReturn(Optional.of(customer1));
        customerService.deleteCustomerById(1L);
    }

    @DisplayName("JUnit test for getAllCustomerFailureTest method")
    @Test
    public void getAllCustomerFailureTest() {

        List<Customer> customerList = new ArrayList<>();

        Mockito.doReturn(customerList).when(customerRepository).findAll();
        assertThrows(CustomerException.class, () -> {
            List<CustomerDto> allCustomer = customerService.getAllCustomer();
        });
    }

    @DisplayName("Junit Test Case for saveCustomer")
    @Test
    public void saveCustomer() {
        Mockito.when(customerRepository.existsByAadhaarNumber("233333333333l")).thenReturn(false);
        when(bankRepository.findById(1L)).thenReturn(bankOptional);
        customerService.saveCustomer(customerdto);
        assertThat(customerdto.getFirstName()).isEqualTo("Virat");
    }

    @DisplayName("JUnit test for saveCustomer method which throws exception")
    @Test
    public void givenExistingAadhaarNumberThrowsException() {
        CustomerDto customerdto = CustomerDto.builder().bankId(1L).firstName("Virat").lastName("Kohli").age(32).email("viratkohli@gmail.com").aadhaarNumber("233333333333l").contactNumber(98744866L).address("Mumbai").panCardNumber("AAAS234KKL").dateOfBirth("1987-09-20").build();
        Mockito.doReturn(false).when(customerRepository).existsByAadhaarNumber(ArgumentMatchers.anyString());

        CustomerException exception = assertThrows(CustomerException.class, () -> {
             customerService.saveCustomer(customerdto);
        });
        assertEquals(exception.getMessage(), "Invalid Contact Number");
    }

    @Test
    public void updateCustomerTest() {
        when(customerRepository.findById(customer1.getCustomerId())).thenReturn(Optional.of(customer1));
        customerdto.setEmail("ram@gmail.com");
        customerdto.setFirstName("Vivek");
        customerService.updateCustomer(customerdto);
        assertThat(customerdto.getEmail()).isEqualTo("ram@gmail.com");
        assertThat(customerdto.getFirstName()).isEqualTo("Vivek");
        customerService.updateCustomer(customerdto);
    }
    @Test
    public void testAadhaarNumberExist() {
        Mockito.when(customerRepository.existsByAadhaarNumber("233333333333l")).thenReturn(true);
        assertThrows(CustomerException.class, () -> {
            customerService.saveCustomer(customerdto);
        });
    }

    @Test
    void notFoundCustomerWithMessage() {
        Customer customer = new Customer();
        Throwable thrown = assertThrows(CustomerException.class, () ->
                customerService.customerFindById(customer.getCustomerId()));
        assertEquals("Customer not present", thrown.getMessage());
    }

    @Test
    public void whenCustomerEmptyTest() {

        Mockito.when(customerRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());

        assertThrows(CustomerException.class, () -> {
            customerService.deleteCustomerById(ArgumentMatchers.anyLong());

        });
    }

    @Test
    public void whenCustomerContactNumberNotCorrectTest() {
        CustomerDto customerdto = CustomerDto.builder().bankId(1L).firstName("Virat").lastName("Kohli").age(32).email("viratkohli@gmail.com").aadhaarNumber("233333333333l").contactNumber(4876543234L).address("Mumbai").panCardNumber("AAAS234KKL").dateOfBirth("1987-09-20").build();
        Mockito.when(customerRepository.existsByAadhaarNumber("233333333333l")).thenReturn(false);
        when(bankRepository.findById(1L)).thenReturn(bankOptional);
        assertThrows(CustomerException.class, () -> {
            customerService.saveCustomer(customerdto);
        });


    }

    @Test
    public void customerNotPresentTest() {

        Mockito.when(customerRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());

        assertThrows(CustomerException.class, () -> {
            customerService.updateCustomer(customerdto);

        });
    }

}


