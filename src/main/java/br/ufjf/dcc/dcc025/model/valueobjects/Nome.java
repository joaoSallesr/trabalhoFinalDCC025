package br.ufjf.dcc.dcc025.model.valueobjects;

import java.util.Objects;

import br.ufjf.dcc.dcc025.model.exception.InvalidNameException;

public class Nome {

    private final String nome;
    private final String sobrenome;

    public Nome(String nome, String sobrenome) {
        validaNome(nome, sobrenome);
        this.nome = nome;
        this.sobrenome = sobrenome;
    }

    // Validação
    private static void validaNome(String nome, String sobrenome) {
        if (nome.length() < 1 && sobrenome.length() < 1) {
            throw new InvalidNameException("Nome / Sobrenome inválido: " + nome + " " + sobrenome);
        }
    }

    // Getters
    public String getNome() {
        return nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    @Override
    public String toString() {
        return nome + " " + sobrenome;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Nome nome1 = (Nome) o;
        return Objects.equals(getNome(), nome1.getNome()) && Objects.equals(getSobrenome(), nome1.getSobrenome());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getNome());
    }

}
