package fr.mfauredev.springdemoone.journey;

import com.github.javafaker.Faker;
import fr.mfauredev.springdemoone.customer.Customer;
import fr.mfauredev.springdemoone.customer.CustomerRegistrationRequest;
import fr.mfauredev.springdemoone.customer.CustomerUpdateRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;


@SpringBootTest(webEnvironment = RANDOM_PORT)
public class CustomerIntegrationTest {

    @Autowired
    WebTestClient webTestClient;

    @Test
    void canRegisterACustomerTest(){
        //create registration request
        Faker faker = new Faker();
        String firstname = faker.name().firstName();
        String lastname = faker.name().lastName().toLowerCase().trim();
        String thename = firstname + "  " + lastname;
        String email = lastname + faker.number().numberBetween(1,999999) + "@mfaure.fr";
        int age2 = faker.number().numberBetween(1,99);
        int age = age2;
        CustomerRegistrationRequest request = new CustomerRegistrationRequest(thename,email,age);

        //send a post resquest
        webTestClient.post()
                .uri("/api/v1/customers")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(request), CustomerRegistrationRequest.class)
                .exchange()
                .expectStatus()
                .isOk();

        //get all customers
        List<Customer> responseBody = webTestClient.get()
                .uri("/api/v1/customers")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(new ParameterizedTypeReference<Customer>() {})
                //.expectBodyList(Customer.class)
                .returnResult()
                .getResponseBody();

        Customer expected = new Customer(thename,email,age);

        //verifiy he is present
        assertThat(responseBody).
                usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
                .contains(expected);

        //get the customer by ID
        var id = responseBody.stream()
                .filter(c -> c.getEmail().equals(email))
                .findFirst()
                .get().getId();

        expected.setId(id);

        Customer responseBodyUniqueCustomer =
                webTestClient.get()
                        .uri("/api/v1/customers/{id}",id)
                        .accept(MediaType.APPLICATION_JSON)
                        .exchange()
                        .expectStatus()
                        .isOk()
                        .expectBody(new ParameterizedTypeReference<Customer>() {})
                        .returnResult()
                        .getResponseBody();

        assertThat(responseBodyUniqueCustomer).isEqualTo(expected);


    }

    @Test
    void deleteCustomerTest(){
        //create registration request
        Faker faker = new Faker();
        String firstname = faker.name().firstName();
        String lastname = faker.name().lastName().toLowerCase().trim();
        String thename = firstname + "  " + lastname;
        String email = lastname + faker.number().numberBetween(1,999999) + "@mfaure.fr";
        int age2 = faker.number().numberBetween(1,99);
        int age = age2;
        CustomerRegistrationRequest request = new CustomerRegistrationRequest(thename,email,age);

        //send a post resquest
        webTestClient.post()
                .uri("/api/v1/customers")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(request), CustomerRegistrationRequest.class)
                .exchange()
                .expectStatus()
                .isOk();

        //get all customers
        List<Customer> responseBody = webTestClient.get()
                .uri("/api/v1/customers")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(new ParameterizedTypeReference<Customer>() {
                })
                .returnResult()
                .getResponseBody();

        //get the customer by ID
        var id = responseBody.stream()
                .filter(c -> c.getEmail().equals(email))
                .findFirst()
                .get().getId();

        webTestClient.delete()
                .uri("/api/v1/customers/{id}",id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk();

        webTestClient.get()
                .uri("/api/v1/customers/{id}",id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isNotFound();


    }
    @Test
    void updateCustomerTest(){
        //create registration request

        Faker f = new Faker();
        String name1 = "Mimi";
        String name2 = "Lulu";
        String email1 = "mail1" + f.number().numberBetween(1,999) ;
        String email2 = "mail2"+ f.number().numberBetween(1,999);
        int age1 = 45;
        int age2 = 23;
        CustomerRegistrationRequest request = new CustomerRegistrationRequest(name1,email1,age1);
        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest(name2,age2,email2);

        //send a post resquest
        webTestClient.post()
                .uri("/api/v1/customers")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(request), CustomerRegistrationRequest.class)
                .exchange()
                .expectStatus()
                .isOk();

        //get all customers
        List<Customer> responseBody = webTestClient.get()
                .uri("/api/v1/customers")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(new ParameterizedTypeReference<Customer>() {
                })
                .returnResult()
                .getResponseBody();

        //get the customer by ID
        var id = responseBody.stream()
                .filter(c -> c.getEmail().equals(email1))
                .findFirst()
                .get().getId();

        webTestClient.put()
                .uri("/api/v1/customers/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(updateRequest), CustomerUpdateRequest.class)
                .exchange()
                .expectStatus()
                .isOk();

        Customer expected = webTestClient.get()
                .uri("/api/v1/customers/{id}",id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(Customer.class)
                .returnResult()
                .getResponseBody();

        assertThat(expected.getAge()).isEqualTo(age2);
        assertThat(expected.getName()).isEqualTo(name2);
        assertThat(expected.getEmail()).isEqualTo(email2);


    }

}
