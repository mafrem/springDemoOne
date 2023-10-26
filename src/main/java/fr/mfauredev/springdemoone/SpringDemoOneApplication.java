package fr.mfauredev.springdemoone;

import fr.mfauredev.springdemoone.customer.Customer;
import fr.mfauredev.springdemoone.customer.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import com.github.javafaker.*;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class SpringDemoOneApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringDemoOneApplication.class, args);
    }

    @Bean
    CommandLineRunner runner (CustomerRepository customerRepository){
        return args -> {

            Faker faker = new Faker();
            Customer customer;
            List<Customer> customers = new ArrayList<>();
            for(int i=0; i<100; ++i){
                String email = faker.internet().emailAddress();
                String name = faker.name().fullName();
                Integer age = faker.number().numberBetween(12,99);
                customer = new Customer(name,email,age);

                customers.add(customer);
            }

            customerRepository.saveAll(customers);
        };
     }

}
