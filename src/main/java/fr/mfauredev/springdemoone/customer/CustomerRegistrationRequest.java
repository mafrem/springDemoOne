package fr.mfauredev.springdemoone.customer;

public record CustomerRegistrationRequest (
        String name,
        String email,
        Integer age
){
}
