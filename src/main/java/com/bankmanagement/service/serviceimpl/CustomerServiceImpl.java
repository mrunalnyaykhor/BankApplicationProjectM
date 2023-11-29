package com.bankmanagement.service.serviceimpl;

import com.bankmanagement.constant.ApplicationConstant;
import com.bankmanagement.dto.CustomerDto;
import com.bankmanagement.entity.Bank;
import com.bankmanagement.entity.Customer;
import com.bankmanagement.exception.BankException;
import com.bankmanagement.exception.CustomerException;
import com.bankmanagement.repository.BankRepository;
import com.bankmanagement.repository.CustomerRepository;
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
    private BankRepository bankRepository;

    @Override
    public String saveCustomer(CustomerDto customerDto) {
        Optional<Customer> customerId = customerRepository.findById(customerDto.getCustomerId());
        if(customerId.isPresent()){
            throw new CustomerException(ApplicationConstant.CUSTOMER_ID_ALREADY_PRESENT);
        }
        Bank bank = bankRepository.findById(customerDto.getBankId()).orElseThrow(() ->
                new BankException(ApplicationConstant.BANK_NOT_AVAILABLE));

        long contactNumber = customerDto.getContactNumber();
        String aadhaarNumber = customerDto.getAadhaarNumber();
        String panCardNumber = customerDto.getPanCardNumber();
        int age = customerDto.getAge();
        if (age<=10 || age >=100) {
            throw new CustomerException(ApplicationConstant.NOT_VALID_AGE);
        }
        if(panCardNumber.length() !=10){
           throw new CustomerException(ApplicationConstant.PANCARD_NUMBER_SHOULD_BE_VALID);
       }
        if (aadhaarNumber.length() != 12) {
            throw new CustomerException(ApplicationConstant.AADHAAR_NUMBER_SHOULD_BE_VALID);
        }
        String contactLength = Long.toString(contactNumber);
        if (contactLength.length() == 10 && (contactLength.startsWith("9")
                || contactLength.startsWith("8")
                || contactLength.startsWith("7") || contactLength.startsWith("6"))) {


            log.info(ApplicationConstant.CONTACT_IS_CORRECT);
            List<Customer> byBankAndCustomer = customerRepository.findByBankAndAadhaarNumberOrEmail(bank, customerDto.getAadhaarNumber(), customerDto.getEmail());
            if (byBankAndCustomer != null )
            {
                if (customerRepository.existsByAadhaarNumber(customerDto.getAadhaarNumber())) {
                    throw new CustomerException(ApplicationConstant.AADHAAR_NUMBER_ALREADY_EXIST);
                } else if (customerRepository.existsByEmail(customerDto.getEmail())) {
                    throw new CustomerException(ApplicationConstant.EMAIL_ALREADY_EXIST);
                }
                Customer customer = new Customer();
                BeanUtils.copyProperties(customerDto, customer);
                customer.setBank(bank);
                log.info(ApplicationConstant.CUSTOMER_SAVE);
                customerRepository.save(customer);
                return ApplicationConstant.CUSTOMER_SAVE;
            }else{
                throw new CustomerException(ApplicationConstant.CUSTOMER_ALREADY_PRESENT);
            }
        }else {

            throw new CustomerException(ApplicationConstant.CONTACT_NUMBER_INVALID);
        }
    }

    public List<CustomerDto> getAllCustomer() {

        customerRepository.findAll().stream().findAny().orElseThrow(() ->
                new CustomerException(ApplicationConstant.CUSTOMER_NOT_PRESENT));
        return customerRepository.findAll().stream().filter(Objects::nonNull).map(customer -> {
            CustomerDto customerDto = new CustomerDto();
            BeanUtils.copyProperties(customer, customerDto);
            return customerDto;

        }).collect(Collectors.toList());

    }

    @Override
    public CustomerDto customerFindById(Long customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() ->
                new CustomerException(ApplicationConstant.CUSTOMER_NOT_PRESENT));
      CustomerDto customerDto =new CustomerDto();
       BeanUtils.copyProperties(customer, customerDto);
        return customerDto;

    }

    @Override
    public String deleteCustomerById(Long customerId) {
        customerRepository.findById(customerId).orElseThrow(() ->
                new CustomerException(ApplicationConstant.CUSTOMER_NOT_PRESENT));
        customerRepository.deleteById(customerId);
        log.info(ApplicationConstant.CUSTOMER_DELETE);
        return String.format(ApplicationConstant.CUSTOMER_DELETE).formatted(customerId);
    }

    @Override
    public CustomerDto updateCustomer(CustomerDto customerDto) {
        customerRepository.findById(customerDto.getCustomerId()).orElseThrow(
                () -> new CustomerException(ApplicationConstant.CUSTOMER_NOT_PRESENT));
       Bank bank  = bankRepository.findById(customerDto.getBankId()).orElseThrow(
                ()->new BankException(ApplicationConstant.BANK_NOT_AVAILABLE));
        Customer customer = new Customer();

        BeanUtils.copyProperties(customerDto, customer);


        customer.setBank(bank);

        customerRepository.save(customer);

        log.info(ApplicationConstant.UPDATE_CUSTOMER_SUCCESSFULLY);
        return customerDto;
    }
}

