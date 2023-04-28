package com.example.bankingApplication.Controller;

import com.example.bankingApplication.dto.CustomerDetails;
import com.example.bankingApplication.service.IBankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    @Autowired
    private IBankingService bankingService;

    @PostMapping("/create")
    public String createCustomer(@RequestBody CustomerDetails customer){
        return bankingService.addCustomer(customer);
    }

    @GetMapping("/customer/{customerNumber}")
    public CustomerDetails findByCustomerId(@PathVariable Long customerNumber){
        return bankingService.findByCustomerNumber(customerNumber);
    }

    @GetMapping("/customers")
    public List<CustomerDetails> findAllCustomers(){
        return bankingService.findAll();
    }
}
