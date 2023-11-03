package com.bankmanagement.repository;

import com.bankmanagement.entity.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository
public interface BankRepository extends JpaRepository<Bank, Serializable> {

    boolean existsByIfscCode(String ifscCode);

    Bank findByBankId(Long bankId);


}
