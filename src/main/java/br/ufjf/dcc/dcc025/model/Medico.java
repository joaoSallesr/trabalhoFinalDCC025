package br.ufjf.dcc.dcc025.model;

import java.util.Objects;

import br.ufjf.dcc.dcc025.model.valueobjects.*;

public class Medico extends Usuario {
    private final Especialidade especialidade;

    public Medico(Nome nome, CPF cpf, Email email, Senha password, Especialidade especialidade) {
        super(nome, cpf, email, password);
        this.especialidade = Objects.requireNonNull(especialidade, "Especialidade obrigatória.");
    }
}
