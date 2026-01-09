package br.ufjf.dcc.dcc025.model;

import java.util.List;

import com.google.gson.reflect.TypeToken;

import br.ufjf.dcc.dcc025.model.dto.DadosMedico;
import br.ufjf.dcc.dcc025.model.dto.DadosPaciente;
import br.ufjf.dcc.dcc025.model.repository.Repository;
import br.ufjf.dcc.dcc025.model.valueobjects.CPF;
import br.ufjf.dcc.dcc025.model.valueobjects.Contato;
import br.ufjf.dcc.dcc025.model.valueobjects.Email;
import br.ufjf.dcc.dcc025.model.valueobjects.Endereco;
import br.ufjf.dcc.dcc025.model.valueobjects.Especialidade;
import br.ufjf.dcc.dcc025.model.valueobjects.Nome;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;

public class Recepcionista extends Usuario {

    private List<Paciente> pacientes;

    public Recepcionista(Nome nome, CPF cpf, Email email) {
        super(nome, cpf, email);
        this.pacientes = new java.util.ArrayList<>();
    }

    // Cadastros (salvar esses novoUsuario em um local)
    public void cadastrarPaciente(DadosPaciente dados) {
        Nome novoNome = new Nome(dados.getNome(), dados.getSobrenome());
        CPF novoCpf = new CPF(dados.getCPF());
        Email novoEmail = new Email(dados.getEmail());
        Contato novoContato = new Contato(dados.getNumeroContato());
        Endereco novoEndereco = new Endereco(dados.getCEP(), dados.getRua(),
                dados.getBairro(), dados.getCidade(), dados.getNumeroCasa());

        Repository<Paciente> repositorio = new Repository<>("pacientes.json");
        Paciente novoPaciente = new Paciente(novoNome, novoCpf, novoEmail, novoContato, novoEndereco);
        Type tipoListaPaciente = new TypeToken<List<Paciente>>(){}.getType();
        this.pacientes = new java.util.ArrayList<>();
        this.pacientes = repositorio.carregarUsuarios(tipoListaPaciente);
        pacientes.add(novoPaciente);
        repositorio.salvar(pacientes);
    }

    public void cadastrarMedico(DadosMedico dados) {
        Nome novoNome = new Nome(dados.getNome(), dados.getSobrenome());
        CPF novoCPF = new CPF(dados.getCPF());
        Email novoEmail = new Email(dados.getEmail());
        Especialidade novaEspecialidade = Especialidade.fromString(dados.getEspecialidade());

        Medico novoMedico = new Medico(novoNome, novoCPF, novoEmail, novaEspecialidade);
    }

    public void carregarLista() {
        this.pacientes = new java.util.ArrayList<>();
        //System.out.println(pacientes.getFirst().getNome());
        Repository<Paciente> repositorio = new Repository<>("pacientes.json");
        Type tipoListaPaciente = new TypeToken<List<Paciente>>(){}.getType();
        this.pacientes = repositorio.carregarUsuarios(tipoListaPaciente);
        System.out.println(pacientes.getFirst().getNome());
    }
}
