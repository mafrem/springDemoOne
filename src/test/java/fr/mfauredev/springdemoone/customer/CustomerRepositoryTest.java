package fr.mfauredev.springdemoone.customer;

import fr.mfauredev.springdemoone.AbstractTestcontainers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace =  AutoConfigureTestDatabase.Replace.NONE)
class CustomerRepositoryTest extends AbstractTestcontainers {

    @Autowired
    private CustomerRepository underTest;

    @Autowired
    private ApplicationContext applicationContext;

    @BeforeEach
    void setUp() {
    }

    @Test
    void existsCustomerByEmail() {
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Customer c = new Customer(
                FAKER.name().fullName(),
                email,
                20
        );
        underTest.save(c);

        //When
        boolean actual = underTest.existsCustomerByEmail(email);

        //Then
        assertThat(actual).isTrue();
    }

    @Test
    void existsCustomerById() {
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Customer c = new Customer(
                FAKER.name().fullName(),
                email,
                20
        );
        underTest.save(c);

        int id = underTest.findAll().stream()
                .filter(customer1 -> customer1.getEmail().equals(email))
                .mapToInt(customer2 -> customer2.getId())
                .findFirst()
                .orElseThrow();

        //When
        boolean actual = underTest.existsById(id);

        //Then
        assertThat(actual).isTrue();
    }
}