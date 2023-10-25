package com.bankmanagement.service;

import com.bankmanagement.dto.BankDto;
import com.bankmanagement.dto.CustomerDto;
import com.bankmanagement.entity.Bank;
import com.bankmanagement.entity.Customer;
import com.bankmanagement.exception.CustomerException;
import com.bankmanagement.repository.BankRepository;
import com.bankmanagement.repository.CustomerRepository;
import com.bankmanagement.service.serviceimpl.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

public class CustomerServiceTest {
    //    @Autowired
//    private CustomerServiceImpl customerServiceImpl;
    @InjectMocks
    private CustomerServiceImpl customerService;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private BankRepository bankRepository;

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
    public void getCustomerById(){
        Bank bank = Bank.builder().bankId(1L).bankName("SBI").branchName("SBIMOhadi").ifscCode("SBIN123").address("Mohadi").build();
        Customer customer1 = Customer.builder().bank(bank).customerId(1l).firstName("Virat").lastName("Kohli").age(32).email("viratkohli@gmail.com").aadhaarNumber("222233444450l").contactNumber(9876543238L).address("MOhadi").panCardNumber("AAAS254KKL").dateOfBirth("1987-09-20").build();
        Mockito.when(customerRepository.findById(1l)).thenReturn(Optional.of(customer1));
        assertEquals(1,customerService.customerFindById(1l).size());

    }

    @Test
    public void deleteCustomerTest(){
        Bank bank = Bank.builder().bankId(1L).bankName("SBI").branchName("SBIMOhadi").ifscCode("SBIN123").address("Mohadi").build();
        Customer customer1 = Customer.builder().bank(bank).customerId(1l).firstName("Virat").lastName("Kohli").age(32).email("viratkohli@gmail.com").aadhaarNumber("222233444450l").contactNumber(9876543238L).address("MOhadi").panCardNumber("AAAS254KKL").dateOfBirth("1987-09-20").build();
        Mockito.when(customerRepository.findById(1l)).thenReturn(Optional.of(customer1));

        customerService.deleteCustomerById(1l);
    }

    @Test
    public void getAllCustomerFailureTest() {
        Bank bank = Bank.builder().bankId(1L).bankName("SBI").branchName("SBIMOhadi").ifscCode("SBIN123").address("Mohadi").build();
        Customer customer1 = Customer.builder().bank(bank).firstName("Virat").lastName("Kohli").age(32).email("viratkohli@gmail.com").aadhaarNumber("222233444455l").contactNumber(9876543234L).address("Mumbai").panCardNumber("AAAS234KKL").dateOfBirth("1987-09-20").build();
        List<Customer> customerList = new ArrayList<>();

        Mockito.doReturn(customerList).when(customerRepository).findAll();
        assertThrows(CustomerException.class, () -> {
            List<CustomerDto> allCustomer = customerService.getAllCustomer();
        });
    }

    @Test
    public void saveCustomer() {
        Optional<Bank> bank = Optional.ofNullable(Bank.builder().bankId(1L).bankName("SBI").branchName("SBIMOhadi").ifscCode("SBIN123").address("Mohadi").build());
        CustomerDto customerdto = CustomerDto.builder().bankId(1l).firstName("Virat").lastName("Kohli").age(32).email("viratkohli@gmail.com").aadhaarNumber("233333333333l").contactNumber(9876543234L).address("Mumbai").panCardNumber("AAAS234KKL").dateOfBirth("1987-09-20").build();
        Mockito.when(customerRepository.existsByAadhaarNumber("233333333333l")).thenReturn(false);
        when(bankRepository.findById(1l)).thenReturn(bank);

        customerService.saveCustomer(customerdto,bank.get().getBankId());
        assertEquals("Virat", customerdto.getFirstName());

    }

    @DisplayName("JUnit test for saveCustomer method which throws exception")
    @Test
    public void givenExistingAadhaarNumber_whenSaveCustomer_thenThrowsException(){
        Optional<Bank> bank = Optional.ofNullable(Bank.builder().bankId(1L).bankName("SBI").branchName("SBIMOhadi").ifscCode("SBIN123").address("Mohadi").build());
        Customer customer = Customer.builder().firstName("Virat").lastName("Kohli").age(32).email("viratkohli@gmail.com").aadhaarNumber("545678767678").contactNumber(9876543234L).address("Mumbai").panCardNumber("AAAS234KKL").dateOfBirth("1987-09-20").build();
        CustomerDto customerdto = CustomerDto.builder().bankId(1l).firstName("Virat").lastName("Kohli").age(32).email("viratkohli@gmail.com").aadhaarNumber("233333333333l").contactNumber(98744866L).address("Mumbai").panCardNumber("AAAS234KKL").dateOfBirth("1987-09-20").build();
        Mockito.doReturn(false).when(customerRepository).existsByAadhaarNumber(ArgumentMatchers.anyString());

        CustomerException exception = assertThrows(CustomerException.class, () -> {
            CustomerDto customerDto = customerService.saveCustomer(customerdto, bank.get().getBankId());
        });
        assertEquals(exception.getMessage(),"Invalid Contact Number");

    }
        @Test
    public void updateCustomerTest(){
        Optional<Bank> bank = Optional.ofNullable(Bank.builder().bankId(1L).bankName("SBI").branchName("SBIMOhadi").ifscCode("SBIN123").address("Mohadi").build());
        Optional<BankDto> bankDto = Optional.ofNullable(BankDto.builder().bankId(1L).bankName("SBI").branchName("SBIMOhadi").ifscCode("SBIN123").address("Mohadi").build());
        Customer customer = Customer.builder().bank(bank.get()).
                customerId(1l).firstName("Ram").lastName("Jadhav").age(32).email("viratkohli@gmail.com").aadhaarNumber("233333333333l").contactNumber(98765448L).address("Mumbai").panCardNumber("AAAS234KKL").dateOfBirth("1987-09-20").build();
        CustomerDto customerDto = CustomerDto.builder().bankId(1l).firstName("Ram").lastName("Kohli").age(32).email("viratkohli@gmail.com").aadhaarNumber("233333333333l").contactNumber(98765448L).address("Mumbai").panCardNumber("AAAS234KKL").dateOfBirth("1987-09-20").build();

        when(customerRepository.findById(customer.getCustomerId())).thenReturn( Optional.of(customer));
        customerDto.setEmail("ram@gmail.com");
        customerDto.setFirstName("Vivek");
        customerService.updateCustomer(customerDto, customer.getBank().getBankId());
        assertThat(customerDto.getEmail()).isEqualTo("ram@gmail.com");
        assertThat(customerDto.getFirstName()).isEqualTo("Vivek");
    }

    @Test
    void notFoundCustomerWithMessage() {
        Customer customer = new Customer();
        Throwable thrown = assertThrows(CustomerException.class, () ->
                customerService.customerFindById(customer.getCustomerId()));
        assertEquals("Customer not present", thrown.getMessage());
    }
}


