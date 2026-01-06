package br.ufjf.dcc.dcc025.model.valueobjects;

import br.ufjf.dcc.dcc025.model.exception.InvalidEmailException;
import org.apache.commons.validator.routines.EmailValidator;

public class Email {
    private String email;

    public Email getInstance(String email) {
        return new Email(email);
    }

    private Email(String email) {
        if (!isValid(email))
            throw new InvalidEmailException("Invalid email: " + email);
        this.email = email;
    }

    private boolean isValid(String email) {
        EmailValidator validator = EmailValidator.getInstance();
        
        return validator.isValid(email);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (!isValid(email))
            throw new InvalidEmailException("Invalid email: " + email);
        this.email = email;        
    }
}
