package fr.mfauredev.springdemoone.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CustomerController
{
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("api/v1/customers")
    public List<Customer> getCustomers(){
        return customerService.getAllCustomers();
    }

    @GetMapping("api/v1/customers/{customerId}")
    public Customer getCustomer(@PathVariable("customerId") Integer id){
        return customerService.getCustomerById(id);
    }

    @GetMapping("/error")
    public String getError(){
        return "lol";

    }
}
