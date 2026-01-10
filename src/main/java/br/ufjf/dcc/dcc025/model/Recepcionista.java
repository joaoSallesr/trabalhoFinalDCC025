package br.ufjf.dcc.dcc025.model;

import br.ufjf.dcc.dcc025.model.dto.DadosRecepcionista;
import br.ufjf.dcc.dcc025.model.valueobjects.CPF;
import br.ufjf.dcc.dcc025.model.valueobjects.Email;
import br.ufjf.dcc.dcc025.model.valueobjects.Nome;
import br.ufjf.dcc.dcc025.model.valueobjects.Senha;

public class Recepcionista extends Usuario {

    public Recepcionista(DadosRecepcionista dados) {
        super(
                new Nome(dados.getNome(), dados.getSobrenome()),
                new CPF(dados.getCPF()),
                new Senha(dados.getSenha()),
                new Email(dados.getEmail())
        );
    }
}
