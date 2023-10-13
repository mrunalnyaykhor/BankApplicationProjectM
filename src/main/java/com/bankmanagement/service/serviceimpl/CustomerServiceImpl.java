package com.bankmanagement.service.serviceimpl;

import com.bankmanagement.dto.CustomerDto;
import com.bankmanagement.entity.Bank;
import com.bankmanagement.entity.Customer;
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
       // List<Customer>customerList= customerRepository.findAllByContactNumberOrPanCardNumberOrAadhaarNumberOrEmail(customerDto.getContactNumber(),customerDto.getPanCardNumber(),customerDto.getEmail());
//        if(customerList.isEmpty()){
//            throw new CustomerException("This customer already exist");
//        }

        Optional<Bank> bank = bankRepository.findById(bankId);
        Customer customer = new Customer();

        BeanUtils.copyProperties(customerDto, customer);
        customer.setBank(bank.get());
        customerDto.setBankId(bank.get().getBankId());

        customerRepository.save(customer);

        return customerDto;
    }

    @Override
    public List<CustomerDto> getAllCustomer() {
        if (customerRepository.findAll().isEmpty())
            throw new CustomerException("customers Data not present in Database");
        return customerRepository.findAll().stream().filter(Objects::nonNull).map(customer -> {
            CustomerDto customerdto = new CustomerDto();
            BeanUtils.copyProperties(customer, customerdto);
            return customerdto;
        }).collect(Collectors.toList());
    }

    @Override
    public List<CustomerDto> customerFindById(Long customerId) {

        Optional<Customer> customerOptional = customerRepository.findById(customerId);

        if (customerOptional.isEmpty()) {
            throw new CustomerException("Customer not present");
        }

        return customerOptional.stream()
                .map(customer -> {
                    CustomerDto customerDto = new CustomerDto();
                    BeanUtils.copyProperties(customer, customerDto);
                    return customerDto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public String deleteCustomerById(Long customerId) {

        Optional<Customer> customerOptional = customerRepository.findById(customerId);

        if (customerOptional.isEmpty()) {
            throw new CustomerException("Customer not present");
        }

        customerOptional.ifPresent(customer -> {
            customerRepository.deleteById(customerId);
        });

        return "Customer Id :%d deleted successfully....!!".formatted(customerId);
    }

    @Override
    public CustomerDto updateCustomer(CustomerDto customerDto, Long customerId) {
        Optional<Customer> customerOptional = customerRepository.findById(customerId);
        if (customerOptional.isEmpty()) {
            throw new CustomerException("Customer Does not exist____!!!");
        }
        if (customerOptional.isPresent()) {
            customerOptional.stream().map(customer -> {
                BeanUtils.copyProperties(customerDto, customer);
                return customerRepository.save(customer);
            });
        }
        return customerDto;
    }
}

