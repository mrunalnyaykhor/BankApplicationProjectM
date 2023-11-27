package com.bankmanagement.service;
import com.bankmanagement.dto.TransactionDto;
import com.bankmanagement.entity.Account;
import com.bankmanagement.entity.Bank;
import com.bankmanagement.entity.Transaction;
import com.bankmanagement.exception.TransactionException;
import com.bankmanagement.repository.AccountRepository;
import com.bankmanagement.repository.TransactionRepository;
import com.bankmanagement.service.serviceimpl.TransactionServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.security.auth.login.AccountException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    LocalDate date = LocalDate.now();
    TransactionDto transactionDto = TransactionDto.builder().accountNumberFrom(4567888L).accountNumberTo(4567889L).transactionDate(date).name("Virat").amount(777788.00).description("Hello").ifscCode("SBIN1234N").build();

    @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private AccountRepository accountRepository;
    @InjectMocks
    private TransactionServiceImpl transactionService;

    @Test
    public void transferMoneyTest() throws AccountException {


        Account account = Account.builder().customerId(1L).bankId(1L).accountId(1L).accountNumber(4567888).amount(777788.00).isBlocked(true).build();
        Account account1 = Account.builder().customerId(1L).bankId(1L).accountId(1L).accountNumber(4567889).amount(777788.00).isBlocked(true).build();
        Mockito.when(accountRepository.findByAccountNumber(4567888L)).thenReturn(account);
        Mockito.when(accountRepository.findByAccountNumber(4567888L)).thenReturn(account1);
        transactionService.transferMoney(transactionDto);
        assertEquals(777788.00, account.getAmount(), 777788.00);
        assertEquals(777788.00, account1.getAmount(), 777788.00);
    }


    @Test
    public void transferMoneySuccessfullyTest() throws AccountException {

        LocalDate date = LocalDate.now();
        Bank bank = Bank.builder().bankId(1L).bankName("SBI").ifscCode("SBIN2345M").branchName("Mohadi").build();
        TransactionDto transactionDto = TransactionDto.builder().accountNumberFrom(4567888L).name("Arun").accountNumberTo(4567889L).transactionDate(date).amount(7788.00).description("Hello").ifscCode("SBIN2345M").build();
        Account fromAccount = Account.builder().customerId(1L).bankId(1L).accountId(1L).accountNumber(4567888).amount(79788.00).isBlocked(false).bank(bank).build();
        Account toAccount = Account.builder().customerId(1L).bankId(1L).accountId(1L).accountNumber(4567889).amount(879788.00).isBlocked(false).bank(bank).build();
        Mockito.when(accountRepository.findByAccountNumber(4567888L)).thenReturn(fromAccount);
        Mockito.when(accountRepository.findByAccountNumber(4567889L)).thenReturn(toAccount);
        transactionService.transferMoney(transactionDto);

    }

    @Test
    public void TransactionFailureWhenAmountLessThanThousandRupeesTest() {

        Account fromAccount = Account.builder().customerId(1L).bankId(1L).accountId(1L).accountNumber(4567888).amount(500.00).isBlocked(true).build();
        Account toACcount = Account.builder().customerId(1L).bankId(1L).accountId(1L).accountNumber(4567889).amount(777788.00).isBlocked(false).build();
        Mockito.when(accountRepository.findByAccountNumber(4567888L)).thenReturn(fromAccount);
        Mockito.when(accountRepository.findByAccountNumber(4567889L)).thenReturn(toACcount);

        assertThrows(TransactionException.class, () -> {
            transactionService.transferMoney(transactionDto);
        });

    }

    @Test
    public void TransactionFailureWhenFromAmountIsBlockedTest() {

        Account fromAccount = Account.builder().customerId(1L).bankId(1L).accountId(1L).accountNumber(4567888).amount(1888800.00).isBlocked(true).build();
        Account toACcount = Account.builder().customerId(1L).bankId(1L).accountId(1L).accountNumber(4567889).amount(4788.00).isBlocked(false).build();
        Mockito.when(accountRepository.findByAccountNumber(4567888L)).thenReturn(fromAccount);
        Mockito.when(accountRepository.findByAccountNumber(4567889L)).thenReturn(toACcount);

        assertThrows(AccountException.class, () -> {
            transactionService.transferMoney(transactionDto);
        });

    }

    @Test
    public void TransactionFailureWhenFromAmountGraterThanTwoThousandIsBlockedTest() {

        Account fromAccount = Account.builder().customerId(1L).bankId(1L).accountId(1L).accountNumber(4567888).amount(99990.00).isBlocked(false).build();
        Account toACcount = Account.builder().customerId(1L).bankId(1L).accountId(1L).accountNumber(4567889).amount(4788.00).isBlocked(false).build();
        Mockito.when(accountRepository.findByAccountNumber(4567888L)).thenReturn(fromAccount);
        Mockito.when(accountRepository.findByAccountNumber(4567889L)).thenReturn(toACcount);

        assertThrows(TransactionException.class, () -> {
            transactionService.transferMoney(transactionDto);
        });

    }

    @Test
    public void TransactionFailureWhenAccountNumberIsEmptyTest() {

        Account fromAccount = null;
        Account toACcount = null;
        Mockito.when(accountRepository.findByAccountNumber(4567888L)).thenReturn(fromAccount);
        Mockito.when(accountRepository.findByAccountNumber(4567889L)).thenReturn(toACcount);
        assertThrows(TransactionException.class, () -> {
            transactionService.transferMoney(transactionDto);
        });
    }

    @Test
    public void TransactionFailureWhenIfscCodeNotMatchTest() {
        LocalDate date = LocalDate.now();
        Bank bank = Bank.builder().bankId(1L).bankName("SBI").ifscCode("SBIN2345M").branchName("Mohadi").build();
        TransactionDto transactionDto = TransactionDto.builder().accountNumberFrom(4567888L).name("Virat").accountNumberTo(4567889L).transactionDate(date).amount(7788.00).description("Hello").ifscCode("SBIN234N").build();
        Account fromAccount = Account.builder().customerId(1L).bankId(1L).accountId(1L).accountNumber(4567888).amount(79788.00).isBlocked(false).bank(bank).build();
        Account toAccount = Account.builder().customerId(1L).bankId(1L).accountId(1L).accountNumber(4567889).amount(879788.00).isBlocked(false).bank(bank).build();
        Mockito.when(accountRepository.findByAccountNumber(4567888L)).thenReturn(fromAccount);
        Mockito.when(accountRepository.findByAccountNumber(4567889L)).thenReturn(toAccount);
        assertThrows(RuntimeException.class, () -> {
            transactionService.transferMoney(transactionDto);
        });

    }

    @Test
    public void TransactionFailureWhenFirstNameNotMatchTest() {
        LocalDate date = LocalDate.now();
        Bank bank = Bank.builder().bankId(1L).bankName("SBI").ifscCode("SBIN2345M").branchName("Mohadi").build();
        TransactionDto transactionDto = TransactionDto.builder().accountNumberFrom(4567888L).name("Virat").accountNumberTo(4567889L).transactionDate(date).amount(7788.00).description("Hello").ifscCode("SBIN2345M").build();
        Account fromAccount = Account.builder().customerId(1L).bankId(1L).accountId(1L).accountNumber(4567888).amount(79788.00).isBlocked(false).build();
        Account toAccount = Account.builder().customerId(1L).bankId(1L).accountId(1L).accountNumber(4567889).amount(879788.00).isBlocked(false).bank(bank).build();
        Mockito.when(accountRepository.findByAccountNumber(4567888L)).thenReturn(fromAccount);
        Mockito.when(accountRepository.findByAccountNumber(4567889L)).thenReturn(toAccount);
        assertThrows(RuntimeException.class, () -> {
            transactionService.transferMoney(transactionDto);
        });
    }

    @Test
    public void findTransactionDaysTest() {
        LocalDate date = LocalDate.now();
        long days = 2;

        Bank bank = Bank.builder().bankId(1L).bankName("SBI").ifscCode("SBIN2345M").branchName("Mohadi").build();
        Account fromAccount = Account.builder().customerId(1L).bankId(1L).accountId(1L).accountNumber(4567888).amount(79788.00).isBlocked(false).bank(bank).build();
        Account toAccount = Account.builder().customerId(1L).bankId(1L).accountId(1L).accountNumber(4567889).amount(879788.00).isBlocked(false).bank(bank).build();
        Transaction transaction = Transaction.builder().transactionId(1L).transactionDate(date).accountNumberFrom(fromAccount.getAccountNumber()).accountNumberTo(toAccount.getAccountNumber()).amount(5000.00).description("Hello").ifscCode("SBIN2345M").name("Virat").build();

        Mockito.when(transactionRepository.findAllByAccountNumberToOrAccountNumberFromAndTransactionDateBetween(ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong(), ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(List.of(transaction));
        transactionService.findTransaction(toAccount.getAccountNumber(), days);
    }
}
