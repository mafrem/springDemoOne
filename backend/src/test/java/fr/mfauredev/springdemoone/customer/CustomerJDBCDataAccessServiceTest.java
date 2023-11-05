package fr.mfauredev.springdemoone.customer;

import fr.mfauredev.springdemoone.AbstractTestcontainers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerJDBCDataAccessServiceTest extends AbstractTestcontainers {

    private CustomerJDBCDataAccessService underTest;
    private final CustomerRowMapper customerRowMapper = new CustomerRowMapper();

    @BeforeEach
    void setUp() {
        underTest = new CustomerJDBCDataAccessService(getJdbcTemplate() , customerRowMapper);
    }

    @Test
    void selectAllCustomers() {
        Customer c = new Customer(
                FAKER.name().fullName(),
                FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID(),
                20
        );
        underTest.insertCustomer(c);

        List<Customer> customers = underTest.selectAllCustomers();

        assertThat(customers).isNotEmpty();

    }

    @Test
    void selectCustomerById() {
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Customer c = new Customer(
                FAKER.name().fullName(),
                email,
                20
        );
        underTest.insertCustomer(c);

        int id = underTest.selectAllCustomers().stream()
                .filter(customer1 -> customer1.getEmail().equals(email))
                .mapToInt(customer2 -> customer2.getId())
                .findFirst()
                .orElseThrow();

        assertThat(underTest.selectCustomerById(id).equals(c));

    }

    @Test
    void emptyResultSelectCustomerById(){
        //Given
        int id = -1;

        //When
        Optional<Customer> customer = underTest.selectCustomerById(id);

        //Then
        assertThat(customer.isEmpty());
    }

    @Test
    void insertCustomer() {
        Customer c = new Customer(
                FAKER.name().fullName(),
                FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID(),
                20
        );
        System.out.println(c.getId());
        underTest.insertCustomer(c);
    }

    @Test
    void existsPersonWithEmail() {
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Customer c = new Customer(
                FAKER.name().fullName(),
                email,
                20
        );
        underTest.insertCustomer(c);

        boolean actual = underTest.existsPersonWithEmail(email);
        assertThat(actual).isTrue();
    }

    @Test
    void existsPersonWithId() {
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Customer c = new Customer(
                FAKER.name().fullName(),
                email,
                20
        );
        underTest.insertCustomer(c);

        int id = underTest.selectAllCustomers().stream()
                .filter(customer1 -> customer1.getEmail().equals(email))
                .mapToInt(customer2 -> customer2.getId())
                .findFirst()
                .orElseThrow();

        assertThat(underTest.existsPersonWithId(id)).isTrue();
    }

    @Test
    void deleteCustomerById() {
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Customer c = new Customer(
                FAKER.name().fullName(),
                email,
                20
        );
        underTest.insertCustomer(c);

        int id = underTest.selectAllCustomers().stream()
                .filter(customer1 -> customer1.getEmail().equals(email))
                .mapToInt(customer2 -> customer2.getId())
                .findFirst()
                .orElseThrow();

        underTest.deleteCustomerById(id);

        assertThat(underTest.selectCustomerById(id).isEmpty());


    }

    @Test
    void updateCustomer() {
        String email = "lemail";
        Customer c = new Customer(
                "Mimichel",
                email,
                20
        );
        underTest.insertCustomer(c);

        int id = underTest.selectAllCustomers().stream()
                .filter(customer1 -> customer1.getEmail().equals(email))
                .mapToInt(customer2 -> customer2.getId())
                .findFirst()
                .orElseThrow();

        c = new Customer(
                "Jambon",
                "lemail2",
                28
        );
        underTest.updateCustomer(c);

        Customer actual = underTest.selectCustomerById(id).get();

        assertThat(actual.getName().equals("Jambon"));
        assertThat(actual.getEmail().equals("lemail2"));
        assertThat(actual.getAge() == 28);
    }
}