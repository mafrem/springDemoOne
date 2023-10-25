package fr.mfauredev.springdemoone.customer;

import fr.mfauredev.springdemoone.exceptions.DuplicateResourceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService
{
    private final CustomerDao customerDao;

    public  CustomerService(@Qualifier("jpa") CustomerDao customerDao){
        this.customerDao = customerDao;
    }

    public List<Customer> getAllCustomers(){
        return  customerDao.selectAllCustomers();
    }

    public Customer getCustomerById(Integer id){
        return  customerDao
                .selectCustomerById(id)
                .orElseThrow(
                        () -> new IllegalArgumentException("Bouh [%s]".formatted(id))
                );
    }

    public void addCustomer(CustomerRegistrationRequest c){
        if (customerDao.existsPersonWithEmail(c.email())){
            throw new DuplicateResourceException("Email (this one :  [%s] ) guy already there sry".formatted(c.email()));
        }
        Customer newOne = new Customer(c.name(), c.email(), c.age());
        customerDao.insertCustomer(newOne);
    }
}
