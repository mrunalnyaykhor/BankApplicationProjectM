package com.bankmanagement.repository;

import com.bankmanagement.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Serializable> {

    boolean existsByAadhaarNumber(String aadhaarNumber);

    void findByContactNumber(Long contactNumber);
}
