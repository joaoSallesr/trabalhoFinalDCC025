package br.ufjf.dcc.dcc025.model;

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

    public Paciente(DadosPaciente dados) {
        super(
                new Nome(dados.getNome(), dados.getSobrenome()),
                new CPF(dados.getCPF()),
                new Senha(dados.getSenha()),
                new Email(dados.getEmail())
        );
        this.contato = new Contato(dados.getNumeroContato());
        this.endereco = new Endereco(
                dados.getCEP(),
                dados.getRua(),
                dados.getBairro(),
                dados.getCidade(),
                dados.getNumeroCasa()
        );
        this.hospitalizado = false;
        this.recebeVisita = false;
    }

    // Atualização de atributos
    public void alterarContato(Contato novoContato) {
        this.contato = Objects.requireNonNull(novoContato, "Novo contato obrigatório.");
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

    public DadosPaciente consultarDados() {
        String meuNome = this.getNome().getNome();
        String meuSobrenome = this.getNome().getSobrenome();
        String meuCPF = this.getCPF().getCPF();
        String minhaSenha = this.getSenha().getSenha();
        String meuEmail = this.getEmail().getEmail();
        String meuContato = this.contato.getNumero();
        String meuCEP = this.endereco.getCEP();
        String minhaRua = this.endereco.getRua();
        String meuBairro = this.endereco.getBairro();
        String minhaCidade = this.endereco.getCidade();
        int meuNumeroCasa = this.endereco.getNumeroCasa();

        DadosPaciente meusDados = new DadosPaciente(meuNome, meuSobrenome, meuCPF, minhaSenha, meuEmail, meuContato, meuCEP, minhaRua, meuBairro, minhaCidade, meuNumeroCasa);
        return meusDados;
    }

    // Getters
    public boolean isHospitalizado() {
        return hospitalizado;
    }

    public boolean isRecebeVisita() {
        return recebeVisita;
    }

    public Contato getContato() {
        return contato;
    }

    public Endereco getEndereco() {
        return endereco;
    }
}
