package fr.mfauredev.springdemoone.customer;

import java.util.List;
import java.util.Optional;

public class CustomerJDBCDataAccessService implements CustomerDao {
    @Override
    public List<Customer> selectAllCustomers() {
        return null;
    }

    @Override
    public Optional<Customer> selectCustomerById(Integer id) {
        return Optional.empty();
    }

    @Override
    public void insertCustomer(Customer customer) {

    }

    @Override
    public boolean existsPersonWithEmail(String email) {
        return false;
    }

    @Override
    public void deleteCustomer(Integer id) {

    }

    @Override
    public void updateCustomer(Integer id) {

    }
}
