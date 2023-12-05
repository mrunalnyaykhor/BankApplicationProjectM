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


        Mockito.when(transactionRepository.findAllByAccountNumberToOrAccountNumberFromAndTransactionDateBetween(ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong(), ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(List.of(transaction));

        List<Transaction> transctionList  = transactionRepository.findAllByAccountNumberToOrAccountNumberFromAndTransactionDateBetween(ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong(), ArgumentMatchers.any(), ArgumentMatchers.any());
        assertEquals(transctionList.size(),1);


    }

}
