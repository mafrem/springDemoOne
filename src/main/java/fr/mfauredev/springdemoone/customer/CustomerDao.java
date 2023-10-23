package fr.mfauredev.springdemoone.customer;

import java.util.List;
import java.util.Optional;

public interface CustomerDao {

    List<Customer> selectAllCustomers();

    Optional<Customer> selectCustomerById(Integer id);

}
