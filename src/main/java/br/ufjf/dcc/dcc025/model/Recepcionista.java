package br.ufjf.dcc.dcc025.model;

import br.ufjf.dcc.dcc025.model.valueobjects.CPF;
import br.ufjf.dcc.dcc025.model.valueobjects.Email;
import br.ufjf.dcc.dcc025.model.valueobjects.Especialidade;
import br.ufjf.dcc.dcc025.model.valueobjects.Nome;
import br.ufjf.dcc.dcc025.model.valueobjects.Senha;

public class Recepcionista extends Usuario {

    public Recepcionista(Nome nome, CPF cpf, Email email, Senha password, Especialidade especialidade) {
        super(nome, cpf, email, password);
    }
}
