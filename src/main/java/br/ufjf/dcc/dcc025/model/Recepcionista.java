package br.ufjf.dcc.dcc025.model;

import br.ufjf.dcc.dcc025.model.valueobjects.*;

public class Recepcionista extends Usuario {
    public Recepcionista(Nome nome, CPF cpf, Email email, Senha password, Especialidade especialidade) {
        super(nome, cpf, email, password);
    }
}
