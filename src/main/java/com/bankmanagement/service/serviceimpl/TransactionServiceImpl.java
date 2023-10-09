package com.bankmanagement.service.serviceimpl;
import com.bankmanagement.dto.AccountDto;
import com.bankmanagement.dto.TransactionDto;
import com.bankmanagement.entity.Account;
import com.bankmanagement.entity.Transaction;
import com.bankmanagement.exception.TransactionException;
import com.bankmanagement.repository.AccountRepository;
import com.bankmanagement.repository.CustomerRepository;
import com.bankmanagement.repository.TransactionRepository;
import com.bankmanagement.service.AccountService;
import com.bankmanagement.service.CustomerService;
import com.bankmanagement.service.TransactionService;
import org.springframework.beans.BeanUtils;
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
    public String transferMoney(TransactionDto transactionDto) {

        Optional<Account> byAccountNumberFrom = Optional.ofNullable(accountRepository.findByAccountNumber(transactionDto.getAccountNumberFrom()));
        Optional<Account> byAccountNumberTo = Optional.ofNullable(accountRepository.findByAccountNumber(transactionDto.getAccountNumberTo()));

        if (byAccountNumberFrom.isEmpty() && byAccountNumberTo.isEmpty()) {
            throw new TransactionException("Account not present");
        }


        Account toAccount = null;
        if (byAccountNumberFrom.isPresent() && byAccountNumberTo.isPresent()) {
            Account fromAccount = byAccountNumberFrom.get();
            toAccount = byAccountNumberTo.get();
            double amountFromTransaction = fromAccount.getAmount() - transactionDto.getAmount();
            double amountToAccount = toAccount.getAmount() + transactionDto.getAmount();

            fromAccount.setAmount(amountFromTransaction);
            toAccount.setAmount(amountToAccount);
            Transaction transaction = new Transaction();
            BeanUtils.copyProperties(transactionDto, transaction);

            accountRepository.save(fromAccount);
            accountRepository.save(toAccount);
            transactionRepository.save(transaction);
        }

        return "your transaction from Account Number :" + transactionDto.getAccountNumberFrom() + "to Account Number :" + transactionDto.getAccountNumberTo() + "is successfully__!! \n" +
                "Transfer Debited Amount : " + transactionDto.getAmount() + "\n" +
                "Credited Amount " + toAccount.getAmount() + transactionDto.getAmount();

    }


}