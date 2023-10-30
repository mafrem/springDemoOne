package fr.mfauredev.springdemoone.customer;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("jdbc")
public class CustomerJDBCDataAccessService implements CustomerDao {

    private final JdbcTemplate jdbcTemplate;
    private final CustomerRowMapper customerRowMapper;

    public CustomerJDBCDataAccessService(JdbcTemplate jdbcTemplate, CustomerRowMapper customerRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.customerRowMapper = customerRowMapper;
    }

    @Override
    public List<Customer> selectAllCustomers() {
        var sql = """
                    SELECT id, name, email, age
                    FROM customer
                """;
        return jdbcTemplate.query(sql, customerRowMapper);

    }
    @Override
    public Optional<Customer> selectCustomerById(Integer id) {
        var sql = """
                    SELECT id, name, email, age
                    FROM customer
                    WHERE id = ?
                """;
        return jdbcTemplate.query(sql, customerRowMapper, id).stream().findFirst();
    }

    @Override
    public void insertCustomer(Customer customer) {
        var sql = """
                    INSERT INTO customer(name, email, age)
                     VALUES (?, ?, ?)
                """;
        int result = jdbcTemplate.update(sql,
                customer.getName(),customer.getEmail(),customer.getAge());
        System.out.println("Result of UPDATE : [%s]".formatted(result));
    }

    @Override
    public boolean existsPersonWithEmail(String email) {
        return false;
    }

    @Override
    public boolean existsPersonWithId(Integer id) {
        var sql = """
                    SELECT id, name, email, age
                    FROM customer
                    WHERE id = ?
                """;
        List<Customer> customers = jdbcTemplate.query(sql, customerRowMapper, id);

        if (customers.size()>0){
            return true;
        }
        return false;
    }

    @Override
    public void deleteCustomerById(Integer customerId) {
        var sql = """
                    DELETE FROM customer
                    WHERE id = ?
                """;
        int result = jdbcTemplate.update(sql, customerId);
        System.out.println("Yayyy " + result);
    }

    @Override
    public void updateCustomer(Customer customer) {

    }
}
