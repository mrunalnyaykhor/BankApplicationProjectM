package com.bankmanagement.service.serviceimpl;

import com.bankmanagement.constant.ApplicationConstant;
import com.bankmanagement.dto.TransactionDto;
import com.bankmanagement.entity.Account;
import com.bankmanagement.entity.Transaction;
import com.bankmanagement.exception.AccountException;
import com.bankmanagement.exception.TransactionException;
import com.bankmanagement.repository.AccountRepository;
import com.bankmanagement.repository.TransactionRepository;
import com.bankmanagement.service.TransactionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
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

        Account byAccountNumberFrom = accountRepository
                .findByAccountNumber(transactionDto.getAccountNumberFrom());
        Account byAccountNumberTo = accountRepository
                .findByAccountNumber(transactionDto.getAccountNumberTo());

        if (byAccountNumberFrom == null || byAccountNumberTo == null) {
            throw new TransactionException(ApplicationConstant.ACCOUNT_NOT_FOUND);
        }

        if (byAccountNumberFrom.getAmount() < 5000) {
            throw new TransactionException(ApplicationConstant.BALANCE_IS_MINIMUM);
        } else {
            double remainingAmountOfDebitedAccount = byAccountNumberFrom.getAmount()
                    - transactionDto.getAmount();
            if (remainingAmountOfDebitedAccount >= 5000) {
                double amountFromTransaction = byAccountNumberFrom.getAmount()
                        - transactionDto.getAmount();
                double amountToAccount = byAccountNumberTo.getAmount()
                        + transactionDto.getAmount();
                byAccountNumberFrom.setAmount(amountFromTransaction);

                if (byAccountNumberFrom.isBlocked()) {
                    throw new AccountException(ApplicationConstant.ACCOUNT_IS_BLOCKED);
                }
                byAccountNumberFrom.setAmount(amountToAccount);
                if (byAccountNumberTo.getAmount() >= 10000) {
                    byAccountNumberTo.setBlocked(false);
                } else if (byAccountNumberTo.getAmount() <= 5000) {
                    byAccountNumberTo.setBlocked(true);
                }
            } else {
                throw new TransactionException(ApplicationConstant.INSUFFICIENT_BALANCE);
            }
        }
        LocalDate date = LocalDate.now();

        Transaction transaction = new Transaction();
        transactionDto.setTransactionDate(date);
        boolean ifscCode = byAccountNumberTo.getBank().getIfscCode()
                .equals(transactionDto.getIfscCode());
        if (!ifscCode) {
            throw new TransactionException(ApplicationConstant.TO_ACCOUNT_IFSC_CODE_INCORRECT);
        }
        boolean name = byAccountNumberTo.getCustomer().getFirstName()
                .equals(transactionDto.getName());
        if (!name) {
            throw new TransactionException(ApplicationConstant.TO_ACCOUNT_NAME_IS_INCORRECT);
        }
        BeanUtils.copyProperties(transactionDto, transaction);
        accountRepository.save(byAccountNumberTo);
        accountRepository.save(byAccountNumberFrom);
        transactionRepository.save(transaction);

        return ApplicationConstant.TRANSACTION_SUCCESSFUL;
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