package br.ufjf.dcc.dcc025.model.valueobjects;

import org.apache.commons.validator.routines.EmailValidator;

import br.ufjf.dcc.dcc025.model.exception.InvalidEmailException;

public class Email {
    private String email;

    public static Email getInstance(String email) {
        return new Email(email);
    }

    private Email(String email) {
        if (!isValid(email))
            throw new InvalidEmailException("Email inválido: " + email);
        this.email = email;
    }

    private boolean isValid(String email) {
        EmailValidator validator = EmailValidator.getInstance();
        
        return validator.isValid(email);
    }

    public String getEmail() {
        return email;
    }
}
