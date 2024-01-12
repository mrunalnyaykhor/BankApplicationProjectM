package com.bankmanagement.service.serviceimpl;

import com.bankmanagement.constant.ApplicationConstant;
import com.bankmanagement.dto.TransactionDto;
import com.bankmanagement.entity.Account;
import com.bankmanagement.entity.Transaction;
import com.bankmanagement.entity.Withdrawal;
import com.bankmanagement.exception.AccountException;
import com.bankmanagement.exception.TransactionException;
import com.bankmanagement.repository.AccountRepository;
import com.bankmanagement.repository.TransactionRepository;
import com.bankmanagement.repository.WithdrawalRepository;
import com.bankmanagement.service.TransactionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @Autowired
    private WithdrawalRepository withdrawalRepository;

    @Override
    @Transactional
    public ResponseEntity<String> transferMoney(TransactionDto transactionDto) throws AccountException {

        Account byAccountNumberFrom = accountRepository
                .findByAccountNumber(transactionDto.getAccountNumberFrom());
        Account byAccountNumberTo = accountRepository
                .findByAccountNumber(transactionDto.getAccountNumberTo());

        if (byAccountNumberFrom == null || byAccountNumberTo == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApplicationConstant.ACCOUNT_NOT_FOUND);
        }

        if (byAccountNumberFrom.getAmount() < 5000) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApplicationConstant.BALANCE_IS_MINIMUM);
        } else {
            double remainingAmountOfDebitedAccount = byAccountNumberFrom.getAmount()
                    - transactionDto.getAmount();
            if (remainingAmountOfDebitedAccount >= 5000) {
                double amountFromTransaction = byAccountNumberFrom.getAmount()
                        - transactionDto.getAmount();
                double amountToAccount = byAccountNumberTo.getAmount()
                        + transactionDto.getAmount();
                byAccountNumberFrom.setAmount(amountFromTransaction);
               byAccountNumberTo.setAmount(amountToAccount);

                if (byAccountNumberFrom.isBlocked()) {
                    throw new AccountException(ApplicationConstant.ACCOUNT_IS_BLOCKED);
                }
                byAccountNumberFrom.setAmount(amountFromTransaction);
                if (byAccountNumberTo.getAmount() >= 10000) {
                    byAccountNumberTo.setBlocked(false);
                } else if (byAccountNumberTo.getAmount() <= 5000) {
                    byAccountNumberTo.setBlocked(true);
                }
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApplicationConstant.INSUFFICIENT_BALANCE);
            }
        }
        LocalDate date = LocalDate.now();

        Transaction transaction = new Transaction();
        transactionDto.setTransactionDate(date);
        boolean ifscCode = byAccountNumberTo.getBank().getIfscCode()
                .equals(transactionDto.getIfscCode());
        if (!ifscCode) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApplicationConstant.TO_ACCOUNT_IFSC_CODE_INCORRECT);
        }
        boolean name = byAccountNumberTo.getCustomer().getFirstName()
                .equals(transactionDto.getName());
        if (!name) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApplicationConstant.TO_ACCOUNT_NAME_IS_INCORRECT);
        }
        BeanUtils.copyProperties(transactionDto, transaction);
        accountRepository.save(byAccountNumberTo);
        accountRepository.save(byAccountNumberFrom);
        transactionRepository.save(transaction);

         return ResponseEntity.status(HttpStatus.OK).body(ApplicationConstant.TRANSACTION_SUCCESSFUL);
    }

    @Override
    public List<TransactionDto> findTransaction(Long accountNumber, long days) {
        List<Transaction> byAccountNumberTo = transactionRepository.findByAccountNumberTo(accountNumber);
        List<Transaction> byAccountNumberFrom = transactionRepository.findByAccountNumberFrom(accountNumber);
        if(byAccountNumberTo == null ||byAccountNumberFrom==null)
            throw new TransactionException(ApplicationConstant.ACCOUNT_NOT_FOUND);

        TransactionDto transactionDto = new TransactionDto();
        LocalDate toDate = LocalDate.now();
        LocalDate fromDate = toDate.minusDays(days);

        List<Transaction> transaction = transactionRepository.findAllByAccountNumberToOrAccountNumberFromAndTransactionDateBetween(accountNumber, accountNumber, fromDate, toDate);
        return transaction.stream().filter(Objects::nonNull).map(transaction1 -> {
            BeanUtils.copyProperties(transaction1, transactionDto);
            return transactionDto;
        }).collect(Collectors.toList());
    }

    @Override
    public List<Transaction> getAllTransaction() {
        List<Transaction> alltransaction = transactionRepository.findAll();
        if(alltransaction.isEmpty())
        {
            throw new TransactionException(ApplicationConstant.TRANSACTION_NOT_AVAILABLE);
        }
        return transactionRepository.findAll().stream().filter(Objects::nonNull).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public ResponseEntity<String> withdrawalMoney( Withdrawal withdrawal) {
        Optional<Account> account = Optional.ofNullable(accountRepository.findByAccountNumber(withdrawal.getAccountNumber()));

        if (account.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApplicationConstant.ACCOUNT_NOT_FOUND);
        }
        Account accounts = account.get();

        Double amount= withdrawal.getAmount();
        if (amount <= 500) {
            throw new AccountException(ApplicationConstant.WITHDRAWAL_AMOUNT_MORE_THAN_FIVE_HUNDRED);
        }
        if (amount > accounts.getAmount() - 2000) {
            throw new AccountException(ApplicationConstant.INSUFFICIENT_BALANCE);
        }
        if (amount >= 500 && amount <= 20000) {
            accounts.setAmount(accounts.getAmount() - amount);
            accountRepository.save(accounts);
            withdrawalRepository.save(withdrawal);
        }
        return  ResponseEntity.status(HttpStatus.OK).body(ApplicationConstant.AMOUNT_WITHDRAWAL_SUCCESSFULLY);

    }




}