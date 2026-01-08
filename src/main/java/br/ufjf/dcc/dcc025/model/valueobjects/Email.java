package br.ufjf.dcc.dcc025.model.valueobjects;

import org.apache.commons.validator.routines.EmailValidator;

import br.ufjf.dcc.dcc025.model.exception.InvalidEmailException;

public class Email {

    private String email;

    private static final EmailValidator VALIDATOR = EmailValidator.getInstance();

    public Email(String email) {
        validaEmail(email);
        this.email = email;
    }

    // Alteração de email
    public void alterar(String email) {
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
}
