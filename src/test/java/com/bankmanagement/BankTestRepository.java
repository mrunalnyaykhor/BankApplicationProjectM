package com.bankmanagement;

import com.bankmanagement.entity.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankTestRepository extends JpaRepository<Bank,Long> {
}
