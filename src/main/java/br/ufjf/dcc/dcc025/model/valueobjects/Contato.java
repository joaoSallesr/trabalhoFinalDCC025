package br.ufjf.dcc.dcc025.model.valueobjects;

import br.ufjf.dcc.dcc025.model.exception.InvalidPhoneNumberException;

public class Contato {

    private String numero;

    public static Contato getInstance(String numero) {
        return new Contato(numero);
    }

    private Contato(String numero) {
        validaNumero(numero);
        this.numero = numero;
    }

    // Alteração de número
    public void alterar(String numero) {
        validaNumero(numero);
        this.numero = numero;
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
}
