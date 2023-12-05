package com.bankmanagement.service;

import com.bankmanagement.constant.ApplicationConstant;
import com.bankmanagement.dto.AccountDto;
import com.bankmanagement.entity.Account;
import com.bankmanagement.entity.Bank;
import com.bankmanagement.entity.Customer;
import com.bankmanagement.enump.AccountType;
import com.bankmanagement.exception.AccountException;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)

public class AccountServiceTest {


    Bank bank = Bank.builder().bankId(1L).bankName("SBI").branchName("SBIMOhadi").ifscCode("SBIN1234568").address("Mohadi").build();

    Customer customer = Customer.builder().bank(bank).customerId(1L).firstName("Virat").lastName("Kohli").age(32).email("viratkohli@gmail.com").aadhaarNumber("222233944450").contactNumber(9876543238L).address("Mohadi").panCardNumber("AAAS254KKL").dateOfBirth("1987-09-20").build();

    Account account = Account.builder().accountId(1l).customer(customer).bank(bank)
            .accountNumber(550144298970l).amount(7072.00).accountType(AccountType.SAVING)
            .isBlocked(false).build();

    AccountDto accountDto = AccountDto.builder().accountId(3l).amount(60345.00).accountType(AccountType.CURRENT).bankId(1L).customerId(1L)
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
    void saveAccountTest() throws ExecutionException, InterruptedException {

        List<Account> accountList = new ArrayList<>();
        account.setBank(bank);
        account.setCustomer(customer);
        account.setAccountType(AccountType.SAVING);
        Mockito.when(accountRepository.findById(Mockito.any())).thenReturn(Optional.empty());
        Mockito.when(bankRepository.findById(any())).thenReturn(Optional.of(bank));
        Mockito.when(customerRepository.findById(any())).thenReturn(Optional.of(customer));
        Mockito.when(accountRepository.save(Mockito.any())).thenReturn(account);
        Mockito.when(accountRepository.findByCustomerAndBankAndAccountType(Mockito.any(),Mockito.any(),Mockito.any())).thenReturn(accountList);
        Mockito.when(accountRepository.findByAccountNumber(Mockito.any())).thenReturn(null);
        String result = accountService.saveAccount(accountDto);

        assertEquals(result,ApplicationConstant.ACCOUNT_IS_CREATED);

    }

    @Test
    void saveAccountTest_failure_WhenAmount_Minimum_ForCurrentAccount() throws ExecutionException, InterruptedException {
        Bank bank = Bank.builder().bankId(1L).bankName("SBI").branchName("SBIMOhadi").ifscCode("SBIN1234568").address("Mohadi").build();

        Customer customer = Customer.builder().bank(bank).customerId(1L).firstName("Virat").lastName("Kohli").age(32).email("viratkohli@gmail.com").aadhaarNumber("222233944450").contactNumber(9876543238L).address("Mohadi").panCardNumber("AAAS254KKL").dateOfBirth("1987-09-20").build();

        Account account = Account.builder().accountId(1l).customer(customer).bank(bank)
                .accountNumber(550144298970l).amount(7072.00).accountType(AccountType.SAVING)
                .isBlocked(false).build();

        AccountDto accountDto = AccountDto.builder().accountId(3l).amount(605.00).accountType(AccountType.CURRENT).bankId(1L).customerId(1L)
                .isBlocked(false).build();


        List<Account> accountList = new ArrayList<>();
        account.setBank(bank);
        account.setCustomer(customer);
        account.setAccountType(AccountType.SAVING);
        Mockito.when(accountRepository.findById(Mockito.any())).thenReturn(Optional.empty());
        Mockito.when(bankRepository.findById(any())).thenReturn(Optional.of(bank));
        Mockito.when(customerRepository.findById(any())).thenReturn(Optional.of(customer));
        Mockito.when(accountRepository.save(Mockito.any())).thenReturn(account);
        Mockito.when(accountRepository.findByCustomerAndBankAndAccountType(Mockito.any(),Mockito.any(),Mockito.any())).thenReturn(accountList);
        Mockito.when(accountRepository.findByAccountNumber(Mockito.any())).thenReturn(null);


            String exception =accountService.saveAccount(accountDto);

        assertEquals(exception,ApplicationConstant.MINIMUM_BALANCE_FOR_CURRENT_ACCOUNT );


    }

    @Test
    void saveAccountTest_failure_WhenAmount_Minimum_ForSavingAccount() throws ExecutionException, InterruptedException {
        Bank bank = Bank.builder().bankId(1L).bankName("SBI").branchName("SBIMOhadi").ifscCode("SBIN1234568").address("Mohadi").build();

        Customer customer = Customer.builder().bank(bank).customerId(1L).firstName("Virat").lastName("Kohli").age(32).email("viratkohli@gmail.com").aadhaarNumber("222233944450").contactNumber(9876543238L).address("Mohadi").panCardNumber("AAAS254KKL").dateOfBirth("1987-09-20").build();

        Account account = Account.builder().accountId(1l).customer(customer).bank(bank)
                .accountNumber(550144298970l).amount(7072.00).accountType(AccountType.SAVING)
                .isBlocked(false).build();

        AccountDto accountDto = AccountDto.builder().accountId(3l).amount(605.00).accountType(AccountType.CURRENT).bankId(1L).customerId(1L)
                .isBlocked(false).build();


        List<Account> accountList = new ArrayList<>();
        account.setBank(bank);
        account.setCustomer(customer);
        account.setAccountType(AccountType.SAVING);
        Mockito.when(accountRepository.findById(Mockito.any())).thenReturn(Optional.empty());
        Mockito.when(bankRepository.findById(any())).thenReturn(Optional.of(bank));
        Mockito.when(customerRepository.findById(any())).thenReturn(Optional.of(customer));
        Mockito.when(accountRepository.save(Mockito.any())).thenReturn(account);
        Mockito.when(accountRepository.findByCustomerAndBankAndAccountType(Mockito.any(),Mockito.any(),Mockito.any())).thenReturn(accountList);
        Mockito.when(accountRepository.findByAccountNumber(Mockito.any())).thenReturn(null);


        String exception =accountService.saveAccount(accountDto);

        assertEquals(exception,ApplicationConstant.MINIMUM_BALANCE_FOR_SAVING_ACCOUNT );

    }
    @Test
    void saveAccountTest_failure_WhenCustomerAndBankAndAccountType_AccountAlready_Present(){
        Bank bank = Bank.builder().bankId(1L).bankName("SBI").branchName("SBIMOhadi").ifscCode("SBIN1234568").address("Mohadi").build();

        Customer customer = Customer.builder().bank(bank).customerId(1L).firstName("Virat").lastName("Kohli").age(32).email("viratkohli@gmail.com").aadhaarNumber("222233944450").contactNumber(9876543238L).address("Mohadi").panCardNumber("AAAS254KKL").dateOfBirth("1987-09-20").build();

        Account account = Account.builder().accountId(1l).customer(customer).bank(bank)
                .accountNumber(550144298970l).amount(7072.00).accountType(AccountType.SAVING)
                .isBlocked(false).build();

        AccountDto accountDto = AccountDto.builder().accountId(1l).amount(605.00).accountType(AccountType.CURRENT).bankId(1L).customerId(1L)
                .isBlocked(false).build();

        Mockito.when(accountRepository.findById(Mockito.any())).thenReturn(Optional.of(account));

        Mockito.when(accountRepository.findByAccountNumber(Mockito.any())).thenReturn(null);

        AccountException exception= assertThrows(AccountException.class,()->{
            accountService.saveAccount(accountDto);
        });

       assertEquals(exception,ApplicationConstant.ACCOUNT_ALREADY_PRESENT );

    }


    @DisplayName("JUnit test for getAllAccount method")
    @Test
    public void getAllAccountTest(){
        Mockito.when(accountRepository.findAll()).thenReturn(List.of(account));
        assertEquals(1, accountService.getAllAccount().size());
    }
    @DisplayName("JUnit test for getAllAccount Fail method")
    @Test
    public void getAllAccountTest_failWhenAccountList_Empty(){

        Mockito.when(accountRepository.findAll()).thenReturn(Collections.emptyList());
assertThrows(AccountException.class,()->{
    accountService.getAllAccount();
});
    }

    @DisplayName("JUnit test for get_AccountById_Test method")
    @Test
    public void get_AccountById_Test()  {
        Mockito.when(accountRepository.findById(account.getAccountId())).thenReturn(Optional.of(account));
        assertNotNull(accountService.getAccountById(account.getAccountId()), ApplicationConstant.RESPONSE_SHOULD_NOT_NULL);
    }

    @DisplayName("JUnit test for updateAccountById method")
    @Test
    public void updateAccountByIdTest() {
        Mockito.when(accountRepository.findById(accountDto.getAccountId())).thenReturn(Optional.ofNullable(account));
        String result = accountService.updateAccountById(accountDto);

        assertEquals(result, ApplicationConstant.ACCOUNT_ID_UPDATE_SUCCESSFULLY);
    }
    @DisplayName("JUnit test for updateAccountById_Not_Found_method")
    @Test
    public void updateAccountByIdTest_failWhenAccountIdNotFound() {
        Mockito.when(accountRepository.findById(accountDto.getAccountId())).thenReturn(Optional.empty());

        assertThrows(AccountException.class,()->{
            accountService.updateAccountById(accountDto);
        });
    }

    @DisplayName("Junit test for deleteAccountById method")
    @Test
    public void deleteAccountByIdTest() {

        Mockito.when(accountRepository.findById(account.getAccountId())).thenReturn(Optional.ofNullable(account));
        String result = accountService.deleteAccountById(account.getAccountId());
        assertEquals( result,ApplicationConstant.ACCOUNT_ID_DELETED_SUCCESSFULLY);


    }

    @Test
    public void testDeleteAccountByIdAccountNotFound() {

        Mockito.when(accountRepository.findById(account.getAccountId())).thenReturn(Optional.empty());

        assertThrows(AccountException.class, () -> {
            accountService.deleteAccountById(account.getAccountId());
        });


    }

    @Test
    public void getBalanceTest(){
        Mockito.when(accountRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(account));
        Double balance = accountService.getBalance(account.getAccountId());
        assertEquals(7072.00, balance);
    }

    @Test
    public void getBalanceFailureWhenAccountNotPresentTest() {

        Mockito.when(accountRepository.findById(account.getAccountId())).thenReturn(Optional.empty());
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
        String result = accountService.isBlocked(account.getAccountId());
        assertEquals(result, "account status is true"); //  isBlocked() method returns a boolean

    }
    @DisplayName("Junit test for blockedAccountTest")
    @Test
    public void blockedTest_whenAmount_MoreThan_tenThousand() {
        Account account = Account.builder().accountId(1l).customer(customer).bank(bank)
                .accountNumber(550144298970l).amount(70752.00).accountType(AccountType.SAVING)
                .isBlocked(false).build();
        Mockito.when(accountRepository.findById(account.getAccountId())).thenReturn(Optional.of(account));
        String result = accountService.isBlocked(account.getAccountId());
        assertEquals(result, "account status is false"); //  isBlocked() method returns a boolean

    }
    @DisplayName("Junit test for checkAccountStatus")
    @Test
    public void checkAccountStatus(){
        Mockito.when(accountRepository.findById(account.getAccountId())).thenReturn(Optional.of(account));
        String result = accountService.accountStatus(account.getAccountId());
        assertNotNull(ApplicationConstant.ACCOUNT_TYPE_STATUS, String.valueOf(account.getAccountType()));


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