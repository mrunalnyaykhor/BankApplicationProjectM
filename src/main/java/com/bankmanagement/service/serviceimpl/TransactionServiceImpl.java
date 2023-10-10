package com.bankmanagement.service.serviceimpl;

import com.bankmanagement.dto.TransactionDto;
import com.bankmanagement.entity.Account;
import com.bankmanagement.entity.Transaction;
import com.bankmanagement.exception.TransactionException;
import com.bankmanagement.repository.AccountRepository;
import com.bankmanagement.repository.CustomerRepository;
import com.bankmanagement.repository.TransactionRepository;
import com.bankmanagement.service.AccountService;
import com.bankmanagement.service.TransactionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private AccountService accountService;
    Account toAccount = null;
    @Autowired
    private AccountRepository accountRepository;

    @Override
    @Transactional
    public String transferMoney(TransactionDto transactionDto) {

        Optional<Account> byAccountNumberFrom = Optional.ofNullable(accountRepository.findByAccountNumber(transactionDto.getAccountNumberFrom()));
        Optional<Account> byAccountNumberTo = Optional.ofNullable(accountRepository.findByAccountNumber(transactionDto.getAccountNumberTo()));

        if (byAccountNumberFrom.isEmpty() && byAccountNumberTo.isEmpty()) {
            throw new TransactionException("Account not present");
        }
        if (byAccountNumberFrom.isPresent() && byAccountNumberTo.isPresent()) {
            Account fromAccount = byAccountNumberFrom.get();
            if (fromAccount.getAmount() < 1000) {
                throw new TransactionException("You Cannot Transfer Money Because Your Balance is below 1000rs & Now your amount of Balance is : " + fromAccount.getAmount());
            } else {
                Double doubleAmt = fromAccount.getAmount() - transactionDto.getAmount();
                if (doubleAmt >= 1000) {
                    toAccount = byAccountNumberTo.get();
                    double amountFromTransaction = fromAccount.getAmount() - transactionDto.getAmount();
                    double amountToAccount = toAccount.getAmount() + transactionDto.getAmount();

                    fromAccount.setAmount(amountFromTransaction);
                    toAccount.setAmount(amountToAccount);
                } else {
                    throw new TransactionException("Cannot send money Insufficient Balance");
                }
            }
            Transaction transaction = new Transaction();
            BeanUtils.copyProperties(transactionDto, transaction);
            accountRepository.save(fromAccount);
            accountRepository.save(toAccount);
            transactionRepository.save(transaction);
        }

        return "your transaction from Account Number :" + transactionDto.getAccountNumberFrom() + "to Account Number :" + transactionDto.getAccountNumberTo() + "is successfully__!! \n" +
                "Transfer Debited Amount : " + transactionDto.getAmount() + "rs" + "\n" +
                "Now Credited Account Balance is :" + toAccount.getAmount() + transactionDto.getAmount() + "rs";

    }


    @Override
    public List<TransactionDto> findTransaction(Long accountId) {
        Optional<Account> accountOptional = accountRepository.findById(accountId);
        if(accountOptional.isPresent())
        {
            Account account= accountOptional.get();
        }
      accountOptional.stream().filter(Objects::nonNull).map(transaction->{

          return null;
      }).collect(Collectors.toList());
        return null;
    }

}