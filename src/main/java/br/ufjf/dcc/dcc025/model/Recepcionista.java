package br.ufjf.dcc.dcc025.model;

import br.ufjf.dcc.dcc025.model.dto.DadosMedico;
import br.ufjf.dcc.dcc025.model.dto.DadosPaciente;
import br.ufjf.dcc.dcc025.model.dto.DadosRecepcionista;
import br.ufjf.dcc.dcc025.model.repository.GerenciadorRepository;
import br.ufjf.dcc.dcc025.model.valueobjects.CPF;
import br.ufjf.dcc.dcc025.model.valueobjects.Contato;
import br.ufjf.dcc.dcc025.model.valueobjects.Email;
import br.ufjf.dcc.dcc025.model.valueobjects.Endereco;
import br.ufjf.dcc.dcc025.model.valueobjects.Especialidade;
import br.ufjf.dcc.dcc025.model.valueobjects.Nome;
import br.ufjf.dcc.dcc025.model.valueobjects.Senha;

public class Recepcionista extends Usuario {

    public Recepcionista(Nome nome, CPF cpf, Senha senha, Email email) {
        super(nome, cpf, senha, email);
    }

    // Cadastros de usuário (salva dados no json dedicado ao tipo de usuário)
    public void cadastrarPaciente(DadosPaciente dados) {
        Nome novoNome = new Nome(dados.getNome(), dados.getSobrenome());
        CPF novoCpf = new CPF(dados.getCPF());
        Senha novaSenha = new Senha(dados.getSenha());
        Email novoEmail = new Email(dados.getEmail());
        Contato novoContato = new Contato(dados.getNumeroContato());
        Endereco novoEndereco = new Endereco(dados.getCEP(), dados.getRua(),
                dados.getBairro(), dados.getCidade(), dados.getNumeroCasa());

        Paciente novoPaciente = new Paciente(novoNome, novoCpf, novaSenha, novoEmail, novoContato, novoEndereco);
        GerenciadorRepository.getInstance().adicionarPaciente(novoPaciente);
    }

    public void cadastrarMedico(DadosMedico dados) {
        Nome novoNome = new Nome(dados.getNome(), dados.getSobrenome());
        CPF novoCPF = new CPF(dados.getCPF());
        Senha novaSenha = new Senha(dados.getSenha());
        Email novoEmail = new Email(dados.getEmail());
        Especialidade novaEspecialidade = Especialidade.fromString(dados.getEspecialidade());

        Medico novoMedico = new Medico(novoNome, novoCPF, novaSenha, novoEmail, novaEspecialidade);
        GerenciadorRepository.getInstance().adicionarMedico(novoMedico);
    }

    public void cadastrarRecepcionista(DadosRecepcionista dados) {
        Nome novoNome = new Nome(dados.getNome(), dados.getSobrenome());
        CPF novoCPF = new CPF(dados.getCPF());
        Senha novaSenha = new Senha(dados.getSenha());
        Email novoEmail = new Email(dados.getEmail());

        Recepcionista novoRecepcionista = new Recepcionista(novoNome, novoCPF, novaSenha, novoEmail);
        GerenciadorRepository.getInstance().adicionarRecepcionista(novoRecepcionista);
    }
}
