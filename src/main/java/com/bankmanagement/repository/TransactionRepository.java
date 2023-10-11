package com.bankmanagement.repository;

import com.bankmanagement.entity.Account;
import com.bankmanagement.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Serializable> {
    Optional<Account> findByAccountNumberTo(Long accountNumberTo);

   List<Transaction> findAllByAccountNumberToOrAccountNumberFromAndTransactionDateBetween(Long accountNumberFrom, Long accountNumberTo, LocalDate fromDate, LocalDate toDate);


    //List<Transaction>  findByAccountNumberAndDate(Long accountNumber, Date date);
}
