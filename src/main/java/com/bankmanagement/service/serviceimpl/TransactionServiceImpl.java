package com.bankmanagement.service.serviceimpl;

import ch.qos.logback.core.joran.conditional.ThenOrElseActionBase;
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

import javax.security.auth.login.AccountException;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {
    Account toAccount = null;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private AccountService accountService;
    @Autowired
    private AccountRepository accountRepository;

    @Override
    @Transactional
    public String transferMoney(TransactionDto transactionDto) throws AccountException {

        Optional<Account> byAccountNumberFrom = Optional.ofNullable(accountRepository.findByAccountNumber(transactionDto.getAccountNumberFrom()));
        Optional<Account> byAccountNumberTo = Optional.ofNullable(accountRepository.findByAccountNumber(transactionDto.getAccountNumberTo()));

        if (byAccountNumberFrom.isEmpty() && byAccountNumberTo.isEmpty()) {
            throw new TransactionException("Account not present");
        }
        if (byAccountNumberFrom.isPresent() && byAccountNumberTo.isPresent()) {
            Account fromAccount = byAccountNumberFrom.get();
            if (fromAccount.getAmount() < 1000) {
                throw new TransactionException("You Cannot Transfer Money Because Your Balance is below 1000rs & Now your amount of Balance is".formatted(fromAccount.getAmount()));
            } else {
                Double doubleAmt = fromAccount.getAmount() - transactionDto.getAmount();
                if (doubleAmt >= 1000) {
                    toAccount = byAccountNumberTo.get();
                    double amountFromTransaction = fromAccount.getAmount() - transactionDto.getAmount();
                    double amountToAccount = toAccount.getAmount() + transactionDto.getAmount();

                    fromAccount.setAmount(amountFromTransaction);
                    if (fromAccount.isBlocked() == true) {
                        throw new AccountException("You cannot send Money Your Account is Blocked Please Deposit Money in your Account");
                    }
                    toAccount.setAmount(amountToAccount);
                    double dataBaseAmount = toAccount.getAmount();
                    if(dataBaseAmount >= 2000){
                       toAccount.setBlocked(false);
                    }

                }
                else {
                    throw new TransactionException("Cannot send money Insufficient Balance");
                }
            }
            LocalDate date = LocalDate.now();

            Transaction transaction = new Transaction();
            transaction.setTransactionDate(date);

            BeanUtils.copyProperties(transactionDto, transaction);
            accountRepository.save(fromAccount);
            accountRepository.save(toAccount);
            transactionRepository.save(transaction);
        }

//        return "Your transaction from Account Number: %s to Account Number: %s is successful!\n" +
//                "Transfer Debited Amount: %.2f rs\n" +
//                "Now Credited Account Balance is: %.2f rs".formatted(
//                        transactionDto.getAccountNumberFrom(), transactionDto.getAccountNumberTo(),
//                        transactionDto.getAmount(), toAccount.getAmount() + transactionDto.getAmount());

       return "Transaction successful__!!"+toAccount.getAmount();
    }

    @Override
    public List<TransactionDto> findTransaction(Long accountNumber, long days) {

        LocalDate toDate = LocalDate.now();
        LocalDate fromDate = toDate.minusDays(days);
        List<Transaction> transaction = transactionRepository.findAllByAccountNumberToOrAccountNumberFromAndTransactionDateBetween(accountNumber,accountNumber,fromDate,toDate);
        return transaction.stream().filter(Objects::nonNull).map(transaction1 -> {
            TransactionDto transactionDto = new TransactionDto();
            BeanUtils.copyProperties(transaction1, transactionDto);
            return transactionDto;
        }).collect(Collectors.toList());

    }


}