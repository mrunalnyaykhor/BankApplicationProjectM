package com.bankmanagement.repository;

import com.bankmanagement.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Serializable> {
     Long findAmountByAccountId(Long accountID);

     Account findByAadhaarNumberAndPanCardNumber(String aadhaarNumber, String panCard);


     Account findByCustomerAccountNumber(Long toAccountNumber);
}
