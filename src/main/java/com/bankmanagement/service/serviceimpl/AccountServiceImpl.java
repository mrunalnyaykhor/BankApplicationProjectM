package com.bankmanagement.service.serviceimpl;
import com.bankmanagement.constant.ApplicationConstant;
import com.bankmanagement.dto.AccountDto;
import com.bankmanagement.entity.Account;
import com.bankmanagement.entity.Bank;
import com.bankmanagement.entity.Customer;
import com.bankmanagement.enump.AccountType;
import com.bankmanagement.exception.AccountException;
import com.bankmanagement.exception.BankException;
import com.bankmanagement.exception.CustomerException;
import com.bankmanagement.repository.AccountRepository;
import com.bankmanagement.repository.BankRepository;
import com.bankmanagement.repository.CustomerRepository;
import com.bankmanagement.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
    public String saveAccount(AccountDto accountdto) throws ExecutionException, InterruptedException {
        CompletableFuture<Optional<Account>> accountFuture = CompletableFuture.supplyAsync(() -> {
            Optional<Account> accountId = accountRepository.findById(accountdto.getAccountId());
            return accountId;});
        CompletableFuture<Bank> bank = CompletableFuture.supplyAsync(() ->
                bankRepository.findById(accountdto.getBankId())
                        .orElseThrow(() -> new BankException(ApplicationConstant.BANK_NOT_AVAILABLE)));
        CompletableFuture<Customer> customer = CompletableFuture.supplyAsync(() ->
                customerRepository.findById(accountdto.getCustomerId())
                        .orElseThrow(() -> new CustomerException(ApplicationConstant.CUSTOMER_NOT_PRESENT)));
        CompletableFuture<Void> allFutures = CompletableFuture.allOf(accountFuture,bank, customer);
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
            throw new AccountException(ApplicationConstant.ACCOUNT_ALREADY_PRESENT);

    }
        else if ((account.getAccountType().name().equals(AccountType.SAVING.name())) && (
                account.getAmount() < AccountType.SAVING.getAmount())) {
            return ApplicationConstant.MINIMUM_BALANCE_FOR_SAVING_ACCOUNT ;
        } else if ((account.getAccountType().name().equals(AccountType.CURRENT.name())) && (
                account.getAmount() < AccountType.CURRENT.getAmount())) {
            return ApplicationConstant.MINIMUM_BALANCE_FOR_CURRENT_ACCOUNT;
        }
        String random;
        Account byAccNo;
        do {
            random = RandomStringUtils.random(12, false, true);
            byAccNo = accountRepository.findByAccountNumber(Long.valueOf(random));
        } while (!Objects.isNull(byAccNo));
        account.setAccountNumber(Long.parseLong(random));

        return Optional.of(accountRepository.save(account))
                .map(savedAccount -> ApplicationConstant.ACCOUNT_IS_CREATED)
                .orElseThrow(() -> new AccountException(ApplicationConstant.ERROR_OCCURRED_WHILE_SAVING_INTO_THE_DATA_BASE));

    }

    @Override
    public List<AccountDto> getAllAccount() throws AccountException {
        accountRepository.findAll().stream().findAny().orElseThrow(()->
                new AccountException(ApplicationConstant.ACCOUNT_NOT_FOUND) );

        return accountRepository.findAll().stream().filter(Objects::nonNull).map(account -> {
            AccountDto accountDto = new AccountDto();
            BeanUtils.copyProperties(account, accountDto);
            return accountDto;
        }).collect(Collectors.toList());
    }


    @Override
    public String updateAccountById(AccountDto accountDto) {
        Account account = accountRepository.findById(accountDto.getAccountId()).orElseThrow(
                () -> new AccountException(ApplicationConstant.ACCOUNT_NOT_FOUND));
        BeanUtils.copyProperties(accountDto, account);
        accountRepository.save(account);
        return ApplicationConstant.ACCOUNT_ID_UPDATE_SUCCESSFULLY;
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
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountException(ApplicationConstant.ACCOUNT_NOT_FOUND));
        if (amount <= 500) {
            throw new AccountException(ApplicationConstant.WITHDRAWAL_AMOUNT_MORE_THAN_FIVE_HUNDRED);
        }
        if (amount > account.getAmount() - 2000) {
            throw new AccountException(ApplicationConstant.INSUFFICIENT_BALANCE);
        }
        if (amount >= 500 && amount <= 20000) {
            account.setAmount(account.getAmount() - amount);
            accountRepository.save(account);
        }
        return ApplicationConstant.AMOUNT_WITHDRAWAL_SUCCESSFULLY;

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
    public AccountDto getAccountById(Long accountId) throws AccountException {
        var account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountException(ApplicationConstant.ACCOUNT_NOT_FOUND));
        AccountDto accountDto = new AccountDto();
        BeanUtils.copyProperties(account, accountDto);

        return accountDto;
    }


}
