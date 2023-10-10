package com.bankmanagement.controller;
import com.bankmanagement.dto.CustomerDto;
import com.bankmanagement.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
@RestController
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @PostMapping("/saveCustomer/{bankId}")
    public ResponseEntity<CustomerDto> saveCustomer(@Valid @RequestBody CustomerDto customerDto, @PathVariable Long bankId)
    {
        return ResponseEntity.ok( customerService.saveCustomer(customerDto,bankId));

    }
    @GetMapping("/getAllCustomer")
    public ResponseEntity<List<CustomerDto>> getAllCustomer(){
        return ResponseEntity.ok(customerService.getAllCustomer());
    }
    @GetMapping("/getCustomerById/{customerId}")
    public ResponseEntity<List <CustomerDto>> getCustomerById(@PathVariable Long customerId){
        return ResponseEntity.ok(customerService.customerFindById(customerId));
    }
    @PutMapping("/customer/{customerId}")
    public ResponseEntity<CustomerDto> updateCustomerDto(@RequestBody CustomerDto customerDto, @PathVariable("customerId") Long customerId) {

        return ResponseEntity.ok(customerService.updateCustomer(customerDto, customerId));
    }
    @DeleteMapping("/deleteCustomerById/{customerId}")
    public String deleteCustomerById(@PathVariable Long customerId)
    {
        return customerService.deleteCustomerById(customerId);
    }

}
