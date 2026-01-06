package br.ufjf.dcc.dcc025.model.valueobjects;

import br.ufjf.dcc.dcc025.model.exception.InvalidNameException;

public class Nome {
    private final String nome;
    private final String sobrenome;

    public Nome(String nome, String sobrenome) {
        if (!isValid(nome, sobrenome))
            throw new InvalidNameException("Nome / Sobrenome inválido: " + nome + " " + sobrenome);
        this.nome = nome;
        this.sobrenome = sobrenome;
    }

    private static boolean isValid (String nome, String sobrenome) { 
        return nome.length() > 1 && sobrenome.length() > 1;
    }

    public String getName() {
        return nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }
}
