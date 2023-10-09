package com.bankmanagement.repository;

import com.bankmanagement.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Serializable> {

    // Account findByAadhaarNumberAndPanCardNumber(String aadhaarNumber, String panCard);

    Account findByAccountNumber(Long accountNumberTo);
    Optional<Account> findByAmount(Double amount);
}
