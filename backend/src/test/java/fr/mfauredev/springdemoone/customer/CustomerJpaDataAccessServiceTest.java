package fr.mfauredev.springdemoone.customer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class CustomerJpaDataAccessServiceTest {

    private CustomerJpaDataAccessService underTest;
    private AutoCloseable autoCloseable;

    @Mock
    private CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new CustomerJpaDataAccessService(customerRepository);
    }

    @AfterEach
    void tearDown() throws Exception{
        autoCloseable.close();
    }

    @Test
    void selectAllCustomers() {
        underTest.selectAllCustomers();
        Mockito.verify(customerRepository).findAll();
    }

    @Test
    void selectCustomerById() {
        int id =1;
        underTest.selectCustomerById(id);
        Mockito.verify(customerRepository).findById(id);
    }

    @Test
    void insertCustomer() {
        Customer c = new Customer(1,"a","b", 30);
        underTest.insertCustomer(c);
        Mockito.verify(customerRepository).save(c);
    }

    @Test
    void existsPersonWithEmail() {
        String email = "lemail@lemail.lemail";
        underTest.existsPersonWithEmail(email);
        Mockito.verify((customerRepository)).existsCustomerByEmail(email);
    }

    @Test
    void existsPersonWithId() {
        int id = 999999;
        underTest.existsPersonWithId(id);
        Mockito.verify(customerRepository).existsCustomerById(id);
    }

    @Test
    void deleteCustomerById() {
        int id = 99999;
        underTest.deleteCustomerById(id);
        Mockito.verify(customerRepository).deleteById(id);
    }

    @Test
    void updateCustomer() {
        Customer c = new Customer(1,"a","b", 30);
        underTest.updateCustomer(c);
        Mockito.verify(customerRepository).save(c);
    }
}