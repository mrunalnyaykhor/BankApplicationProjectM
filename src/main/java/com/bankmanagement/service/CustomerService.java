package com.bankmanagement.service;

import com.bankmanagement.dto.CustomerDto;
import com.bankmanagement.dto.TransactionDto;

import java.util.List;

public interface CustomerService {
    CustomerDto saveCustomer(CustomerDto customerDto,Long bankId) ;

    List<CustomerDto> getAllCustomer();

    List<CustomerDto> customerFindById(Long customerId);

    String deleteCustomerById(Long customerId);

    CustomerDto updateCustomer(CustomerDto customerDto,Long customerId);

    String transferMoney(TransactionDto transactionDto);
}
