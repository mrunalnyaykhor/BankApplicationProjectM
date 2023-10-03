package com.bankmanagement.service.serviceimpl;
import com.bankmanagement.dto.CustomerDto;
import com.bankmanagement.entity.Customer;
import com.bankmanagement.exception.CustomerException;
import com.bankmanagement.repository.CustomerRepository;
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

    @Override
    public CustomerDto saveCustomer(CustomerDto customerDto) {
        Customer customer= new Customer();
        BeanUtils.copyProperties(customerDto,customer);
        customerRepository.save(customer);
        return customerDto;
    }

    @Override
    public List<CustomerDto> getAllCustomer() {
        if(customerRepository.findAll().isEmpty())
            throw new CustomerException("customers Data not present in Database");
        return customerRepository.findAll().stream().filter(Objects::nonNull).map(customer -> {
            CustomerDto customerdto = new CustomerDto();
            BeanUtils.copyProperties(customer, customerdto);
            return customerdto;
        }).collect(Collectors.toList());
    }
    @Override
    public List<CustomerDto> customerFindById(Long customerId) {

        if(customerRepository.findById(customerId).isEmpty())
            throw new CustomerException(" Customer not present");

         return  customerRepository.findById(customerId).stream().filter(Objects::nonNull)
                   .map(customer -> {
                       CustomerDto customerDto = new CustomerDto();
                       BeanUtils.copyProperties(customer,customerDto);
                       return customerDto;
                   }).collect(Collectors.toList());
    }

    @Override
    public String deleteCustomerById(Long customerId) {

        if(customerRepository.findById(customerId).isEmpty()){
            throw new CustomerException(" Customer not present");}
        if(customerRepository.findById(customerId).isPresent()){
         customerRepository.deleteById(customerId);}
        return "deleted successfully";

    }

    @Override
    public CustomerDto updateCustomer(CustomerDto customerDto, Long customerId) {
        Optional<Customer> customerOptional= customerRepository.findById(customerId);

        return customerDto;
    }


}

