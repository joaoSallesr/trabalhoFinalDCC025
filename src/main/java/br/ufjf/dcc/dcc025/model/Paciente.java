package br.ufjf.dcc.dcc025.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import br.ufjf.dcc.dcc025.model.dto.DadosPaciente;
import br.ufjf.dcc.dcc025.model.valueobjects.CPF;
import br.ufjf.dcc.dcc025.model.valueobjects.Consulta;
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
    private List<DocumentoMedico> documentos = new ArrayList<>();
    private List<Consulta> historicoConsultas = new ArrayList<>();

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
        this.historicoConsultas = new ArrayList<>();
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

    // Documentos médicos
    public void adicionarDocumento(DocumentoMedico documento) {
        if (documentos == null) {
            documentos = new ArrayList<>();
        }
        documentos.add(documento);
    }

    public List<DocumentoMedico> getDocumentos() {
        if (documentos == null) {
            documentos = new ArrayList<>();
        }
        return documentos;
    }

    // Consultas
    public void adicionarConsulta(Consulta consulta) {
        if (this.historicoConsultas == null) {
            this.historicoConsultas = new ArrayList<>();
        }
        this.historicoConsultas.add(consulta);
    }

    public void removerConsulta(Consulta consulta) {
        if (this.historicoConsultas != null) {
            this.historicoConsultas.remove(consulta);
        }
    }

    public List<Consulta> getConsultas() {
        if (this.historicoConsultas == null) {
            this.historicoConsultas = new ArrayList<>();
        }
        return historicoConsultas;
    }
    
    public void atualizarConsulta(Consulta antiga, Consulta nova) {
        int index = historicoConsultas.indexOf(antiga);
            if (index >= 0) {
                historicoConsultas.set(index, nova);
            }   
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

    // Overrides
    @Override
    public String toString() {
        return this.getNome().getNome() + " " + this.getNome().getSobrenome() + " (CPF: " + this.getCPF().getCPF() + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Paciente paciente = (Paciente) o;

        return Objects.equals(this.getCPF().getCPF(), paciente.getCPF().getCPF());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getCPF().getCPF());
    }
}
