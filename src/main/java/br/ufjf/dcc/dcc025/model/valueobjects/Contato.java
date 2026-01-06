package br.ufjf.dcc.dcc025.model.valueobjects;

import br.ufjf.dcc.dcc025.model.exception.InvalidPhoneNumberException;

public class Contato {
    private final String contato;

    public static Contato getInstance(String contato) {
        return new Contato(contato);
    }

    private Contato(String contato) {
        if (!isValid(contato))
            throw new InvalidPhoneNumberException("Número inválido: " + contato + " - (XX) XXXXX-XXXX");
        this.contato = contato;
    }

    private boolean isValid(String contato) {
        return !(contato.length()>11); // alterar para regra real se necessário
    }

    public String getContato() {
        return contato;
    }
}
