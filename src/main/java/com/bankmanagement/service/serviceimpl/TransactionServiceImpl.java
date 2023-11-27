package com.bankmanagement.service.serviceimpl;
import com.bankmanagement.constant.ApplicationConstant;
import com.bankmanagement.dto.TransactionDto;
import com.bankmanagement.entity.Account;
import com.bankmanagement.entity.Bank;
import com.bankmanagement.entity.Transaction;
import com.bankmanagement.exception.AccountException;
import com.bankmanagement.exception.BankException;
import com.bankmanagement.exception.TransactionException;
import com.bankmanagement.repository.AccountRepository;
import com.bankmanagement.repository.TransactionRepository;
import com.bankmanagement.service.TransactionService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class TransactionServiceImpl implements TransactionService {


    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Override
    @Transactional
    public String transferMoney(TransactionDto transactionDto) throws AccountException {
       // Transaction transaction3 = transactionRepository.findBy(transactionDto.getAccountNumberFrom());

        Account byAccountNumberFrom = accountRepository.findByAccountNumber(transactionDto.getAccountNumberFrom());
        Account byAccountNumberTo = accountRepository.findByAccountNumber(transactionDto.getAccountNumberTo());

        if (byAccountNumberFrom==null || byAccountNumberTo==null) {
            throw new TransactionException(ApplicationConstant.ACCOUNT_NOT_FOUND);
        }

            Account toAccount = byAccountNumberFrom;
            Account fromAccount = byAccountNumberTo;


            if (toAccount.getAmount() < 5000) {
                throw new TransactionException(ApplicationConstant.BALANCE_IS_MINIMUM);
            } else {
                double remainingAmountOfDebitedAccount = fromAccount.getAmount() - transactionDto.getAmount();
                if (remainingAmountOfDebitedAccount >= 5000) {
                    double amountFromTransaction = fromAccount.getAmount() - transactionDto.getAmount();
                    double amountToAccount = toAccount.getAmount() + transactionDto.getAmount();
                    fromAccount.setAmount(amountFromTransaction);

                    if (fromAccount.isBlocked()) {
                        throw new AccountException(ApplicationConstant.ACCOUNT_IS_BLOCKED);
                    }
                    toAccount.setAmount(amountToAccount);
                    if (toAccount.getAmount() >= 10000) {
                        toAccount.setBlocked(false);
                    } else if (toAccount.getAmount() <= 5000) {
                        toAccount.setBlocked(true);
                    }
                } else {
                    throw new TransactionException(ApplicationConstant.INSUFFICIENT_BALANCE);
                }
            }
            LocalDate date = LocalDate.now();

            Transaction transaction = new Transaction();
            transactionDto.setTransactionDate(date);
            boolean ifscCode = toAccount.getBank().getIfscCode().equals(transactionDto.getIfscCode());
            if (!ifscCode) {
                throw new TransactionException("To account ifsc code is not correct");
            }
            boolean name = toAccount.getCustomer().getFirstName().equals(transactionDto.getName());
            if (!name) {
                throw new TransactionException("To account Name is not correct");
            }
            BeanUtils.copyProperties(transactionDto, transaction);
            accountRepository.save(fromAccount);
            accountRepository.save(toAccount);
            transactionRepository.save(transaction);

        return "Transaction successful__!!";
    }

    @Override
    public List<TransactionDto> findTransaction(Long accountNumber, long days) {
        TransactionDto transactionDto = new TransactionDto();
        LocalDate toDate = LocalDate.now();
        LocalDate fromDate = toDate.minusDays(days);
        List<Transaction> transaction = transactionRepository.findAllByAccountNumberToOrAccountNumberFromAndTransactionDateBetween(accountNumber, accountNumber, fromDate, toDate);
        return transaction.stream().filter(Objects::nonNull).map(transaction1 -> {

            BeanUtils.copyProperties(transaction1, transactionDto);
            return transactionDto;
        }).collect(Collectors.toList());
    }
}