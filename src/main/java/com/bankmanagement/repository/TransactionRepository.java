package com.bankmanagement.repository;
import com.bankmanagement.entity.Account;
import com.bankmanagement.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Serializable> {

  List<Transaction> findAllByAccountNumberToOrAccountNumberFromAndTransactionDateBetween
           (Long accountNumberFrom, Long accountNumberTo, LocalDate fromDate, LocalDate toDate);
    List<Transaction> findByAccountNumberTo(Long accountNumber);
    List<Transaction> findByAccountNumberFrom(Long accountNumber);

//     List<Transaction> findByAccountNumberToOrAccountNumberFrom(accountNumber, accountNumber);

}
