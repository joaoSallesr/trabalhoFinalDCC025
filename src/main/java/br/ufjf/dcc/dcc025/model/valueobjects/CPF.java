package br.ufjf.dcc.dcc025.model.valueobjects;

import java.util.Objects;

import br.ufjf.dcc.dcc025.model.exception.InvalidCPFException;

public class CPF {

    private final String cpf;

    public CPF(String cpf) {
        String cpfNumero = validaELimpaCPF(cpf);
        this.cpf = cpfNumero;
    }

    // Validação
    private static String validaELimpaCPF(String cpf) {
        if (cpf == null) {
            throw new InvalidCPFException("CPF não pode ser nulo.");
        }

        String cpfNumero = cpf.replaceAll("\\D", "");
        if (cpfNumero.length() != 11) {
            throw new InvalidCPFException("CPF inválido. Deve conter 11 dígitos.");
        }

        return cpfNumero;
    }

    // Getters
    public String getCPF() {
        return cpf;
    }

    public String getCPFFormatado() {
        return cpf.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
    }

    // Overrides
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CPF other = (CPF) o;
        return Objects.equals(cpf, other.cpf);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cpf);
    }

    @Override
    public String toString() {
        return getCPFFormatado();
    }
}
