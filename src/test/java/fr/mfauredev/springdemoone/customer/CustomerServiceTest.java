package fr.mfauredev.springdemoone.customer;

import fr.mfauredev.springdemoone.exceptions.DuplicateResourceException;
import fr.mfauredev.springdemoone.exceptions.MissingResourceException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerDao customerDao;
    private CustomerService underTest;

    @BeforeEach
    void setUp() {
        underTest = new CustomerService(customerDao);
    }

    @Test
    void getAllCustomersTest() {
        underTest.getAllCustomers();
        verify(customerDao).selectAllCustomers();
    }

    @Test
    void getCustomerByIdTest() {
        //Given
        int id = 999;
        Customer c = new Customer(id,"a","b", 30);
        when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(c));

        //When
        Customer actuel = underTest.getCustomerById(id);

        //Then
        assertThat(actuel).isEqualTo(c);
    }

    @Test
    void getCustomerByIdTestException() {
        //Given
        int id = 999;
        when(customerDao.selectCustomerById(id)).thenReturn(Optional.empty());

        //Then
        assertThatThrownBy( () -> underTest.getCustomerById(id))
                .isInstanceOf(MissingResourceException.class)
                .hasMessage("Bouh [%s]".formatted(id));
    }

    @Test
    void addCustomer() {
        //Given

        //When

        //Then
    }

    @Test
    void deleteCustomerById() {
            //Given

            //When

            //Then
    }

    @Test
    void updateCustomer() {
            //Given

            //When

            //Then
    }
}