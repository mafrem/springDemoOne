package fr.mfauredev.springdemoone.customer;

import fr.mfauredev.springdemoone.exceptions.DuplicateResourceException;
import fr.mfauredev.springdemoone.exceptions.MissingResourceException;
import fr.mfauredev.springdemoone.exceptions.RequestValidationException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService
{
    private final CustomerDao customerDao;

    public  CustomerService(@Qualifier("jdbc") CustomerDao customerDao){
        this.customerDao = customerDao;
    }

    public List<Customer> getAllCustomers(){
        return  customerDao.selectAllCustomers();
    }

    public Customer getCustomerById(Integer id){
        return  customerDao
                .selectCustomerById(id)
                .orElseThrow(
                        () -> new MissingResourceException("Bouh [%s]".formatted(id))
                );
    }

    public void addCustomer(CustomerRegistrationRequest c){
        if (customerDao.existsPersonWithEmail(c.email())){
            throw new DuplicateResourceException("Email (this one :  [%s] ) guy already there sry".formatted(c.email()));
        }
        Customer newOne = new Customer(c.name(), c.email(), c.age());
        customerDao.insertCustomer(newOne);
    }

    public void deleteCustomerById(Integer id){
        if(customerDao.existsPersonWithId(id)){
            customerDao.deleteCustomerById(id);
        }
    }

    public void updateCustomer(Integer customerId, CustomerUpdateRequest updateRequest){
        Customer customer = getCustomerById(customerId);

        boolean changes = false;

        if(updateRequest.name()!=null && !updateRequest.name().equals(customer.getName())){
            customer.setName(updateRequest.name());
            changes = true;
        }
        if(updateRequest.email()!=null && !updateRequest.email().equals(customer.getEmail())){
            if(customerDao.existsPersonWithEmail(updateRequest.email())){
                throw new DuplicateResourceException(
                        "Email (this one :  [%s] ) guy already there sry".formatted(updateRequest.email()));
            }
            customer.setEmail(updateRequest.email());
            changes = true;
        }
        if(updateRequest.age()!=null && !updateRequest.age().equals(customer.getAge())){
            customer.setAge(updateRequest.age());
            changes = true;
        }

        if(!changes){
            throw  new RequestValidationException("Aucun user modifié");
        }
        customerDao.updateCustomer(customer);

    }
}
