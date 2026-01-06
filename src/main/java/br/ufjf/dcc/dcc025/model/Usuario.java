package br.ufjf.dcc.dcc025.model;

import java.util.Objects;
import java.util.UUID;

import br.ufjf.dcc.dcc025.model.valueobjects.*;

public abstract class Usuario {
    private final UUID id;
    private final Nome nome;
    private final CPF cpf;
    private Email email;
    private final Senha senha;
    private boolean ativo;

    protected Usuario(Nome nome, CPF cpf, Email email, Senha senha) {
        this.id = UUID.randomUUID();
        this.nome = Objects.requireNonNull(nome, "Nome obrigatório.");
        this.cpf = Objects.requireNonNull(cpf, "CPF obrigatório.");
        this.senha = Objects.requireNonNull(senha, "Senha obrigatória.");
        this.ativo = true;
    }

    protected void changeEmail(Email email) {
        this.email = Objects.requireNonNull(email, "Novo email obrigatório.");
    }

    public void ativar() {
        this.ativo = true;
    }

    public void desativar() {
        this.ativo = false;
    }
}
