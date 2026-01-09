package br.ufjf.dcc.dcc025.model;

import java.util.Objects;

import br.ufjf.dcc.dcc025.model.valueobjects.CPF;
import br.ufjf.dcc.dcc025.model.valueobjects.Contato;
import br.ufjf.dcc.dcc025.model.valueobjects.Email;
import br.ufjf.dcc.dcc025.model.valueobjects.Endereco;
import br.ufjf.dcc.dcc025.model.valueobjects.Nome;

public class Paciente extends Usuario {

    private Contato contato;
    private Endereco endereco;
    private boolean hospitalizado;
    private boolean recebeVisita;

    public Paciente(Nome nome, CPF cpf, Email email, Contato contato, Endereco endereco) {
        super(nome, cpf, email);
        this.contato = Objects.requireNonNull(contato, "Contato obrigatório.");
        this.endereco = Objects.requireNonNull(endereco, "Endereço obrigatório.");
        this.hospitalizado = false;
        this.recebeVisita = false;
    }

    // Atualização de atributos
    public void alterarContato(String novoNumero) {
        this.contato = Objects.requireNonNull(contato, "Contato obrigatório.");
    }
    public void alterarEndereco(Endereco novoEndereco) {
        this.endereco = Objects.requireNonNull(novoEndereco, "Novo endereço obrigatório.");
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
        if (!hospitalizado) {
            throw new IllegalStateException("Paciente não hospitalizado.");
        }
        this.recebeVisita = true;
    }

    public void bloquearVisita() {
        this.recebeVisita = false;
    }

    // Getters
    public boolean getHospitalizado() {
        return hospitalizado;
    }

    public boolean getRecebeVisita() {
        return recebeVisita;
    }

    public Contato getContato() {
        return contato;
    }

    public Endereco getEndereco() {
        return endereco;
    }
}
