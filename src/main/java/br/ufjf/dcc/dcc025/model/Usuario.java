package br.ufjf.dcc.dcc025.model;

import java.util.Objects;
import java.util.UUID;

import br.ufjf.dcc.dcc025.model.valueobjects.CPF;
import br.ufjf.dcc.dcc025.model.valueobjects.Email;
import br.ufjf.dcc.dcc025.model.valueobjects.Nome;
import br.ufjf.dcc.dcc025.model.valueobjects.Senha;

public abstract class Usuario {

    private final UUID id;
    private final Nome nome;
    private final CPF cpf;
    private Senha senha;
    private Email email;
    private boolean ativo;

    protected Usuario(Nome nome, CPF cpf, Senha senha, Email email) {
        this.id = UUID.randomUUID();
        this.nome = Objects.requireNonNull(nome, "Nome obrigatório.");
        this.cpf = Objects.requireNonNull(cpf, "CPF obrigatório.");
        this.senha = Objects.requireNonNull(senha, "Senha obrigatória.");
        this.email = Objects.requireNonNull(email, "Email obrigatório.");
        this.ativo = true;
    }

    // Atualização de atributos
    public void alterarSenha(Senha novaSenha) {
        this.senha = Objects.requireNonNull(novaSenha, "Nova senha obrigatória.");
    }

    public void alterarEmail(Email novoEmail) {
        this.email = Objects.requireNonNull(novoEmail, "Novo email obrigatório.");
    }

    public void ativarUsuario() {
        this.ativo = true;
    }

    public void desativarUsuario() {
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

    public Senha getSenha() {
        return senha;
    }

    public Email getEmail() {
        return email;
    }

    public boolean isAtivo() {
        return ativo;
    }
}
