package com.bankmanagement.service.serviceimpl;

import com.bankmanagement.constant.ApplicationConstant;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
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
            log.info(ApplicationConstant.AADHAAR_NUMBER_ALREADY_EXIST);
            throw new CustomerException(ApplicationConstant.AADHAAR_NUMBER_ALREADY_EXIST);
        }
        long l = customerDto.getContactNumber();
        String s = Long.toString(l);
        Optional<Bank> bank = bankRepository.findById(bankId);
        if (s.length() == 10 && (s.startsWith("9") || s.startsWith("8") || s.startsWith("7") || s.startsWith("6"))) {
            log.info("contact Number is proper");
            Customer customer = new Customer();
            BeanUtils.copyProperties(customerDto, customer);

            customer.setBank(customer.getBank());
            // customerDto.setBankId(bank.get().getBankId());
            log.info(ApplicationConstant.CUSTOMER_SAVE);
            Customer save = customerRepository.save(customer);

            //customerDto.setCustomerId(save.getCustomerId());
        } else {
            throw new CustomerException(ApplicationConstant.CONTACT_NUMBER_NOT_PROPER);
        }


        return customerDto;
    }

    public List<CustomerDto> getAllCustomer() {
        if (bankRepository.findAll().isEmpty())
            throw new BankException(ApplicationConstant.BANK_NOT_AVAILABLE);

        if (customerRepository.findAll().isEmpty())
            throw new CustomerException(ApplicationConstant.CUSTOMER_NOT_PRESENT);

        List<CustomerDto> collect = customerRepository.findAll().stream().filter(Objects::nonNull).map(customer -> {
            CustomerDto dto = new CustomerDto();
            Bank bank = new Bank();
            dto.setBankId(bank.getBankId());
            BeanUtils.copyProperties(customer, dto);
            return dto;

        }).collect(Collectors.toList());

        return collect;
    }

    @Override
    public CustomerDto customerFindById(Long customerId) {
        Optional<Customer> customerOptional = customerRepository.findById(customerId);
        if (customerOptional.isEmpty()) {
            log.info(ApplicationConstant.CUSTOMER_NOT_PRESENT);
            throw new CustomerException(ApplicationConstant.CUSTOMER_NOT_PRESENT);
        }
        Customer customer = customerOptional.get();

            CustomerDto customerDto = CustomerDto.builder().bankId(customerOptional.get().getBank().getBankId()).build();
            customerDto.setBankId(customerOptional.get().getBank().getBankId());
            BeanUtils.copyProperties(customer, customerDto);
            log.info("customer get successfully");
            return customerDto;

    }

    @Override
    public String deleteCustomerById(Long customerId) {
        Optional<Customer> customerOptional = customerRepository.findById(customerId);
        if (customerOptional.isEmpty()) {
            log.info(ApplicationConstant.CUSTOMER_NOT_PRESENT);
            throw new CustomerException(ApplicationConstant.CUSTOMER_NOT_PRESENT);
        }
        customerOptional.ifPresent(customer -> {
            customerRepository.deleteById(customerId);
        });
        log.info(ApplicationConstant.CUSTOMER_DELETE);
        return ApplicationConstant.CUSTOMER_DELETE.formatted(customerId);
    }

    @Override
    public CustomerDto updateCustomer(CustomerDto customerDto, Long customerId) {
        Optional<Customer> customerOptional = customerRepository.findById(customerId);
        if (customerOptional.isEmpty()){
            throw new CustomerException(ApplicationConstant.CUSTOMER_NOT_PRESENT);
        }
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDto, customer);
        customerRepository.save(customer);
        log.info(ApplicationConstant.UPDATE_CUSTOMER_SUCCESSFULLY);
        return customerDto;
    }
}

