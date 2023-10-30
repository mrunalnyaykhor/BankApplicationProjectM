package com.bankmanagement.service;

import com.bankmanagement.dto.AccountDto;
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
import org.springframework.beans.factory.annotation.Autowired;

import javax.security.auth.login.AccountException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    LocalDate date = LocalDate.now();
    TransactionDto transactionDto = TransactionDto.builder().accountNumberFrom(4567888L).accountNumberTo(4567889l).transactionDate(date).name("Virat").amount(777788.00).description("Hello").ifscCode("SBIN1234N").build();
    AccountDto accountDto = AccountDto.builder().bankId(1L).customerId(1L).accountId(1L).accountNumber(4567888).isBlocked(false).aadhaarNumber(233333333333L).age(32).contactNumber(9876543234L).email("redmi@gmail.com").amount(777788.00).dateOfBirth("1987-09-20").firstName("Virat").lastName("Kohli").panCardNumber("AAAS234KKL").build();
    @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private AccountRepository accountRepository;
    @InjectMocks
    private TransactionServiceImpl transactionService;

    @Test
    public void transferMoneyTest() throws AccountException {


        Account account = Account.builder().customerId(1L).bankId(1L).accountId(1L).accountNumber(4567888).amount(777788.00).isBlocked(true).aadhaarNumber(233333333333L).age(32).contactNumber(9876543234L).email("redmi@gmail.com").dateOfBirth("1987-09-20").firstName("Virat").lastName("Kohli").panCardNumber("AAAS234KKL").build();
        Account account1 = Account.builder().customerId(1L).bankId(1L).accountId(1L).accountNumber(4567889).amount(777788.00).isBlocked(true).aadhaarNumber(233333333333L).age(32).contactNumber(9876543234L).email("redmi@gmail.com").dateOfBirth("1987-09-20").firstName("Virat").lastName("Kohli").panCardNumber("AAAS234KKL").build();
        Mockito.when(accountRepository.findByAccountNumber(4567888L)).thenReturn(account);
        Mockito.when(accountRepository.findByAccountNumber(4567888L)).thenReturn(account1);
        transactionService.transferMoney(transactionDto);

    }


    @Test
    public void transferMoneySuccessfullyTest() throws AccountException {

        LocalDate date = LocalDate.now();
        Bank bank= Bank.builder().bankId(1l).bankName("SBI").ifscCode("SBIN2345M").branchName("Mohadi").build();
        TransactionDto transactionDto = TransactionDto.builder().accountNumberFrom(4567888l).name("Arun").accountNumberTo(4567889L).transactionDate(date).amount(7788.00).description("Hello").ifscCode("SBIN2345M").build();
        Account fromAccount = Account.builder().customerId(1L).bankId(1L).accountId(1L).accountNumber(4567888).amount(79788.00).isBlocked(false).aadhaarNumber(233333333333L).age(32).contactNumber(9876543234L).email("redmi@gmail.com").dateOfBirth("1987-09-20").firstName("Arun").lastName("Kohli").panCardNumber("AAAS234KKL").bank(bank).build();
        Account toAccount = Account.builder().customerId(1L).bankId(1L).accountId(1L).accountNumber(4567889).amount(879788.00).isBlocked(false).aadhaarNumber(233333333333L).age(32).contactNumber(9876543234L).email("redmi@gmail.com").dateOfBirth("1987-09-20").firstName("Arun").lastName("Kohli").panCardNumber("AAAS234KKL").bank(bank).build();
        Mockito.when(accountRepository.findByAccountNumber(4567888L)).thenReturn(fromAccount);
        Mockito.when(accountRepository.findByAccountNumber(4567889L)).thenReturn(toAccount);
        transactionService.transferMoney(transactionDto);

    }

    @Test
    public void TransactionFailureWhenAmountLessThanThousandRupeesTest() {

        Account fromAccount = Account.builder().customerId(1L).bankId(1L).accountId(1L).accountNumber(4567888).amount(500.00).isBlocked(true).aadhaarNumber(233333333333L).age(32).contactNumber(9876543234L).email("redmi@gmail.com").dateOfBirth("1987-09-20").firstName("Virat").lastName("Kohli").panCardNumber("AAAS234KKL").build();
        Account toACcount = Account.builder().customerId(1L).bankId(1L).accountId(1L).accountNumber(4567889).amount(777788.00).isBlocked(false).aadhaarNumber(233333333333L).age(32).contactNumber(9876543234L).email("redmi@gmail.com").dateOfBirth("1987-09-20").firstName("Virat").lastName("Kohli").panCardNumber("AAAS234KKL").build();
        Mockito.when(accountRepository.findByAccountNumber(4567888L)).thenReturn(fromAccount);
        Mockito.when(accountRepository.findByAccountNumber(4567889L)).thenReturn(toACcount);

        assertThrows(TransactionException.class, () -> {
            transactionService.transferMoney(transactionDto);
        });

    }

    @Test
    public void TransactionFailureWhenFromAmountIsBlockedTest()  {

        Account fromAccount = Account.builder().customerId(1L).bankId(1L).accountId(1L).accountNumber(4567888).amount(1888800.00).isBlocked(true).aadhaarNumber(233333333333L).age(32).contactNumber(9876543234L).email("redmi@gmail.com").dateOfBirth("1987-09-20").firstName("Virat").lastName("Kohli").panCardNumber("AAAS234KKL").build();
        Account toACcount = Account.builder().customerId(1L).bankId(1L).accountId(1L).accountNumber(4567889).amount(4788.00).isBlocked(false).aadhaarNumber(233333333333L).age(32).contactNumber(9876543234L).email("redmi@gmail.com").dateOfBirth("1987-09-20").firstName("Virat").lastName("Kohli").panCardNumber("AAAS234KKL").build();
        Mockito.when(accountRepository.findByAccountNumber(4567888L)).thenReturn(fromAccount);
        Mockito.when(accountRepository.findByAccountNumber(4567889L)).thenReturn(toACcount);

        assertThrows(AccountException.class, () -> {
            transactionService.transferMoney(transactionDto);
        });

    }
    @Test
    public void TransactionFailureWhenFromAmountGraterThanTwoThousandIsBlockedTest() {

        Account fromAccount = Account.builder().customerId(1L).bankId(1L).accountId(1L).accountNumber(4567888).amount(99990.00).isBlocked(false).aadhaarNumber(233333333333L).age(32).contactNumber(9876543234L).email("redmi@gmail.com").dateOfBirth("1987-09-20").firstName("Virat").lastName("Kohli").panCardNumber("AAAS234KKL").build();
        Account toACcount = Account.builder().customerId(1L).bankId(1L).accountId(1L).accountNumber(4567889).amount(4788.00).isBlocked(false).aadhaarNumber(233333333333L).age(32).contactNumber(9876543234L).email("redmi@gmail.com").dateOfBirth("1987-09-20").firstName("Virat").lastName("Kohli").panCardNumber("AAAS234KKL").build();
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
        Bank bank= Bank.builder().bankId(1l).bankName("SBI").ifscCode("SBIN2345M").branchName("Mohadi").build();
        TransactionDto transactionDto = TransactionDto.builder().accountNumberFrom(4567888l).name("Virat").accountNumberTo(4567889L).transactionDate(date).amount(7788.00).description("Hello").ifscCode("SBIN234N").build();
        Account fromAccount = Account.builder().customerId(1L).bankId(1L).accountId(1L).accountNumber(4567888).amount(79788.00).isBlocked(false).aadhaarNumber(233333333333L).age(32).contactNumber(9876543234L).email("redmi@gmail.com").dateOfBirth("1987-09-20").firstName("Vijay").lastName("Kohli").panCardNumber("AAAS234KKL").bank(bank).build();
        Account toAccount = Account.builder().customerId(1L).bankId(1L).accountId(1L).accountNumber(4567889).amount(879788.00).isBlocked(false).aadhaarNumber(233333333333L).age(32).contactNumber(9876543234L).email("redmi@gmail.com").dateOfBirth("1987-09-20").firstName("Arun").lastName("Kohli").panCardNumber("AAAS234KKL").bank(bank).build();
        Mockito.when(accountRepository.findByAccountNumber(4567888L)).thenReturn(fromAccount);
        Mockito.when(accountRepository.findByAccountNumber(4567889L)).thenReturn(toAccount);
        assertThrows(RuntimeException.class, () -> {
            transactionService.transferMoney(transactionDto);
        });

    }
    @Test
    public void TransactionFailureWhenFirstNameNotMatchTest() {
        LocalDate date = LocalDate.now();
        Bank bank= Bank.builder().bankId(1l).bankName("SBI").ifscCode("SBIN2345M").branchName("Mohadi").build();
        TransactionDto transactionDto = TransactionDto.builder().accountNumberFrom(4567888l).name("Virat").accountNumberTo(4567889L).transactionDate(date).amount(7788.00).description("Hello").ifscCode("SBIN2345M").build();
        Account fromAccount = Account.builder().customerId(1L).bankId(1L).accountId(1L).accountNumber(4567888).amount(79788.00).isBlocked(false).aadhaarNumber(233333333333L).age(32).contactNumber(9876543234L).email("redmi@gmail.com").dateOfBirth("1987-09-20").firstName("Vijay").lastName("Kohli").panCardNumber("AAAS234KKL").bank(bank).build();
        Account toAccount = Account.builder().customerId(1L).bankId(1L).accountId(1L).accountNumber(4567889).amount(879788.00).isBlocked(false).aadhaarNumber(233333333333L).age(32).contactNumber(9876543234L).email("redmi@gmail.com").dateOfBirth("1987-09-20").firstName("Arun").lastName("Kohli").panCardNumber("AAAS234KKL").bank(bank).build();
        Mockito.when(accountRepository.findByAccountNumber(4567888L)).thenReturn(fromAccount);
        Mockito.when(accountRepository.findByAccountNumber(4567889L)).thenReturn(toAccount);
        assertThrows(RuntimeException.class, () -> {
            transactionService.transferMoney(transactionDto);
        });
    }

    @Test
    public void findTransactionDaysTest(){
        LocalDate date = LocalDate.now();
        long days =2;
        LocalDate fromDate = date.minusDays(days);

        Bank bank= Bank.builder().bankId(1l).bankName("SBI").ifscCode("SBIN2345M").branchName("Mohadi").build();
       // TransactionDto transactionDto = TransactionDto.builder().accountNumberFrom(4567888l).name("Virat").accountNumberTo(4567889L).transactionDate(date).amount(7788.00).description("Hello").ifscCode("SBIN2345M").build();
        Account fromAccount = Account.builder().customerId(1L).bankId(1L).accountId(1L).accountNumber(4567888).amount(79788.00).isBlocked(false).aadhaarNumber(233333333333L).age(32).contactNumber(9876543234L).email("redmi@gmail.com").dateOfBirth("1987-09-20").firstName("Vijay").lastName("Kohli").panCardNumber("AAAS234KKL").bank(bank).build();
        Account toAccount = Account.builder().customerId(1L).bankId(1L).accountId(1L).accountNumber(4567889).amount(879788.00).isBlocked(false).aadhaarNumber(233333333333L).age(32).contactNumber(9876543234L).email("redmi@gmail.com").dateOfBirth("1987-09-20").firstName("Arun").lastName("Kohli").panCardNumber("AAAS234KKL").bank(bank).build();
        Transaction transaction = Transaction.builder().transactionId(1l).transactionDate(date).accountNumberFrom(fromAccount.getAccountNumber()).accountNumberTo(toAccount.getAccountNumber()).amount(5000.00).description("Hello").ifscCode("SBIN2345M").name("Virat").build();
       // Transaction transaction1 = Transaction.builder().transactionId(2l).transactionDate(date).accountNumberFrom(fromAccount.getAccountNumber()).accountNumberTo(toAccount.getAccountNumber()).amount(2000.00).description("Books").ifscCode("SBIN2345M").name("Virat").build();
List<Transaction> transactionList = new ArrayList<>();

//transactionList.add(transaction1);
transactionList.add(transaction);

        Mockito.when(transactionRepository.findAllByAccountNumberToOrAccountNumberFromAndTransactionDateBetween(ArgumentMatchers.anyLong(),ArgumentMatchers.anyLong(),ArgumentMatchers.any(),ArgumentMatchers.any())).thenReturn(List.of(transaction));
        transactionService.findTransaction(toAccount.getAccountNumber(), days);
    }
}
