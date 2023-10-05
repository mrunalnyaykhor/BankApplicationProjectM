package com.bankmanagement.repository;

import com.bankmanagement.entity.Account;
import com.bankmanagement.entity.UserTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.Optional;

@Repository
public interface UserTransactionRepository extends JpaRepository<UserTransaction, Serializable> {
    Optional<Account> findByAccountNumberTo(Long accountNumberTo);
}
