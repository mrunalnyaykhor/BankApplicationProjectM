package com.bankmanagement.service;

import com.bankmanagement.dto.AccountDto;
import com.bankmanagement.entity.Account;
import com.bankmanagement.entity.Bank;
import com.bankmanagement.entity.Customer;
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
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

public class AccountServiceTest {

    Optional<Bank> bank = Optional.ofNullable(Bank.builder().bankId(1L).bankName("SBI").branchName("SBIMOhadi").ifscCode("SBIN123").address("Mohadi").build());
    Optional<Customer> customer = Optional.ofNullable(Customer.builder().customerId(1L).
            firstName("Virat").lastName("Kohli").age(32).email("viratkohli@gmail.com")
            .aadhaarNumber("233333333333l").contactNumber(9876543234L).address("Mumbai")
            .panCardNumber("AAAS234KKL").dateOfBirth("1987-09-20").build());
    AccountDto accountDto = AccountDto.builder().bankId(1L).customerId(1L).accountId(1L)
            .accountNumber(4567888)
            .isBlocked(false).aadhaarNumber(233333333333L).age(32).contactNumber(9876543234L)
            .email("redmi@gmail.com")
            .amount(777788.00).dateOfBirth("1987-09-20").firstName("Virat").lastName("Kohli").panCardNumber("AAAS234KKL").build();
    Account account = Account.builder().customerId(1L).bankId(1L).accountId(1L)
            .accountNumber(4567888).amount(777788.00)
            .isBlocked(false).aadhaarNumber(233333333333L).age(32).contactNumber(9876543234L)
            .email("redmi@gmail.com")
            .dateOfBirth("1987-09-20").firstName("Virat").lastName("Kohli").panCardNumber("AAAS234KKL").build();
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
    void saveAccountTest() throws AccountException {
        when(bankRepository.findById(1L)).thenReturn(bank);
        when(customerRepository.findById(1L)).thenReturn(customer);
        accountService.saveAccount(accountDto, customer.get().getCustomerId(), bank.get().getBankId());
        assertEquals("Virat", accountDto.getFirstName());

    }
    @Test
    public void neverSaveAccountWhenCustomerFieldNotMatchTest() throws AccountException {
        Optional<Customer> customer = Optional.ofNullable(Customer.builder().customerId(1L).
                firstName("Viratss").lastName("Kohli").age(32).email("viratkohli@gmail.com")
                .aadhaarNumber("233333333333l").contactNumber(9876543234L).address("Mumbai")
                .panCardNumber("AAAS234KKL").dateOfBirth("1987-09-20").build());

        when(bankRepository.findById(1L)).thenReturn(bank);
        when(customerRepository.findById(1L)).thenReturn(customer);

        accountService.saveAccount(accountDto, customer.get().getCustomerId(), bank.get().getBankId());

        assertThrows(AccountException.class, () -> {
            accountService.saveAccount(accountDto, customer.get().getCustomerId(), bank.get().getBankId());

        });
    }
        @Test
        public void neverSaveAccountWhenCustomerFieldLastNameNotMatchTest() throws AccountException {
            Optional<Customer> customer11 = Optional.ofNullable(Customer.builder().customerId(1L).
                    firstName("Virat").lastName("sharma").age(32).email("viratkohli@gmail.com")
                    .aadhaarNumber("233333333333l").contactNumber(9876543234L).address("Mumbai")
                    .panCardNumber("AAAS234KKL").dateOfBirth("1987-09-20").build());

            when(bankRepository.findById(1L)).thenReturn(bank);
            when(customerRepository.findById(1L)).thenReturn(customer11);

            accountService.saveAccount(accountDto, customer.get().getCustomerId(), bank.get().getBankId());

            assertThrows(AccountException.class, () -> {
                accountService.saveAccount(accountDto, customer.get().getCustomerId(), bank.get().getBankId());

            });



    }
    @Test
    public void neverSaveAccountWhenCustomerField_PanCard_NotMatchTest() throws AccountException {
        Optional<Customer> customer11 = Optional.ofNullable(Customer.builder().customerId(1L).
                firstName("Virat").lastName("Kohli").age(32).email("viratkohli@gmail.com")
                .aadhaarNumber("233333333333l").contactNumber(9876543234L).address("Mumbai")
                .panCardNumber("AAAS234KK").dateOfBirth("1987-09-20").build());

        when(bankRepository.findById(1L)).thenReturn(bank);
        when(customerRepository.findById(1L)).thenReturn(customer11);

        accountService.saveAccount(accountDto, customer.get().getCustomerId(), bank.get().getBankId());

        assertThrows(AccountException.class, () -> {
            accountService.saveAccount(accountDto, customer.get().getCustomerId(), bank.get().getBankId());

        });
    }
    @Test
    public void neverSaveAccountWhenCustomerField_ContactNumber_NotMatchTest() throws AccountException {
        Optional<Customer> customer11 = Optional.ofNullable(Customer.builder().customerId(1L).
                firstName("Virat").lastName("Kohli").age(32).email("viratkohli@gmail.com")
                .aadhaarNumber("233333333333l").contactNumber(9876543237L).address("Mumbai")
                .panCardNumber("AAAS234KKL").dateOfBirth("1987-09-20").build());

        when(bankRepository.findById(1L)).thenReturn(bank);
        when(customerRepository.findById(1L)).thenReturn(customer11);

        accountService.saveAccount(accountDto, customer.get().getCustomerId(), bank.get().getBankId());

        assertThrows(AccountException.class, () -> {
            accountService.saveAccount(accountDto, customer.get().getCustomerId(), bank.get().getBankId());

        });
    }
    @Test
    public void neverSaveAccountWhenCustomerField_DateOfBirth_NotMatchTest() throws AccountException {
        Optional<Customer> customer11 = Optional.ofNullable(Customer.builder().customerId(1L).
                firstName("Virat").lastName("Kohli").age(32).email("viratkohli@gmail.com")
                .aadhaarNumber("233333333333l").contactNumber(9876543234L).address("Mumbai")
                .panCardNumber("AAAS234KKL").dateOfBirth("1988-09-20").build());

        when(bankRepository.findById(1L)).thenReturn(bank);
        when(customerRepository.findById(1L)).thenReturn(customer11);

        accountService.saveAccount(accountDto, customer.get().getCustomerId(), bank.get().getBankId());

        assertThrows(AccountException.class, () -> {
            accountService.saveAccount(accountDto, customer.get().getCustomerId(), bank.get().getBankId());

        });



    }




    @DisplayName("JUnit test for getAllAccount method")
    @Test
    public void getAllAccountTest() throws AccountException {
        Mockito.when(accountRepository.findAll()).thenReturn(List.of(account));
        assertEquals(1, accountService.getAllAccount().size());
    }

    @DisplayName("JUnit test for updateAccountById method")
    @Test
    public void updateAccountByIdTest() throws AccountException {
        Mockito.when(accountRepository.findById(1L)).thenReturn(Optional.ofNullable(account));

        String s = accountService.updateAccountById(accountDto, account.getAccountId());
        verify(accountRepository, times(1)).findById(accountDto.getAccountId());
    }

    @DisplayName("Junit test for deleteAccountById method")
    @Test
    public void deleteAccountByIdTest() throws AccountException {
        Mockito.when(accountRepository.findById(1L)).thenReturn(Optional.ofNullable(account));
        String result = accountService.deleteAccountById(accountDto.getAccountId());
        assertEquals("Account Id :1 deleted successfully....!!", result);
        verify(accountRepository, times(1)).deleteById(accountDto.getAccountId());

    }

    @Test
    public void testDeleteAccountByIdAccountNotFound() {
        when(accountRepository.findById(accountDto.getAccountId())).thenReturn(Optional.empty());
        AccountException exception = assertThrows(AccountException.class, () -> {
            accountService.deleteAccountById(accountDto.getAccountId());

        });

        assertEquals("Account Id does not exist", exception.getMessage());
        verify(accountRepository, never()).deleteById(anyLong());
    }

    @Test
    public void getBalanceTest() throws AccountException {
        Mockito.when(accountRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(account));
        List<Double> balance = accountService.getBalance(account.getAccountId());
        assertEquals(1, balance.stream().count());
    }

    @Test
    public void getBalanceFailureWhenAccountNotPresentTest() throws AccountException {

        Mockito.when(accountRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());
        assertThrows(AccountException.class, () -> {
            accountService.getBalance(account.getAccountId());
        });

    }

    @Test
    public void withdrawalAmountByIdTest() throws AccountException {
        Mockito.when(accountRepository.findById(account.getAccountId())).thenReturn(Optional.ofNullable(account));
        String s = accountService.withdrawalAmountById(account.getAccountId(), 1000.00);
        verify(accountRepository, times(1)).findById(eq(account.getAccountId()));

    }

    @Test
    public void FailureWithdrawalAmountByIdWhenAccountIsNotPresentTest() throws AccountException {
        Mockito.when(accountRepository.findById(account.getAccountId())).thenReturn(Optional.empty());
        assertThrows(AccountException.class, () -> {
            accountService.withdrawalAmountById(account.getAccountId(), 1000.00);

        });
    }

    @Test
    public void FailureWithdrawalAmountLessThanFiveHundredTest() throws AccountException {
        Account account = Account.builder().customerId(1L).bankId(1L).accountId(1L)
                .accountNumber(4567888).amount(788777.00)
                .isBlocked(false).aadhaarNumber(233333333333L).age(32).contactNumber(9876543234L)
                .email("redmi@gmail.com")
                .dateOfBirth("1987-09-20").firstName("Virat").lastName("Kohli").panCardNumber("AAAS234KKL").build();
        Mockito.when(accountRepository.findById(account.getAccountId())).thenReturn(Optional.of(account));
        assertThrows(AccountException.class, () -> {
            accountService.withdrawalAmountById(account.getAccountId(), 100.00);
        });
    }

    @Test
    public void FailureWithdrawalWhenAccountAmountLessThanTwoThousandTest() throws AccountException {
        Account account = Account.builder().customerId(1L).bankId(1L).accountId(1L)
                .accountNumber(4567888).amount(788.00)
                .isBlocked(false).aadhaarNumber(233333333333L).age(32).contactNumber(9876543234L)
                .email("redmi@gmail.com")
                .dateOfBirth("1987-09-20").firstName("Virat").lastName("Kohli").panCardNumber("AAAS234KKL").build();
        Mockito.when(accountRepository.findById(account.getAccountId())).thenReturn(Optional.of(account));
        assertThrows(AccountException.class, () -> {
            accountService.withdrawalAmountById(account.getAccountId(), 5000.00);
        });
    }


    @DisplayName("Junit test for DepositAmountTest")
    @Test
    public void depositAmountTest() throws AccountException {
        when(accountRepository.findById(any())).thenReturn(Optional.of(account));
        accountService.deposit(account.getAccountId(), 2000.00);
        verify(accountRepository, times(1)).findById(eq(account.getAccountId()));

    }

    @DisplayName("Junit test failureDepositAmountTest")
    @Test
    public void depositAmountFailureTest() throws AccountException {
        Account account = Account.builder().customerId(1L).bankId(1L).accountId(1L)
                .accountNumber(4567888).amount(88777.00)
                .isBlocked(false).aadhaarNumber(233333333333L).age(32).contactNumber(9876543234L)
                .email("redmi@gmail.com")
                .dateOfBirth("1987-09-20").firstName("Virat").lastName("Kohli").panCardNumber("AAAS234KKL").build();
        when(accountRepository.findById(any())).thenReturn(Optional.of(account));

        assertThrows(AccountException.class, () -> {
            accountService.deposit(account.getAccountId(), 96.00);
        });
    }

    @DisplayName("Junit test for blockedAccountTest")
    @Test
    public void blockedTest() throws AccountException {
        Mockito.when(accountRepository.findById(account.getAccountId())).thenReturn(Optional.of(account));
        String blocked = accountService.isBlocked(account.getAccountId());
        assertFalse(account.isBlocked()); //  isBlocked() method returns a boolean

    }
    @Test
    public void blockedAmountTest() throws AccountException {
        Account account = Account.builder().customerId(1L).bankId(1L).accountId(1L)
                .accountNumber(4567888).amount(777.00)
                .isBlocked(false).aadhaarNumber(233333333333L).age(32).contactNumber(9876543234L)
                .email("redmi@gmail.com")
                .dateOfBirth("1987-09-20").firstName("Virat").lastName("Kohli").panCardNumber("AAAS234KKL").build();
        Mockito.when(accountRepository.findById(account.getAccountId())).thenReturn(Optional.of(account));
        String blocked = accountService.isBlocked(account.getAccountId());
        assertTrue(account.isBlocked()); //  isBlocked() method returns a boolean

    }
//    public String isBlocked(Long accountId) throws AccountException {
//        var account = accountRepository.findById(accountId).orElseThrow(() -> new AccountException("Account not present"));
//        if (accountRepository.findById(accountId).isPresent()) {
//
//            double amount = account.getAmount();
//            if (amount <= 2000) {
//                account.setBlocked(true);
//                accountRepository.save(account);
//            } else {
//                account.setBlocked(false);
//                accountRepository.save(account);
//            }
//        }
//        return "account is blocked :".formatted(account.isBlocked());



}