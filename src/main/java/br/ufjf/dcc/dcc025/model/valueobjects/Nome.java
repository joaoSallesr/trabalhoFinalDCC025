package br.ufjf.dcc.dcc025.model.valueobjects;

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
        if (nome.length() > 1 && sobrenome.length() > 1) {
            throw new InvalidNameException("Nome / Sobrenome inválido: " + nome + " " + sobrenome);
        }
    }

    // Getters
    public String getName() {
        return nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }
}
