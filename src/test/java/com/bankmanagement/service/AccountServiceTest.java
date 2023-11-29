package com.bankmanagement.service;

import com.bankmanagement.constant.ApplicationConstant;
import com.bankmanagement.dto.AccountDto;
import com.bankmanagement.entity.Account;
import com.bankmanagement.entity.Bank;
import com.bankmanagement.entity.Customer;
import com.bankmanagement.enump.AccountType;
import com.bankmanagement.repository.AccountRepository;
import com.bankmanagement.repository.BankRepository;
import com.bankmanagement.repository.CustomerRepository;
import com.bankmanagement.service.serviceimpl.AccountServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.security.auth.login.AccountException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)

public class AccountServiceTest {


    Bank bank = Bank.builder().bankId(1L).bankName("SBI").branchName("SBIMOhadi").ifscCode("SBIN0080667").address("Mohadi").build();
    Customer customer = Customer.builder().customerId(1L).bank(bank).
            firstName("Virat").lastName("Kohli").age(32).email("viratkohli@gmail.com")
            .aadhaarNumber("233333333333l").contactNumber(9876543234L).address("Mumbai")
            .panCardNumber("PNZAB2320M").dateOfBirth("1987-09-20").build();
    Account account = Account.builder().accountId(1l).customer(customer).bank(bank)
            .accountNumber(550144298970l).amount(77772.00).accountType(AccountType.CURRENT)
            .isBlocked(false).build();
    AccountDto accountDto = AccountDto.builder().accountId(4l).amount(6044.00).accountType(AccountType.SAVING).bankId(1L).customerId(1L)
            .isBlocked(false).build();

    @InjectMocks
    private AccountServiceImpl accountService;
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private BankRepository bankRepository;
    @Mock
    private CustomerRepository customerRepository;

    @DisplayName("JUnit test for saveAccountTest method")
    @Test
    void saveAccountTest(){
        List<Account> accountList = new ArrayList<>();
       Mockito.when(accountRepository.findById(1l)).thenReturn(Optional.of(account));
        Mockito.when(bankRepository.findById(accountDto.getBankId())).thenReturn(Optional.ofNullable(bank));
        Mockito.when(customerRepository.findById(accountDto.getCustomerId())).thenReturn(Optional.of(customer));
        Mockito.when(accountRepository.findByCustomerAndBankAndAccountType(account.getCustomer(),account.getBank(),account.getAccountType())).thenReturn(accountList);


        String result = accountService.saveAccount(accountDto);
       // assertEquals(result,"Account is created");

    }

    @Test
    public void neverSaveAccountWhenCustomerFieldNotMatchTest() {
        Customer customer = Customer.builder().customerId(1L).
                firstName("Viratss").lastName("Kohli").age(32).email("viratkohli@gmail.com")
                .aadhaarNumber("233333333333l").contactNumber(9876543234L).address("Mumbai")
                .panCardNumber("AAAS234KKL").dateOfBirth("1987-09-20").build();

        when(bankRepository.findById(1L)).thenReturn(Optional.ofNullable(bank));
        when(customerRepository.findById(1L)).thenReturn(Optional.ofNullable(customer));

        assertThrows(AccountException.class, () -> {

            assert customer != null;
            accountService.saveAccount(accountDto);

        });
    }

    @DisplayName("JUnit test for getAllAccount method")
    @Test
    public void getAllAccountTest(){
        Mockito.when(accountRepository.findAll()).thenReturn(List.of(account));
        assertEquals(1, accountService.getAllAccount().size());
    }

    @DisplayName("JUnit test for getAllAccount method")
    @Test
    public void get_AccountById_Test()  {
        Mockito.when(accountRepository.findById(account.getAccountId())).thenReturn(Optional.of(account));
        assertNotNull(accountService.getAccountById(account.getAccountId()), ApplicationConstant.RESPONSE_SHOULD_NOT_NULL);
    }

    @DisplayName("JUnit test for updateAccountById method")
    @Test
    public void updateAccountByIdTest() {
        Mockito.when(accountRepository.findById(1L)).thenReturn(Optional.ofNullable(account));
        accountService.updateAccountById(accountDto);
        verify(accountRepository.findById(account.getAccountId()), times(1));
    }

    @DisplayName("Junit test for deleteAccountById method")
    @Test
    public void deleteAccountByIdTest() {
        Mockito.when(accountRepository.findById(1L)).thenReturn(Optional.ofNullable(account));
        String result = accountService.deleteAccountById(account.getAccountId());
        assertEquals("Account Id :1 deleted successfully....!!", result);
        verify(accountRepository, times(1)).deleteById(account.getAccountId());

    }

    @Test
    public void testDeleteAccountByIdAccountNotFound() {
        when(accountRepository.findById(account.getAccountId())).thenReturn(Optional.empty());
        AccountException exception = assertThrows(AccountException.class, () -> {
            accountService.deleteAccountById(account.getAccountId());

        });

        assertEquals(ApplicationConstant.ACCOUNT_ID_DOES_NOT_EXIST, exception.getMessage());
        verify(accountRepository, never()).deleteById(anyLong());
    }

    @Test
    public void getBalanceTest(){
        Mockito.when(accountRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(account));
        Double balance = accountService.getBalance(account.getAccountId());
        assertEquals(777788, balance);
    }

    @Test
    public void getBalanceFailureWhenAccountNotPresentTest() {

        Mockito.when(accountRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());
        assertThrows(AccountException.class, () -> {
            accountService.getBalance(account.getAccountId());
        });

    }

    @Test
    public void withdrawalAmountByIdTest(){
        Mockito.when(accountRepository.findById(account.getAccountId())).thenReturn(Optional.ofNullable(account));
        accountService.withdrawalAmountById(account.getAccountId(), 1000.00);
        verify(accountRepository, times(1)).findById(eq(account.getAccountId()));

    }

    @Test
    public void FailureWithdrawalAmountByIdWhenAccountIsNotPresentTest() {
        Mockito.when(accountRepository.findById(account.getAccountId())).thenReturn(Optional.empty());
        assertThrows(AccountException.class, () -> {
            accountService.withdrawalAmountById(account.getAccountId(), 1000.00);

        });
    }

    @Test
    public void FailureWithdrawalAmountLessThanFiveHundredTest() {
        Account account = Account.builder().customerId(1L).bankId(1L).accountId(1L)
                .accountNumber(4567888).amount(788777.00)
                .isBlocked(false).build();
        Mockito.when(accountRepository.findById(account.getAccountId())).thenReturn(Optional.of(account));
        assertThrows(AccountException.class, () -> {
            accountService.withdrawalAmountById(account.getAccountId(), 100.00);
        });
    }

    @Test
    public void FailureWithdrawalWhenAccountAmountLessThanTwoThousandTest() {
        Account account = Account.builder().customerId(1L).bankId(1L).accountId(1L)
                .accountNumber(4567888).amount(788.00)
                .isBlocked(false)
                .build();
        Mockito.when(accountRepository.findById(account.getAccountId())).thenReturn(Optional.of(account));
        assertThrows(AccountException.class, () -> {
            accountService.withdrawalAmountById(account.getAccountId(), 5000.00);
        });
    }


    @DisplayName("Junit test for DepositAmountTest")
    @Test
    public void depositAmountTest() {
        when(accountRepository.findById(any())).thenReturn(Optional.of(account));
        accountService.deposit(account.getAccountId(), 2000.00);
        verify(accountRepository, times(1)).findById(eq(account.getAccountId()));

    }

    @DisplayName("Junit test failureDepositAmountTest")
    @Test
    public void depositAmountFailureTest()  {
        Account account = Account.builder().customerId(1L).bankId(1L).accountId(1L)
                .accountNumber(4567888).amount(88777.00)
                .isBlocked(false).build();
        when(accountRepository.findById(any())).thenReturn(Optional.of(account));

        assertThrows(AccountException.class, () -> {
            accountService.deposit(account.getAccountId(), 96.00);
        });
    }

    @DisplayName("Junit test for blockedAccountTest")
    @Test
    public void blockedTest() {
        Mockito.when(accountRepository.findById(account.getAccountId())).thenReturn(Optional.of(account));
        accountService.isBlocked(account.getAccountId());
        assertFalse(account.isBlocked()); //  isBlocked() method returns a boolean

    }
    @DisplayName("Junit test for blockedAccountTest")
    @Test
    public void checkAccountStatus(){
        Mockito.when(accountRepository.findById(account.getAccountId())).thenReturn(Optional.of(account));
        accountService.accountStatus(account.getAccountId());


    }

    @Test
    public void blockedAmountTest(){
        Account account = Account.builder().customerId(1L).bankId(1L).accountId(1L)
                .accountNumber(4567888).amount(777.00)
                .isBlocked(false).build();
        Mockito.when(accountRepository.findById(account.getAccountId())).thenReturn(Optional.of(account));
        accountService.isBlocked(account.getAccountId());
        assertTrue(account.isBlocked()); //  isBlocked() method returns a boolean

    }


}