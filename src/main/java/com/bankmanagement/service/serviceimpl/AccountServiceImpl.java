package com.bankmanagement.service.serviceimpl;
import com.bankmanagement.constant.ApplicationConstant;
import com.bankmanagement.dto.AccountDto;
import com.bankmanagement.entity.Account;
import com.bankmanagement.entity.Bank;
import com.bankmanagement.entity.Customer;
import com.bankmanagement.enump.AccountType;
import com.bankmanagement.exception.AccountException;
import com.bankmanagement.exception.BankException;
import com.bankmanagement.repository.AccountRepository;
import com.bankmanagement.repository.BankRepository;
import com.bankmanagement.repository.CustomerRepository;
import com.bankmanagement.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
@Service
@Slf4j
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private BankRepository bankRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public ResponseEntity<String> saveAccount(AccountDto accountdto) throws ExecutionException, InterruptedException {

        CompletableFuture<Bank> bank = CompletableFuture.supplyAsync(() ->
                bankRepository.findById(accountdto.getBankId()).orElseThrow(() -> new BankException(ApplicationConstant.BANK_NOT_AVAILABLE)));
        Optional<Customer> customer = customerRepository.findById(accountdto.getCustomerId());
        if(customer.isEmpty())
        {
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApplicationConstant.CUSTOMER_NOT_PRESENT);
        }

        CompletableFuture<Void> allFutures = CompletableFuture.allOf( bank);
        allFutures.join();
        //TO DO- CompletableFuture async
        // https://www.baeldung.com/java-completablefuture
        log.info(ApplicationConstant.BANK_AND_CUSTOMER_PRESENT);
        Account account = new Account();
        BeanUtils.copyProperties(accountdto, account);
        account.setBank(bank.get());
        account.setCustomer(customer.get());
        List<Account> byCustomerIdAndBankIdAndAccountType =
                accountRepository.findByCustomerAndBankAndAccountType(account.getCustomer(), account.getBank(), account.getAccountType());
        if (!byCustomerIdAndBankIdAndAccountType.isEmpty()) {
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApplicationConstant.ACCOUNT_ALREADY_PRESENT);
        }
        else if ((account.getAccountType().name().equals(AccountType.SAVING.name())) && (
                account.getAmount() < AccountType.SAVING.getAmount())) {

            return  ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApplicationConstant.MINIMUM_BALANCE_FOR_SAVING_ACCOUNT );
        } else if ((account.getAccountType().name().equals(AccountType.CURRENT.name())) && (
                account.getAmount() < AccountType.CURRENT.getAmount())) {
            return  ResponseEntity.status(HttpStatus.OK).body(ApplicationConstant.MINIMUM_BALANCE_FOR_CURRENT_ACCOUNT);
        }
        String random;
        Account byAccNo;
        do {
            random = RandomStringUtils.random(12, false, true);
            byAccNo = accountRepository.findByAccountNumber(Long.valueOf(random));
        } while (!Objects.isNull(byAccNo));    //......................................
        account.setAccountNumber(Long.parseLong(random));
        accountRepository.save(account);
        return ResponseEntity.status(HttpStatus.OK).body(ApplicationConstant.ACCOUNT_IS_CREATED);
    }

    @Override
    public List<Account> getAllAccount() throws AccountException {
        accountRepository.findAll().stream().findAny().orElseThrow(() ->
                new AccountException(ApplicationConstant.ACCOUNT_NOT_FOUND));
        return accountRepository.findAll().stream().filter(Objects::nonNull).collect(Collectors.toList());
    }


    @Override
    public ResponseEntity<String> updateAccountById(Account account) {
        Account account1 = accountRepository.findById(account.getAccountId()).orElseThrow(
                () -> new AccountException(ApplicationConstant.ACCOUNT_NOT_FOUND));
        Bank bank = bankRepository.findById(account.getBankId()).orElseThrow(
                () -> new BankException(ApplicationConstant.BANK_NOT_AVAILABLE));
        Optional<Customer> customer = customerRepository.findById(account.getCustomerId());
        if(customer.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApplicationConstant.CUSTOMER_NOT_PRESENT);
        }
        account.setBank(bank);
        account.setCustomer(customer.get());
        accountRepository.save(account);
        return ResponseEntity.status(HttpStatus.OK).body(ApplicationConstant.ACCOUNT_ID_UPDATE_SUCCESSFULLY);
    }

    @Override
    public String deleteAccountById(Long accountId) throws AccountException {
        accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountException(ApplicationConstant.ACCOUNT_ID_DOES_NOT_EXIST));
        accountRepository.deleteById(accountId);
        return ApplicationConstant.ACCOUNT_ID_DELETED_SUCCESSFULLY;
    }

    @Override
    public Double getBalance(Long accountId) throws AccountException {
        return accountRepository.findById(accountId)
                .orElseThrow(() ->
                        new AccountException(ApplicationConstant.ACCOUNT_NOT_FOUND)).getAmount();

    }

    @Override
    public String withdrawalAmountById(Long accountId, Double amount) throws AccountException {
        return null;
    }


    @Override
    public String deposit(Long accountId, Double amount) throws AccountException {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountException(ApplicationConstant.ACCOUNT_NOT_FOUND));
        if (amount <= 500) {
            throw new AccountException(ApplicationConstant.ENTER_MORE_THAN_HUNDRED_RUPEES);
        }
        account.setAmount(account.getAmount() + amount);
        accountRepository.save(account);
        return ApplicationConstant.AMOUNT_DEPOSITED_SUCCESSFULLY;
    }


    @Override
    public String isBlocked(Long accountId) throws AccountException {
        var account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountException(ApplicationConstant.ACCOUNT_NOT_FOUND));
        if (account.getAmount() < AccountType.CURRENT.getAmount() || account.getAmount() < AccountType.SAVING.getAmount()) {
            account.setBlocked(true);
            accountRepository.save(account);
        }
        return String.format(ApplicationConstant.ACCOUNT_STATUS, account.isBlocked());
    }
    @Override
    public String accountStatus(Long accountId) throws AccountException {
        var account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountException(ApplicationConstant.ACCOUNT_NOT_FOUND));
        return String.format(ApplicationConstant.ACCOUNT_TYPE_STATUS,account.getAccountType());


    }

    @Override
    public List<Account> getAllSavingAccount() {
       List<Account> savingAccounts = accountRepository.findByAccountType(AccountType.SAVING);

        if (savingAccounts.isEmpty()) {
            throw new AccountException(ApplicationConstant.ACCOUNT_NOT_FOUND);
        }

       return savingAccounts;
    }

    @Override
    public AccountDto getMyAccountDetails(Long accountNumber) {

        Account accounts = accountRepository.findByAccountNumber(accountNumber);
        AccountDto accountDto = new AccountDto();
        BeanUtils.copyProperties(accounts,accountDto);
        return accountDto;
    }


    @Override
    public AccountDto getAccountById(Long accountId) throws AccountException {
        var account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountException(ApplicationConstant.ACCOUNT_NOT_FOUND));
        AccountDto accountDto = new AccountDto();
        BeanUtils.copyProperties(account, accountDto);

        return accountDto;
    }


}
