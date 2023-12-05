package com.bankmanagement.service;
import com.bankmanagement.constant.ApplicationConstant;
import com.bankmanagement.dto.TransactionDto;
import com.bankmanagement.entity.Account;
import com.bankmanagement.entity.Bank;
import com.bankmanagement.entity.Customer;
import com.bankmanagement.entity.Transaction;
import com.bankmanagement.enump.AccountType;
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
import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {
    @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private AccountRepository accountRepository;
    @InjectMocks
    private TransactionServiceImpl transactionService;
    Bank bank = Bank.builder().bankId(1L).bankName("SBI").branchName("SBIMOhadi").ifscCode("SBIN1234568").address("Mohadi").build();

    Customer customer = Customer.builder().bank(bank).customerId(1L).firstName("Virat").lastName("Kohli").age(32).email("viratkohli@gmail.com").aadhaarNumber("222233944450").contactNumber(9876543238L).address("Mohadi").panCardNumber("AAAS254KKL").dateOfBirth("1987-09-20").build();

    Account account1 = Account.builder().accountId(1l).customer(customer).bank(bank)
            .accountNumber(550144298970l).amount(7072.00).accountType(AccountType.SAVING)
            .isBlocked(false).build();

    Account account2 = Account.builder().accountId(2l).customer(customer).bank(bank)
            .accountNumber(550144298971l).amount(707920.00).accountType(AccountType.CURRENT)
            .isBlocked(false).build();
    TransactionDto transactionDto1 = TransactionDto.builder().amount(4000).accountNumberFrom(account2.getAccountNumber()).accountNumberTo(account1.getAccountNumber()).description("Books").ifscCode("SBIN1234568").name("Virat").build();
    @Test
    public void transferMoneySuccessfullyTest() {
        Mockito.when(accountRepository.findByAccountNumber(Mockito.any())).thenReturn(account1);
        Mockito.when(accountRepository.findByAccountNumber(Mockito.any())).thenReturn(account2);
        String result = transactionService.transferMoney(transactionDto1);
        assertEquals(result, ApplicationConstant.TRANSACTION_SUCCESSFUL);

    }
    @Test
    public void transferMoneyTest_AccountNotFound() {

        Mockito.when(accountRepository.findByAccountNumber(Mockito.any())).thenReturn(null);
        Mockito.when(accountRepository.findByAccountNumber(transactionDto1.getAccountNumberTo())).thenReturn(null);

        assertThrows(TransactionException.class, () -> {
            transactionService.transferMoney(transactionDto1);
        });
    }

    @Test
    public void transferMoneyTest_fail_InsufficientBalance()  {

        Account account = Account.builder().accountId(1l).customer(customer).bank(bank)
                .accountNumber(account2.getAccountNumber()).amount(70720.00).accountType(AccountType.CURRENT)
                .isBlocked(false).build();
        TransactionDto transactionDto1 = TransactionDto.builder().amount(60000).accountNumberFrom(account2.getAccountNumber()).accountNumberTo(account1.getAccountNumber()).description("Books").ifscCode("SBIN1234568").name("Virat").build();
        Mockito.when(accountRepository.findByAccountNumber(Mockito.any())).thenReturn(account);

        Mockito.when(accountRepository.findByAccountNumber(Mockito.any())).thenReturn(account1);

        assertThrows(TransactionException.class,()->{
            transactionService.transferMoney(transactionDto1);
        });
    }

    @Test
    public void TransactionFailureWhenNameNotMatchTest() {
        Mockito.when(accountRepository.findByAccountNumber(Mockito.any())).thenReturn(account1);
        Mockito.when(accountRepository.findByAccountNumber(Mockito.any())).thenReturn(account2);
        TransactionDto transactionDto1 = TransactionDto.builder().amount(4000).accountNumberFrom(account1.getAccountNumber()).accountNumberTo(account2.getAccountNumber()).description("Books").ifscCode("SBIN1234568").name("Roman").build();
       TransactionException exception = assertThrows(TransactionException.class, () -> {
            transactionService.transferMoney(transactionDto1);
        });
       assertEquals(exception.getMessage(),ApplicationConstant.TO_ACCOUNT_NAME_IS_INCORRECT);

    }

    @Test
    public void TransactionFailureWhenIfscCode_NotMatchTest() {
        Mockito.when(accountRepository.findByAccountNumber(Mockito.any())).thenReturn(account1);
        Mockito.when(accountRepository.findByAccountNumber(Mockito.any())).thenReturn(account2);
        TransactionDto transactionDto1 = TransactionDto.builder().amount(4000).accountNumberFrom(account1.getAccountNumber()).accountNumberTo(account2.getAccountNumber()).description("Books").ifscCode("SBIN1534548").name("Virat").build();
        TransactionException exception = assertThrows(TransactionException.class, () -> {
            transactionService.transferMoney(transactionDto1);
        });
        assertEquals(exception.getMessage(),ApplicationConstant.TO_ACCOUNT_IFSC_CODE_INCORRECT);
    }

    @Test
    public void findTransactionDaysTest() {
        LocalDate date = LocalDate.now();
        long days = 2;
        Transaction transaction = Transaction.builder().transactionId(1L).transactionDate(date).accountNumberFrom(account1.getAccountNumber()).accountNumberTo(account2.getAccountNumber()).amount(5000.00).description("Hello").ifscCode("SBIN1234568").name("Virat").build();
        Mockito.when(transactionRepository.findAllByAccountNumberToOrAccountNumberFromAndTransactionDateBetween(ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong(), ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(List.of(transaction));
        List<TransactionDto> transaction1 = transactionService.findTransaction(account1.getAccountNumber(), days);
        int expectedTransactions=1 ;
        assertEquals(expectedTransactions, transaction1.size());
    }
}
