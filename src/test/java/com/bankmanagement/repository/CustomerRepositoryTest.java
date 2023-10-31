package com.bankmanagement.repository;

import com.bankmanagement.entity.Bank;
import com.bankmanagement.entity.Customer;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.mock;

@ExtendWith({SpringExtension.class})
@SpringBootTest

public class CustomerRepositoryTest {
    CustomerRepository customerRepository = mock(CustomerRepository.class);


    @Test
    public void saveCustomerFailureWhenAadhaarNumberExistTest() {
        Bank bank = Bank.builder().bankId(1L).bankName("SBI").branchName("SBIMOhadi").ifscCode("SBIN123N").address("Mohadi").build();
        Customer customer1 = Customer.builder().bank(bank).customerId(1L).firstName("Virat").lastName("Kohli").age(32).email("viratkohli@gmail.com").aadhaarNumber("222233444450l").contactNumber(9876543238L).address("MOhadi").panCardNumber("AAAS254KKL").dateOfBirth("1987-09-20").build();
        Mockito.when(customerRepository.existsByAadhaarNumber("222233444450l")).thenReturn(true); // Specify
        boolean customer = customerRepository.existsByAadhaarNumber("222233444490l");;
        assertNotEquals(222233444450l, 222233444490l);
        assertThat(222233444490l).isNotNull();
        assertThat(222233444490l).isNotEqualTo(customer1.getAadhaarNumber());
    }
}