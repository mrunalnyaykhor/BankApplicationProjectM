package com.bankmanagement.controller;
import com.bankmanagement.constant.UrlConstant;
import com.bankmanagement.dto.CustomerDto;
import com.bankmanagement.entity.Customer;
import com.bankmanagement.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
@RestController
@CrossOrigin(origins = { "http://localhost:4200/" })
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @PostMapping(UrlConstant.SAVE_CUSTOMER)
    public ResponseEntity<ResponseEntity<String>> saveCustomer(@Valid @RequestBody CustomerDto customerDto)
    {
        return ResponseEntity.ok( customerService.saveCustomer(customerDto));

    }
    @GetMapping(UrlConstant.GET_ALL_CUSTOMER)
    public ResponseEntity<List<Customer>> getAllCustomer(){
        return ResponseEntity.ok(customerService.getAllCustomer());
    }
    @GetMapping(UrlConstant.GET_CUSTOMER_BY_ID)
    public ResponseEntity<CustomerDto> getCustomerById(@PathVariable Long customerId){
        return ResponseEntity.ok(customerService.customerFindById(customerId));
    }
    @PutMapping(UrlConstant.UPDATE_CUSTOMER)
    public ResponseEntity<ResponseEntity<String>> updateCustomer(@Valid @RequestBody Customer customer) {

        return ResponseEntity.ok(customerService.updateCustomer(customer));
    }

    @DeleteMapping(UrlConstant.DELETE_CUSTOMER)
    public String deleteCustomerById(@PathVariable Long customerId) {
        return customerService.deleteCustomerById(customerId);
    }

}
