package fr.mfauredev.springdemoone.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class MissingResourceException extends RuntimeException{
    public MissingResourceException(String message) {
        super(message);
    }
}
