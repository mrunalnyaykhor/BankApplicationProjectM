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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<String> saveCustomer(CustomerDto customerDto) {

        if (customerRepository.existsByPanCardNumber(customerDto.getPanCardNumber())) {
            throw new BankException(ApplicationConstant.PAN_CARD_NUMBER_ALREADY_EXIST);

        }
        Bank bank = bankRepository.findById(customerDto.getBankId()).orElseThrow(() ->
                new BankException(ApplicationConstant.BANK_NOT_AVAILABLE));

        long contactNumber = customerDto.getContactNumber();
        String aadhaarNumber = customerDto.getAadhaarNumber();
        String panCardNumber = customerDto.getPanCardNumber();
        int age = customerDto.getAge();
        if (age<=10 || age >=100) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApplicationConstant.NOT_VALID_AGE);
        }
        if(panCardNumber.length() !=10){
           throw new CustomerException(ApplicationConstant.PAN_CARD_NUMBER_SHOULD_BE_VALID);
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
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApplicationConstant.AADHAAR_NUMBER_ALREADY_EXIST);
                } else if (customerRepository.existsByEmail(customerDto.getEmail())) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApplicationConstant.EMAIL_ALREADY_EXIST);
                }
                Customer customer = new Customer();
                BeanUtils.copyProperties(customerDto, customer);
                customer.setBank(bank);
                log.info(ApplicationConstant.CUSTOMER_SAVE);
                customerRepository.save(customer);

                return ResponseEntity.status(HttpStatus.OK).body(ApplicationConstant.CUSTOMER_SAVE);
            }else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApplicationConstant.CUSTOMER_ALREADY_PRESENT);
            }
        }else {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApplicationConstant.CONTACT_NUMBER_INVALID);
        }
    }

    public List<Customer> getAllCustomer() {

        customerRepository.findAll().stream().findAny().orElseThrow(() ->
                new CustomerException(ApplicationConstant.CUSTOMER_NOT_PRESENT));
        return customerRepository.findAll().stream().filter(Objects::nonNull).
        collect(Collectors.toList());

    }

    @Override
    public CustomerDto customerFindById(Long customerId) {
        Optional<Customer>customer= Optional.ofNullable(customerRepository.findById(customerId).orElseThrow(() ->
                new CustomerException(ApplicationConstant.CUSTOMER_NOT_PRESENT)));
        Customer customer1 = customer.get();
        CustomerDto customerDto =new CustomerDto();
       BeanUtils.copyProperties(customer1, customerDto);
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
    public ResponseEntity<String> updateCustomer(Customer customer) {
        customerRepository.findById(customer.getCustomerId()).orElseThrow(
                () -> new CustomerException(ApplicationConstant.CUSTOMER_NOT_PRESENT));
        Bank bank = bankRepository.findById(customer.getBankId()).orElseThrow(
                () -> new BankException(ApplicationConstant.BANK_NOT_AVAILABLE));

        List<Customer> customers = customerRepository.findByAadhaarNumberAndFirstNameAndPanCardNumber(customer.getAadhaarNumber(), customer.getFirstName(), customer.getPanCardNumber());
        if (customers.size() > 1) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApplicationConstant.CUSTOMER_ALREADY_PRESENT);
        }
        customer.setBank(bank);
        customerRepository.save(customer);
        log.info(ApplicationConstant.UPDATE_CUSTOMER_SUCCESSFULLY);

        return ResponseEntity.status(HttpStatus.OK).body(ApplicationConstant.UPDATE_CUSTOMER_SUCCESSFULLY);
    }

}
