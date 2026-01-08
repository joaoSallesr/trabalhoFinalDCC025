package br.ufjf.dcc.dcc025.model;

import java.util.Objects;
import java.util.UUID;

import br.ufjf.dcc.dcc025.model.valueobjects.CPF;
import br.ufjf.dcc.dcc025.model.valueobjects.Email;
import br.ufjf.dcc.dcc025.model.valueobjects.Nome;

public abstract class Usuario {
    
    private final UUID id;
    private final Nome nome;
    private final CPF cpf;
    private Email email;
    private boolean ativo;

    protected Usuario(Nome nome, CPF cpf, Email email) {
        this.id = UUID.randomUUID();
        this.nome = Objects.requireNonNull(nome, "Nome obrigatório.");
        this.cpf = Objects.requireNonNull(cpf, "CPF obrigatório.");
        this.email = Objects.requireNonNull(email, "Email obrigatório.");
        this.ativo = true;
    }

    public void alterarEmail(String novoEmail) {
        this.email.alterar(novoEmail);
    }

    public void ativar() {
        this.ativo = true;
    }

    public void desativar() {
        this.ativo = false;
    }

    // Getters
    public UUID getId() {
        return id;
    }

    public Nome getNome() {
        return nome;
    }

    public CPF getCPF() {
        return cpf;
    }

    public Email getEmail() {
        return email;
    }

    public boolean getAtivo() {
        return ativo;
    }
}
