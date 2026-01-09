package br.ufjf.dcc.dcc025.model;

import java.util.List;
import java.util.Objects;

import br.ufjf.dcc.dcc025.model.dto.DadosPaciente;
import br.ufjf.dcc.dcc025.model.valueobjects.CPF;
import br.ufjf.dcc.dcc025.model.valueobjects.Contato;
import br.ufjf.dcc.dcc025.model.valueobjects.Email;
import br.ufjf.dcc.dcc025.model.valueobjects.Endereco;
import br.ufjf.dcc.dcc025.model.valueobjects.Nome;
import br.ufjf.dcc.dcc025.model.valueobjects.Senha;

public class Paciente extends Usuario {

    private Contato contato;
    private Endereco endereco;
    private boolean hospitalizado;
    private boolean recebeVisita;

    public Paciente(Nome nome, CPF cpf, Senha senha, Email email, Contato contato, Endereco endereco) {
        super(nome, cpf, senha, email);
        this.contato = Objects.requireNonNull(contato, "Contato obrigatório.");
        this.endereco = Objects.requireNonNull(endereco, "Endereço obrigatório.");
        this.hospitalizado = false;
        this.recebeVisita = false;
    }

    // Atualização de atributos
    public void alterarContato(String novoNumero) {
        this.contato = Objects.requireNonNull(contato, "Novo contato obrigatório.");
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

    // Interações com usuário
    public boolean verificarVisita(String nome, String sobrenome, List<Paciente> listaPacientes) {
        for (Paciente paciente : listaPacientes) {
            if (paciente.getRecebeVisita() && paciente.getNome().getNome().equalsIgnoreCase(nome) && paciente.getNome().getSobrenome().equalsIgnoreCase(sobrenome)) {
                return true;
            }
        }
        return false;
    }

    public DadosPaciente consultarDados() {
        String meuNome = this.getNome().getNome();
        String meuSobrenome = this.getNome().getSobrenome();
        String meuCPF = this.getCPF().getCPF();
        String minhaSenha = this.getSenha().getSenha();
        String meuEmail = this.getEmail().getEmail();
        String meuContato = this.getContato().getNumero();
        String meuCEP = this.getEndereco().getCEP();
        String minhaRua = this.getEndereco().getRua();
        String meuBairro = this.getEndereco().getBairro();
        String minhaCidade = this.getEndereco().getCidade();
        int meuNumeroCasa = this.getEndereco().getNumeroCasa();

        DadosPaciente meusDados = new DadosPaciente(meuNome, meuSobrenome, meuCPF, minhaSenha, meuEmail, meuContato, meuCEP, minhaRua, meuBairro, minhaCidade, meuNumeroCasa);
        return meusDados;
    }

    public void marcarConsulta() {

    }

    public void desmarcarConsulta() {

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
