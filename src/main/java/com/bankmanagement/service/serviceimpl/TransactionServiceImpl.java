package com.bankmanagement.service.serviceimpl;
import com.bankmanagement.dto.TransactionDto;
import com.bankmanagement.entity.Account;
import com.bankmanagement.entity.Transaction;
import com.bankmanagement.repository.AccountRepository;
import com.bankmanagement.repository.CustomerRepository;
import com.bankmanagement.repository.TransactionRepository;
import com.bankmanagement.service.AccountService;
import com.bankmanagement.service.CustomerService;
import com.bankmanagement.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AccountService accountService;
    @Autowired
    private AccountRepository accountRepository;


    @Override
    public TransactionDto transactionAmount(TransactionDto transactionDto, Long accountId) {

      Optional<Account>  account1 =  accountRepository.findById(accountId);
      Optional<Account>  account2 =  accountRepository.findById(accountId);
      Long accountNumberFrom = transactionDto.getAccountNumberFrom();
      Long accountNumberTo = transactionDto.getAccountNumberTo();
        Double amount = transactionDto.getTransferAmount();

//        Account toAccount = accountService.findByAccountNumber(accountNumberTo);
//        toAccount.setBalance(toAccount.getBalance() + amount);
//        accountRepository.save(toAccount);
//
//        Account fromaccount = accountService.findByAccountNumber(accountNumberFrom);
//        fromaccount.setBalance(toAccount.getBalance() - amount);
//        accountRepository.save(fromaccount);

        return transactionDto;
    }

    @Override
    public String transferMoney(TransactionDto transactionDto) {

        Account byAccountNumberFrom = accountRepository.findByAccountNumber(transactionDto.getAccountNumberFrom());
        Account byAccountNumberTo = accountRepository.findByAccountNumber(transactionDto.getAccountNumberTo());


        return null;
    }


}