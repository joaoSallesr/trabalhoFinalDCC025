package br.ufjf.dcc.dcc025.model;

import java.util.Objects;

import br.ufjf.dcc.dcc025.model.valueobjects.*;

public class Paciente extends Usuario {
    private Contato contato;
    private Endereco endereco;
    private boolean hospitalizado;
    private boolean recebeVisita;


    public Paciente(Nome nome, CPF cpf, Email email, Senha password, Contato contato, Endereco endereco) {
        super(nome, cpf, email, password);
        this.contato = Objects.requireNonNull(contato, "Contato obrigatório.");
        this.endereco = Objects.requireNonNull(endereco, "Endereço obrigatório.");
        this.hospitalizado = false;
        this.recebeVisita = false;
    }

    public void changeEndereco(Endereco endereco) {
        this.endereco = Objects.requireNonNull(endereco, "Novo endereço obrigatório.");
    }

    public void changeContato(Contato contato) {
        this.contato = Objects.requireNonNull(contato, "Novo contato obrigatório.");
    }

    public void hospitalizar() {
        this.hospitalizado = true;
        this.recebeVisita = false;
    }

    public void deshospitalizar() {
        this.hospitalizado = false;
        this.recebeVisita = false;
    }

    public void permitirVisita() {
        if (!hospitalizado)
            throw new IllegalStateException("Paciente não hospitalizado.");
        this.recebeVisita = true;
    }

    public void bloquearVisita() {
        this.recebeVisita = false;
    }
}
