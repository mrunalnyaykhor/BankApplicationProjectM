package com.bankmanagement.repository;

import com.bankmanagement.dto.AccountDto;
import com.bankmanagement.dto.TransactionDto;
import com.bankmanagement.entity.Account;
import com.bankmanagement.entity.Transaction;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

@ExtendWith({SpringExtension.class})
@SpringBootTest

public class TransactionRepositoryTest {
    BankRepository bankRepository1 = mock(BankRepository.class);

    TransactionRepository transactionRepository = mock(TransactionRepository.class);
    @Test
    public void TransactionSaveFindAllByAccountNumberToOrAccountNumberFromAndTransactionDateBetween() {

        LocalDate date = LocalDate.now();

        Transaction transaction = Transaction.builder().accountNumberFrom(4567888L).accountNumberTo(4567889L).transactionDate(date).name("Virat").amount(777788.00).description("Hello").ifscCode("SBIN1234N").build();
        Account account = Account.builder().customerId(1L).bankId(1L).accountId(1L)
                .accountNumber(4567888).amount(777788.00)
                .isBlocked(false).aadhaarNumber(233333333333L).age(32).contactNumber(9876543234L)
                .email("redmi@gmail.com")
                .dateOfBirth("1987-09-20").firstName("Virat").lastName("Kohli").panCardNumber("AAAS234KKL").build();

        Mockito.when(transactionRepository.findAllByAccountNumberToOrAccountNumberFromAndTransactionDateBetween(ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong(), ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(List.of(transaction));

        transactionRepository.findAllByAccountNumberToOrAccountNumberFromAndTransactionDateBetween(ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong(), ArgumentMatchers.any(), ArgumentMatchers.any());


    }

}
