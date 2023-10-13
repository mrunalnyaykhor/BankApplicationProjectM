package com.bankmanagement.service.serviceimpl;

import com.bankmanagement.dto.AccountDto;
import com.bankmanagement.entity.Account;
import com.bankmanagement.entity.Bank;
import com.bankmanagement.entity.Customer;
import com.bankmanagement.exception.BankException;
import com.bankmanagement.exception.CustomerException;
import com.bankmanagement.repository.AccountRepository;
import com.bankmanagement.repository.BankRepository;
import com.bankmanagement.repository.CustomerRepository;
import com.bankmanagement.service.AccountService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountException;
import java.math.BigInteger;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {
    int x=10;
    Account account1 = null;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private BankRepository bankRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public AccountDto saveAccount(AccountDto accountdto, Long customerId, Long bankId) throws AccountException {


        var email = accountdto.getEmail();
        var customerOptional = customerRepository.findById(customerId).orElseThrow(() -> new CustomerException("customer not present & Cannot create Account"));
        var bankOptional = bankRepository.findById(bankId).orElseThrow(() -> new BankException("Bank not Present & Cannot create Account"));
        if (email.endsWith("@gmail.com") || email.endsWith("@yahoo.com")) {
            Account account = new Account();
            Optional<Customer> customer = customerRepository.findById(customerId);
            Optional<Bank> bank = bankRepository.findById(bankId);
            Random random = new Random();
            Long accNo = random.nextLong(55);
            Long sAccNum = accNo;
            BigInteger accountNo = new BigInteger("5555" + sAccNum);
            accountdto.setAccountNumber(Long.parseLong(accountNo.toString()));
            account.getAccountNumber();

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
        if (accountRepository.findAll().isEmpty()) throw new AccountException("customers Data not present in Database");
        return accountRepository.findAll().stream().filter(Objects::nonNull).map(account -> {
            AccountDto accountDto = new AccountDto();
            BeanUtils.copyProperties(account, accountDto);
            return accountDto;
        }).collect(Collectors.toList());
    }


    @Override
    public String updateAccountById(AccountDto accountDto, Long accountId) throws AccountException {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new AccountException("Account not present"));
        BeanUtils.copyProperties(accountDto, account);
        accountRepository.save(account);

        return "Account Id Number: %d Updated Successfully".formatted(account.getAccountId());
    }

    @Override
    public String deleteAccountById(Long accountId) throws AccountException {
        Optional<Account> accountOptional = accountRepository.findById(accountId);

        if (accountOptional.isEmpty()) {
            throw new AccountException("Account Id does not exist");
        }
        accountOptional.ifPresent(account -> {
            accountRepository.deleteById(accountId);
        });

        return "Account Id :%d deleted successfully....!!".formatted(accountId);
    }

    @Override
    public List<Double> getBalance(Long accountId) throws AccountException {
        Optional<Account> optionalAccount1 = accountRepository.findById(accountId);
        if (optionalAccount1.isEmpty()) {
            throw new AccountException("Account not exist");
        }
        return optionalAccount1.stream().filter(Objects::nonNull).map(Account::getAmount).collect(Collectors.toList());

    }

    @Override
    public String withdrawalAmountById(Long accountId, Double amount) throws AccountException {
        Optional<Account> account = accountRepository.findById(accountId);
        if (account.isPresent()) {
            account1 = account.get();
            account.stream().filter(amt -> amount >= 500 && amt.getAmount() >= 2000).forEach(account2 -> {
                account2.setAmount((account2.getAmount() - amount));
                accountRepository.save(account2);
            });
        }

        if (amount <= 500) {
            throw new AccountException("Enter more than 500 rs for withdrawal");
        } else if (account1.getAmount() <= 2000) {
            throw new AccountException("Insufficient Balance");
        } else {
            System.out.println("some problem");
        }

        return "Amount withdrawal successfully %.2f && Required Balance is %.2f".formatted(amount, account1.getAmount());
    }

    @Override
    public String deposit(Long accountId, Double amount) throws AccountException {
        Optional<Account> account = accountRepository.findById(accountId);
        if (account.isEmpty()) {
            account1 = account.get();
            account.stream().filter(amount1 -> amount >= 100).forEach(account2 -> {
                account2.setAmount(account2.getAmount() + amount);
                accountRepository.save(account2);
            });

        } else if (amount <= 100) {
            throw new AccountException("Please Enter More than 100 rs");
        }

        return "Your Amount deposited successfully: deposited amount: %.2f%nNow Current Balance is: %.2f".formatted(amount, account1.getAmount());
    }

    @Override
    public AccountDto updateAccountStatus(AccountDto accountDto, Long accountId) throws AccountException {
        Optional<Account> optionalAccount = accountRepository.findById(accountId);
        if (optionalAccount.isEmpty()) {
            throw new AccountException("Account not present");
        }
        Account accounts = new Account();
        BeanUtils.copyProperties(accounts, accountDto);
        Account account = optionalAccount.get();
        accountDto.setBlocked(accountDto.getAmount() < 1000);
        accountRepository.save(account);
        return accountDto;
    }


}
