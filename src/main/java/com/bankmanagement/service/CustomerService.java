package com.bankmanagement.service;

import com.bankmanagement.dto.CustomerDto;

import java.util.List;

public interface CustomerService {
    String saveCustomer(CustomerDto customerDto) ;

    List<CustomerDto> getAllCustomer();

    CustomerDto customerFindById(Long customerId);

    String deleteCustomerById(Long customerId);

    CustomerDto updateCustomer(CustomerDto customerDto);


}
