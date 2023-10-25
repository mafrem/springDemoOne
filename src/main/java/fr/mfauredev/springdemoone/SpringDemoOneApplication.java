package fr.mfauredev.springdemoone;

import fr.mfauredev.springdemoone.customer.Customer;
import fr.mfauredev.springdemoone.customer.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class SpringDemoOneApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringDemoOneApplication.class, args);
    }

    @Bean
    CommandLineRunner runner (CustomerRepository customerRepository){
        return args -> {
            Customer alex = new Customer("Alec","mail@mail",21);
            Customer jamila = new Customer("Jamila","mail2@mail",24);

            List<Customer> customers = List.of(alex, jamila);
            customerRepository.saveAll(customers);
        };
     }

}
