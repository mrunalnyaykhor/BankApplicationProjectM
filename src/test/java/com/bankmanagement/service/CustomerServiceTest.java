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

    Bank bank = Bank.builder().bankId(1L).bankName("SBI").branchName("SBIMOhadi").ifscCode("SBIN1234568").address("Mohadi").build();
    Customer customer1 = Customer.builder().bank(bank).customerId(1L).firstName("Virat").lastName("Kohli").age(32).email("viratkohli@gmail.com").aadhaarNumber("222233944450").contactNumber(9876543238L).address("Mohadi").panCardNumber("AAAS254KKL").dateOfBirth("1987-09-20").build();
    Optional<Bank> bankOptional = Optional.ofNullable(Bank.builder().bankId(1L).bankName("SBI").branchName("SBIMOhadi").ifscCode("SBIN1234560").address("Mohadi").build());
    CustomerDto customerdto = CustomerDto.builder().customerId(4L).bankId(1L).firstName("Sanjay").lastName("Datta").age(32).email("sanjay@gmail.com").aadhaarNumber("272235944450").contactNumber(8876643234L).address("Mumbai").panCardNumber("AANS234KKL").dateOfBirth("1983-09-20").build();
    @InjectMocks
    private CustomerServiceImpl customerService;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private BankRepository bankRepository;

    @DisplayName("Junit Test Case for saveCustomer")
    @Test
    public void saveCustomer() {
        Mockito.when(customerRepository.existsByAadhaarNumber(customerdto.getAadhaarNumber())).thenReturn(false);
        when(bankRepository.findById(customerdto.getBankId())).thenReturn(bankOptional);
        when(bankRepository.findById(customerdto.getBankId())).thenReturn(Optional.of(bank));
        String result = customerService.saveCustomer(customerdto);
        assertEquals(result, "customer Save successfully");

    }

    @DisplayName("JUnit test for getAllCustomer method")
    @Test
    public void getAllCustomerTest() {
        Mockito.when(customerRepository.findAll()).thenReturn(List.of(customer1));
        assertEquals(1, customerService.getAllCustomer().size());
    }

    @DisplayName("JUnit test for getCustomerById method")
    @Test
    public void getCustomerById() {
        Mockito.when(customerRepository.findById(customer1.getCustomerId())).thenReturn(Optional.of(customer1)); //Optional.of return empty
         customerService.customerFindById(customer1.getCustomerId());
    }

    @DisplayName("JUnit test for deleteCustomerTest method")
    @Test
    public void deleteCustomerTest() {
        Mockito.when(customerRepository.findById(customer1.getCustomerId())).thenReturn(Optional.of(customer1));
        String result = customerService.deleteCustomerById(customer1.getCustomerId());
        assertEquals(result, "Customer Id  deleted successfully....!!");
    }

    @DisplayName("JUnit test for getAllCustomerFailureTest method")
    @Test
    public void getAllCustomerFailureTest() {

        List<Customer> customerList = new ArrayList<>();

        Mockito.doReturn(customerList).when(customerRepository).findAll();
        assertThrows(CustomerException.class, () -> {
            customerService.getAllCustomer();
        });
    }


    @DisplayName("JUnit test for saveCustomer method which throws exception")
    @Test
    public void givenInvalidContactNumberThrowsException() {

        Mockito.when(customerRepository.existsByAadhaarNumber(customerdto.getAadhaarNumber())).thenReturn(false);
        when(bankRepository.findById(customerdto.getBankId())).thenReturn(Optional.of(bank));
        CustomerException exception = assertThrows(CustomerException.class, () -> {
            customerService.saveCustomer(customerdto);
        });
        assertEquals(exception.getMessage(), "customer contactNumber  is invalid");
    }

    @Test
    public void updateCustomerTest() {
        CustomerDto customerdto = CustomerDto.builder().customerId(1L).bankId(1L).firstName("Sanjay").lastName("Datta").age(32).email("sanjay@gmail.com").aadhaarNumber("222233944450").contactNumber(8876643234L).address("Mumbai").panCardNumber("AANS234KKL").dateOfBirth("1983-09-20").build();
        Mockito.when(customerRepository.findById(customerdto.getCustomerId())).thenReturn(Optional.of(customer1));
        Mockito.when(bankRepository.findById(customerdto.getBankId())).thenReturn(Optional.of(bank));
        customerdto.setEmail("ram@gmail.com");
        customerdto.setFirstName("Vivek");
        customerService.updateCustomer(customerdto);
        assertThat(customerdto.getEmail()).isEqualTo("ram@gmail.com");
        assertThat(customerdto.getFirstName()).isEqualTo("Vivek");

    }

    @Test
    void notFoundCustomerWithMessage() {
        Customer customer = new Customer();
        Throwable thrown = assertThrows(CustomerException.class, () ->
                customerService.customerFindById(customer.getCustomerId()));
        assertEquals("Customer not present in DataBase", thrown.getMessage());
    }

    @Test
    public void whenCustomerEmptyTest() {

        Mockito.when(customerRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(CustomerException.class, () -> {
            customerService.deleteCustomerById(ArgumentMatchers.anyLong());

        });
        assertEquals(exception.getMessage(), "Customer not present in DataBase");
    }

    @Test
    public void whenCustomerContactNumberNotStart_CorrectDigitTest() {
        Mockito.when(customerRepository.existsByAadhaarNumber(customerdto.getAadhaarNumber())).thenReturn(true);
        Mockito.when(bankRepository.findById(1L)).thenReturn(bankOptional);

         assertThrows(CustomerException.class, () -> {
            customerService.saveCustomer(customerdto);
        });

    }

    @Test
    public void customerNotPresentTest() {

        Mockito.when(customerRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());

        CustomerException exception = assertThrows(CustomerException.class, () -> {
            customerService.updateCustomer(customerdto);

        });
        assertEquals(exception.getMessage(), "Customer not present in DataBase");
    }

}


