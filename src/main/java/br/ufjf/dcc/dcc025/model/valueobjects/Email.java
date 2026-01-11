package br.ufjf.dcc.dcc025.model.valueobjects;

import java.util.Objects;

import org.apache.commons.validator.routines.EmailValidator;

import br.ufjf.dcc.dcc025.model.exception.InvalidEmailException;

public class Email {

    private final String email;

    private static final EmailValidator VALIDATOR = EmailValidator.getInstance();

    public Email(String email) {
        validaEmail(email);
        this.email = email;
    }

    // Validação
    private static void validaEmail(String email) {
        if (!VALIDATOR.isValid(email)) {
            throw new InvalidEmailException("Email inválido: " + email);
        }
    }

    // Getters
    public String getEmail() {
        return email;
    }

    // Overrides
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Email emailObj = (Email) o;
        return Objects.equals(email, emailObj.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    @Override
    public String toString() {
        return email;
    }
}
