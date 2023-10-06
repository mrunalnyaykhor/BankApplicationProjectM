package com.bankmanagement.service.serviceimpl;

import com.bankmanagement.dto.AccountDto;
import com.bankmanagement.entity.Account;
import com.bankmanagement.entity.Bank;
import com.bankmanagement.entity.Customer;
import com.bankmanagement.exception.CustomerException;
import com.bankmanagement.repository.AccountRepository;
import com.bankmanagement.repository.BankRepository;
import com.bankmanagement.repository.CustomerRepository;
import com.bankmanagement.service.AccountService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private BankRepository bankRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public AccountDto saveAccount(AccountDto accountdto,Long customerId,Long bankId) {
        Optional<Customer> customerOptional =customerRepository.findById(customerId);
        if(customerOptional.isEmpty())
            throw new CustomerException("customer not present & Cannot create Account");
        if(customerOptional.isPresent()) {

        Account account= new Account();
        Optional<Customer> customer = customerRepository.findById(customerId);
        Optional<Bank> bank = bankRepository.findById(bankId);
        BeanUtils.copyProperties(accountdto, account);
        account.setCustomer(customer.get());
        accountdto.setCustomerId(customer.get().getCustomerId());
        account.setBank(bank.get());
        accountdto.setBankId(bank.get().getBankId());

            accountRepository.save(account);
        }
            return accountdto;

    }

    @Override
    public List<AccountDto> getAllAccount() throws AccountException {
        if (accountRepository.findAll().isEmpty())
            throw new AccountException("customers Data not present in Database");
        return accountRepository.findAll().stream().filter(Objects::nonNull).map(account -> {
            AccountDto accountDto = new AccountDto();
            BeanUtils.copyProperties(account, accountDto);
            return accountDto;
        }).collect(Collectors.toList());
    }

    @Override
    public List<AccountDto> accountFindById(Long accountId) throws AccountException {
        if (accountRepository.findById(accountId).isEmpty())
            throw new AccountException("Account not present");
        return accountRepository.findById(accountId).stream().filter(Objects::nonNull)
                .map(account -> {
                    AccountDto accountDto = new AccountDto();
                    BeanUtils.copyProperties(account, accountDto);
                    return accountDto;
                }).collect(Collectors.toList());
    }

    @Override
    public AccountDto updateAccountById(AccountDto accountDto, Long accountId) throws AccountException {
        if (accountRepository.findById(accountId).isEmpty())
            throw new AccountException("This AccountId Doesn't exist");
        if (accountRepository.findById(accountId).isPresent()) {
            Account account = new Account();
            BeanUtils.copyProperties(accountDto, account);
            accountRepository.save(account);
        }
        return accountDto;
    }

    @Override
    public String deleteAccountById(Long accountId) {
        if (accountRepository.findById(accountId).isPresent()) {
            accountRepository.deleteById(accountId);
        }
        return "Account Id deleted successfully....!!" + accountId;
    }

    @Override
    public List<AccountDto> getAmountById(Double amount) throws AccountException {
        Optional<Account> optionalAccount = accountRepository.findById(amount);
        if (optionalAccount.isEmpty())
            throw new AccountException("Account not available");
        return accountRepository.findById(amount).stream().filter(Objects::nonNull)
                .map(amounts -> {
                    AccountDto accountDto = new AccountDto();
                    BeanUtils.copyProperties(amounts, accountDto);
                    return accountDto;
                }).collect(Collectors.toList());
    }

    @Override
    public AccountDto depositAmount(AccountDto accountDto, Long accountId) throws AccountException {
        Optional<Account> optionalAccount = accountRepository.findById(accountId);
        if (optionalAccount.isEmpty())
            throw new AccountException("Account not Available");
        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();

            BeanUtils.copyProperties(account, accountDto);
            Double amt = account.getAmount();
            System.out.println(amt); //1000
            Double balance = amt + accountDto.getAmount();
            System.out.println(balance);
            account.setAmount(balance);
            accountRepository.save(account);
        }
        return accountDto;
    }

    @Override
    public Account withdrawalAmountById(Long accountId, Double amount) throws AccountException {
        Optional<Account> account = accountRepository.findById(accountId);

        if (account.isPresent()) {
            Account account1 = account.get();
            if (amount >= 500 && account1.getAmount() >= 2000) {

                account1.setAmount((account1.getAmount() - amount));

                accountRepository.save(account1);
            } else {
                if (amount >= 500) {
                    throw new AccountException("Enter more than 500 rs for withdrawal");
                } else if (account1.getAmount() <= 2000) {
                    throw new AccountException("Insufficient Balance");
                } else {
                    System.out.println("some problem");
                }
            }
        }
        return account.orElse(null);
    }

    @Override
    public Account deposit(Long accountId, Double amount) throws AccountException {
        Optional<Account> account = accountRepository.findById(accountId);

        if (account.isPresent()) {
            Account account1 = account.get();
            if (amount >= 100) {
                account1.setAmount(account1.getAmount() + amount);
                accountRepository.save(account1);
            } else {
                throw new AccountException("Please Enter More than 500 rs");
            }
        }
        return account.orElse(null);
    }



}
