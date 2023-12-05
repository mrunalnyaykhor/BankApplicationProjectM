package com.bankmanagement.repository;

import com.bankmanagement.entity.Bank;
import com.bankmanagement.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;


@Repository
public interface CustomerRepository extends JpaRepository<Customer, Serializable> {

    boolean existsByAadhaarNumber(String aadhaarNumber);


    List<Customer> findByBankAndAadhaarNumberOrEmail(Bank bank, String customerId, String email);

    boolean existsByEmail(String email);


    boolean existsByPanCardNumber(String panCardNumber);

    List<Customer> findByAadhaarNumberAndFirstNameAndPanCardNumber(String aadhaarNumber, String firstName, String panCardNumber);
}
