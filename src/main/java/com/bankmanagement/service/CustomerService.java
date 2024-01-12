package com.bankmanagement.service;

import com.bankmanagement.dto.CustomerDto;
import com.bankmanagement.entity.Customer;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CustomerService {
    ResponseEntity<String> saveCustomer(CustomerDto customerDto) ;

    List<Customer> getAllCustomer();

    CustomerDto customerFindById(Long customerId);

    String deleteCustomerById(Long customerId);

    ResponseEntity<String> updateCustomer(Customer customer);


}
