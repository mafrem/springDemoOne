package fr.mfauredev.springdemoone.customer;

public record CustomerUpdateRequest(String name, Integer age, String email) {
}
