package com.bankmanagement.repository;

import com.bankmanagement.entity.Bank;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;

@ExtendWith({SpringExtension.class})
@SpringBootTest
public class BankRepositoryTest {

    BankRepository bankRepository1 = mock(BankRepository.class);
    @Autowired
    private BankRepository bankRepository;


    @Test
    public void saveUserFailureWhenIfscCodeExistTest() {
       Bank bank = Bank.builder().bankId(1L).bankName("SBI").branchName("SBIMOhadi").ifscCode("SBIN123N").address("Mohadi").build();

        Mockito.when(bankRepository1.existsByIfscCode("SBIN123N")).thenReturn(true); // Specify
        boolean savedUser = bankRepository1.existsByIfscCode("SBIN123Nt");

        Assertions.assertNotEquals(bank.getIfscCode(), savedUser);
        assertThat(savedUser).isNotNull();
        assertThat(savedUser).isNotEqualTo(bank.getIfscCode());
    }
}
