package com.bankmanagement.repository;

import com.bankmanagement.entity.Account;
import com.bankmanagement.entity.Bank;
import com.bankmanagement.entity.Customer;
import com.bankmanagement.enump.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Serializable> {

    Account findByAccountNumber(Long accountNumberTo);
    List<Account> findByCustomerAndBankAndAccountType(Customer customer, Bank bank, AccountType accountType);

    boolean existsByAccountNumber(long accountNumber);
}
