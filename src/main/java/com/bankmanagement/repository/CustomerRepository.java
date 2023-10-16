package com.bankmanagement.repository;

import com.bankmanagement.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Serializable> {
    boolean existsByAadhaarNumber(String aadhaarNumber);

}
