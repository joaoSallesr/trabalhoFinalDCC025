package br.ufjf.dcc.dcc025.model.valueobjects;

import java.util.Objects;

import br.ufjf.dcc.dcc025.model.exception.InvalidPhoneNumberException;

public class Contato {

    private final String numero;

    public Contato(String numero) {
        String contatoNumero = numero.replaceAll("\\D", "");
        validaNumero(contatoNumero);
        this.numero = contatoNumero;
    }

    // Validação
    private static void validaNumero(String numero) {
        if (numero == null || numero.isBlank()) {
            throw new InvalidPhoneNumberException("Número vazio.");
        }

        if (!numero.matches("\\d{11}")) {
            throw new InvalidPhoneNumberException("Número inválido: " + numero + " - (XX) XXXXX-XXXX");
        }
    }

    // Getters
    public String getNumero() {
        return numero;
    }

    public String getNumeroFormatado() {
        return numero.replaceAll("(\\d{2})(\\d{5})(\\d{4})", "($1) $2-$3");
    }

    // Overrides
    @Override
    public String toString() {
        return getNumeroFormatado();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Contato contato = (Contato) o;
        return Objects.equals(numero, contato.numero);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numero);
    }
}
