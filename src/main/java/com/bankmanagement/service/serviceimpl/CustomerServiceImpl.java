package com.bankmanagement.service.serviceimpl;

import com.bankmanagement.dto.CustomerDto;
import com.bankmanagement.entity.Bank;
import com.bankmanagement.entity.Customer;
import com.bankmanagement.exception.BankException;
import com.bankmanagement.exception.CustomerException;
import com.bankmanagement.repository.AccountRepository;
import com.bankmanagement.repository.BankRepository;
import com.bankmanagement.repository.CustomerRepository;
import com.bankmanagement.repository.TransactionRepository;
import com.bankmanagement.service.CustomerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private BankRepository bankRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public CustomerDto saveCustomer(CustomerDto customerDto, Long bankId) {
        if (customerRepository.existsByAadhaarNumber(customerDto.getAadhaarNumber())) {
            throw new CustomerException("A Customer of AadhaarNumber Number %s already exists.".formatted(customerDto.getAadhaarNumber()));
        }
        long l = customerDto.getContactNumber();
        String s = Long.toString(l);
        Optional<Bank> bank = bankRepository.findById(bankId);
        if (s.length() == 10 && (s.startsWith("9") || s.startsWith("8") || s.startsWith("7") || s.startsWith("6"))) {

            Customer customer = new Customer();
            BeanUtils.copyProperties(customerDto, customer);

            customer.setBank(customer.getBank());
            // customerDto.setBankId(bank.get().getBankId());
            Customer save = customerRepository.save(customer);
            customerDto.setCustomerId(save.getCustomerId());
        } else {
            throw new CustomerException("Invalid Contact Number");
        }


        return customerDto;
    }

    public List<CustomerDto> getAllCustomer() {
        if (customerRepository.findAll().isEmpty())
            throw new CustomerException("customers Data not present in Database");
        if (bankRepository.findAll().isEmpty())
            throw new BankException("Bank Data not present in Database");

        List<CustomerDto> collect = customerRepository.findAll().stream().filter(Objects::nonNull).map(customer -> {
            CustomerDto dto = new CustomerDto();
            Bank bank = new Bank();
            dto.setBankId(bank.getBankId());
            //.builder().bankId(customer.getBank().getBankId()).build();

            BeanUtils.copyProperties(customer, dto);
            return dto;

        }).collect(Collectors.toList());

        return collect;
    }

    @Override
    public CustomerDto customerFindById(Long customerId) {
        Optional<Customer> customerOptional = customerRepository.findById(customerId);
        if (customerOptional.isEmpty()) {
            throw new CustomerException("Customer not present");
        }
        Customer customer = customerOptional.get();

            CustomerDto customerDto = CustomerDto.builder().bankId(customerOptional.get().getBank().getBankId()).build();
            customerDto.setBankId(customerOptional.get().getBank().getBankId());
            BeanUtils.copyProperties(customer, customerDto);
            return customerDto;

    }

    @Override
    public String deleteCustomerById(Long customerId) {
        Optional<Customer> customerOptional = customerRepository.findById(customerId);
        if (customerOptional.isEmpty())
            throw new CustomerException("Customer not present");
        customerOptional.ifPresent(customer -> {
            customerRepository.deleteById(customerId);
        });
        return "Customer Id :%d deleted successfully....!!".formatted(customerId);
    }

    @Override
    public CustomerDto updateCustomer(CustomerDto customerDto, Long customerId) {
        Optional<Customer> customerOptional = customerRepository.findById(customerId);
        if (customerOptional.isEmpty()){
            throw new CustomerException("Customer Does not exist____!!!");
        }
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDto, customer);
        customerRepository.save(customer);
        return customerDto;
    }
}

