package com.bankmanagement.repository;

import com.bankmanagement.entity.Withdrawal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
@Repository
public interface WithdrawalRepository extends JpaRepository<Withdrawal, Serializable> {
}
