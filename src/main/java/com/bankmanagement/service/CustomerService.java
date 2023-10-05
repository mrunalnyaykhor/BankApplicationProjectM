package com.bankmanagement.service;

import com.bankmanagement.dto.CustomerDto;
import com.bankmanagement.dto.UserTransactionDto;
import com.bankmanagement.entity.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    CustomerDto saveCustomer(CustomerDto customerDto,Long bankId) ;

    List<CustomerDto> getAllCustomer();

    List<CustomerDto> customerFindById(Long customerId);

    String deleteCustomerById(Long customerId);

    CustomerDto updateCustomer(CustomerDto customerDto,Long customerId);

    String transferMoney(UserTransactionDto transactionDto);
}
