package com.bankmanagement.repository;

import com.bankmanagement.entity.Account;
import com.bankmanagement.entity.Bank;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

@ExtendWith({SpringExtension.class})
@SpringBootTest

public class AccountRepositoryTest {
    AccountRepository accountRepository = mock(AccountRepository.class);
    @Test
    public void saveUserFailureWhenIfscCodeExistTest() {
        Account account = Account.builder().customerId(1L).bankId(1L).accountId(1L)
                .accountNumber(4567888).amount(777788.00)
                .isBlocked(false).build();

        Mockito.when(accountRepository.findByAccountNumber(account.getAccountNumber())).thenReturn(account); // Specify
        Account byAccountNumber = accountRepository.findByAccountNumber(account.getAccountNumber());

        assertEquals(4567888, byAccountNumber.getAccountNumber());
        assertThat(byAccountNumber.getAccountNumber()).isNotNull();
        assertThat(byAccountNumber.getAccountNumber()).isEqualTo(account.getAccountNumber());
    }
}
