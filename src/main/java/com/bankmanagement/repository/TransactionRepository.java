package com.bankmanagement.repository;
import com.bankmanagement.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Serializable> {

   List<Transaction> findAllByAccountNumberToOrAccountNumberFromAndTransactionDateBetween
           (Long accountNumberFrom, Long accountNumberTo, LocalDate fromDate, LocalDate toDate);



}
