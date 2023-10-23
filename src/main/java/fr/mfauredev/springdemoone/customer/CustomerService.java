package fr.mfauredev.springdemoone.customer;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService
{
    private final CustomerDao customerDao;

    public  CustomerService(CustomerDao customerDao){
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
}
