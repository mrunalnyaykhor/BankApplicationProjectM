package com.bankmanagement.service.serviceimpl;

import com.bankmanagement.dto.CustomerDto;
import com.bankmanagement.dto.UserTransactionDto;
import com.bankmanagement.entity.Account;
import com.bankmanagement.entity.Bank;
import com.bankmanagement.entity.Customer;
import com.bankmanagement.entity.UserTransaction;
import com.bankmanagement.exception.BankException;
import com.bankmanagement.exception.CustomerException;
import com.bankmanagement.repository.AccountRepository;
import com.bankmanagement.repository.BankRepository;
import com.bankmanagement.repository.CustomerRepository;
import com.bankmanagement.repository.UserTransactionRepository;
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
    private UserTransactionRepository transactionRepository;

    @Override
    public CustomerDto saveCustomer(CustomerDto customerDto, Long bankId) {
        Customer customer = new Customer();
        Optional<Bank> bank = bankRepository.findById(bankId);
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

        if (customerRepository.findById(customerId).isEmpty())
            throw new CustomerException(" Customer not present");

        return customerRepository.findById(customerId).stream().filter(Objects::nonNull)
                .map(customer -> {
                    CustomerDto customerDto = new CustomerDto();
                    BeanUtils.copyProperties(customer, customerDto);
                    return customerDto;
                }).collect(Collectors.toList());
    }

    @Override
    public String deleteCustomerById(Long customerId) {

        if (customerRepository.findById(customerId).isEmpty()) {
            throw new CustomerException(" Customer not present");
        }
        if (customerRepository.findById(customerId).isPresent()) {
            customerRepository.deleteById(customerId);
        }
        return "deleted successfully";

    }

    @Override
    public CustomerDto updateCustomer(CustomerDto customerDto, Long customerId) {
        Optional<Customer> customerOptional = customerRepository.findById(customerId);

        return customerDto;
    }

    @Override
    public String transferMoney(UserTransactionDto transactionDto) {
        UserTransaction transaction = new UserTransaction();
        Account fromAccount = accountRepository.findByCustomerAccountNumber(transactionDto.getAccountNumberFrom());
        Account toAccount = accountRepository.findByCustomerAccountNumber(transactionDto.getAccountNumberTo());

        if (Objects.isNull(fromAccount)) {
            throw new BankException("Account Not Present"
                    + transactionDto.getAccountNumberFrom());
        }
        if (Objects.isNull(toAccount)) {
            throw new BankException("Account Not Present"
                    + transactionDto.getAccountNumberFrom());
        }


        if (!transactionDto.getIfscCode().equals(toAccount.getIfscCode())) {
            return transactionDto.getIfscCode();
        }
        if (transactionDto.getAmount() > fromAccount.getAmount()) {
            return "low Balance";
        }
        double fromAccountDebited = fromAccount.getAmount() - transactionDto.getAmount();
        double toAccountCredited = toAccount.getAmount() + transactionDto.getAmount();

        fromAccount.setAmount(fromAccountDebited);
        toAccount.setAmount(toAccountCredited);

        BeanUtils.copyProperties(transactionDto, transaction);

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);
        transactionRepository.save(transaction);

        return null;
    }


}

