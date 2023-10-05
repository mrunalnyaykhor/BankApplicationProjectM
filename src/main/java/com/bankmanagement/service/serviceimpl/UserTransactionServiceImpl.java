package com.bankmanagement.service.serviceimpl;
import com.bankmanagement.dto.UserTransactionDto;
import com.bankmanagement.entity.Account;
import com.bankmanagement.entity.UserTransaction;
import com.bankmanagement.repository.AccountRepository;
import com.bankmanagement.repository.UserTransactionRepository;
import com.bankmanagement.service.AccountService;
import com.bankmanagement.service.UserTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserTransactionServiceImpl implements UserTransactionService {
    @Autowired
    private UserTransactionRepository userTransactionRepository;

    @Autowired
    private AccountService accountService;
    @Autowired
    private AccountRepository accountRepository;


    @Override
    public UserTransactionDto transactionAmount(UserTransactionDto userTransactionDto, Long accountId) {
       Long accountNumberFrom = userTransactionDto.getAccountNumberFrom();
        double amount = userTransactionDto.getAmount();
        Long accountNumberTo = userTransactionDto.getAccountNumberTo();

      //  Account account = accountService.findByCustomerAccountNumber(accountNumberTo);
        return null;
    }


    @Override
    public UserTransaction depositAmount(UserTransaction userTransaction) {

        Long fromAccountNumber = userTransaction.getAccountNumberFrom();
        Long toAccountNumber = userTransaction.getAccountNumberTo();
        Double amount = userTransaction.getTransferAmount();

        Account toAccount = accountService.findByCustomerAccountNumber(toAccountNumber);
        toAccount.setBalance(toAccount.getBalance() + amount);
        accountRepository.save(toAccount);

        Account fromaccount = accountService.findByCustomerAccountNumber(fromAccountNumber);
        fromaccount.setBalance(toAccount.getBalance() - amount);
        accountRepository.save(fromaccount);

        // UserTransaction transaction = userTransactionRepository.save(new UserTransaction(userTransaction.getAmount(), userTransaction.getAccountNumberFrom(), userTransaction.getAccountNumberTo(), userTransaction.getIfscCode(), userTransaction.getName(), new Date()));


        return null;
    }


}