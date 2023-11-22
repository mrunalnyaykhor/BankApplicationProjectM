package com.bankmanagement.service.serviceimpl;

import com.bankmanagement.constant.ApplicationConstant;
import com.bankmanagement.dto.AccountDto;
import com.bankmanagement.dto.CustomerDto;
import com.bankmanagement.entity.Account;
import com.bankmanagement.entity.Bank;
import com.bankmanagement.entity.Customer;
import com.bankmanagement.exception.BankException;
import com.bankmanagement.exception.CustomerException;
import com.bankmanagement.repository.AccountRepository;
import com.bankmanagement.repository.BankRepository;
import com.bankmanagement.repository.CustomerRepository;
import com.bankmanagement.service.AccountService;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class AccountServiceImpl implements AccountService {
    Account account1 = null;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private BankRepository bankRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public AccountDto saveAccount(AccountDto accountdto, Long customerId, Long bankId) throws AccountException {
         bankRepository.findById(bankId).orElseThrow(() -> new BankException(ApplicationConstant.BANK_NOT_AVAILABLE));
         customerRepository.findById(customerId).orElseThrow(() -> new CustomerException(ApplicationConstant.CUSTOMER_NOT_PRESENT));
        log.info("Bank and customer present in database");

        Account account = new Account();
        Optional<Customer> customer = customerRepository.findById(customerId);
        Optional<Bank> bank = bankRepository.findById(bankId);
        Random random = new Random();
        long accNo = random.nextLong(44);
        long sAccNum = accNo;
        BigInteger accountNo = new BigInteger("444" + sAccNum);
        accountdto.setAccountNumber(Long.parseLong(accountNo.toString()));
        accountdto.getAccountNumber();
        if(customer.isPresent()) {
            Customer customer1 = customer.get();
            boolean firstName = customer1.getFirstName().equals(accountdto.getFirstName());
            boolean lastName = customer1.getLastName().equals(accountdto.getLastName());
            boolean panCard = customer1.getPanCardNumber().equals(accountdto.getPanCardNumber());
            boolean contact = customer1.getContactNumber().equals(accountdto.getContactNumber());
            boolean dob = customer1.getDateOfBirth().equals(accountdto.getDateOfBirth());
            if ((!firstName)) {
                throw new AccountException(ApplicationConstant.NAME_INCORRECT);
            }
            if ((!lastName)) {
                throw new AccountException(ApplicationConstant.LAST_NAME_INCORRECT);
            }
            if ((!panCard)) {
                throw new AccountException(ApplicationConstant.PANCARD_INCORRECT);
            }
            if ((!contact)) {
                throw new AccountException(ApplicationConstant.CONTACT_INCORRECT);
            }
            if ((!dob)) {
                throw new AccountException(ApplicationConstant.DATE_OF_BIRTH_INCORRECT);
            }

        }

        BeanUtils.copyProperties(accountdto, account);
        account.setCustomer(customer.get());
        accountdto.setCustomerId(customer.get().getCustomerId());
        account.setBank(bank.get());
        accountdto.setBankId(bank.get().getBankId());
        accountRepository.save(account);
        return accountdto;
    }

    @Override
    public List<AccountDto> getAllAccount() throws AccountException {
        if (accountRepository.findAll().isEmpty())
        {
            throw new AccountException(ApplicationConstant.ACCOUNT_NOT_FOUND);
        }
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
        if (accountOptional.isEmpty())
        {
            throw new AccountException("Account Id does not exist");
        }
            accountRepository.deleteById(accountId);
        return "Account Id :%d deleted successfully....!!".formatted(accountId);}
    @Override
    public Double getBalance(Long accountId) throws AccountException {
        Optional<Account> optionalAccount1 = accountRepository.findById(accountId);
        if (optionalAccount1.isEmpty()) {
            throw new AccountException(ApplicationConstant.ACCOUNT_NOT_FOUND);
        }
        return optionalAccount1.get().getAmount();

    }

    @Override
    public String withdrawalAmountById(Long accountId, Double amount) throws AccountException {

        Optional<Account> account = accountRepository.findById(accountId);
        if (account.isEmpty()) {
            throw new AccountException(ApplicationConstant.ACCOUNT_NOT_FOUND);
        }if (account.isPresent()) {
            account1 = account.get();
            account.stream().filter(amt -> amount >= 500 && amt.getAmount() >= 2000).forEach(account2 -> {
                account2.setAmount((account2.getAmount() - amount));
                accountRepository.save(account2);});
        }

        if (amount <= 500) {

            throw new AccountException(ApplicationConstant.MORE_THAN_FIVE_HUNDRED);

        } else if (account1.getAmount() <= 2000) {

            throw new AccountException(ApplicationConstant.INSUFFICIENT_BALANCE);


        }

        return ApplicationConstant.AMOUNT_WITHDRAWAL_SUCCESSFULLY.formatted(amount, account1.getAmount());

    }
    @Override
    public String deposit(Long accountId, Double amount) throws AccountException {
        Optional<Account> account = accountRepository.findById(accountId);
        if (account.isPresent() && amount>=100) {
            Account account2 = account.get();
            account2.setAmount(account2.getAmount() + amount);
            accountRepository.save(account2);
        }
        if (amount <= 100) {
            throw new AccountException(ApplicationConstant.ENTER_MORE_THAN_HUNDRED_RUPEES);

        }
        return ApplicationConstant.AMOUNT_DEPOSITED_SUCCESSFULLY;
    }


    @Override
    public String isBlocked(Long accountId) throws AccountException {
        var account = accountRepository.findById(accountId).orElseThrow(() -> new AccountException(ApplicationConstant.ACCOUNT_NOT_FOUND));
        if (accountRepository.findById(accountId).isPresent()) {

            double amount = account.getAmount();
            if (amount <= 2000) {
                account.setBlocked(true);
                accountRepository.save(account);
            } else {
                account.setBlocked(false);
                accountRepository.save(account);
            }
        }
        return ApplicationConstant.ACCOUNT_STATUS.formatted(account.isBlocked());
    }

    @Override
    public AccountDto getAccountById(Long accountId) {
        Optional<Account> accountOptional = accountRepository.findById(accountId);
        AccountDto accountDto = new AccountDto();
        Account account = new Account();
        BeanUtils.copyProperties(account, accountDto);
        if(accountOptional.isPresent()){
            Account account2 = accountOptional.get();
            BeanUtils.copyProperties(account2, accountDto);
        }
        return accountDto;
}
}
