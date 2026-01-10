package br.ufjf.dcc.dcc025.controller;

import br.ufjf.dcc.dcc025.model.Paciente;
import br.ufjf.dcc.dcc025.model.dto.DadosPaciente;
import br.ufjf.dcc.dcc025.model.repository.GerenciadorRepository;
import br.ufjf.dcc.dcc025.model.valueobjects.Contato;
import br.ufjf.dcc.dcc025.model.valueobjects.Email;
import br.ufjf.dcc.dcc025.model.valueobjects.Endereco;
import br.ufjf.dcc.dcc025.model.valueobjects.Senha;

public class PacienteController {

    public void cadastrarPaciente(DadosPaciente dados) {
        Paciente novoPaciente = new Paciente(dados);
        GerenciadorRepository.getInstance().adicionarPaciente(novoPaciente);
    }

    // Atualização de atributos
    public void alterarSenha(Paciente paciente, String novaSenha) {
        Senha senha = new Senha(novaSenha);
        paciente.alterarSenha(senha);
        GerenciadorRepository.getInstance().salvarPacientes();
    }

    public void alterarEmail(Paciente paciente, String novoEmail) {
        Email email = new Email(novoEmail);
        paciente.alterarEmail(email);
        GerenciadorRepository.getInstance().salvarPacientes();
    }

    public void alterarContato(Paciente paciente, String novoNumero) {
        Contato contato = new Contato(novoNumero);
        paciente.alterarContato(contato);
        GerenciadorRepository.getInstance().salvarPacientes();
    }

    public void alterarEndereco(
            Paciente paciente, String cep, String rua,
            String bairro, String cidade, int numeroCasa) {
        Endereco endereco = new Endereco(cep, rua, bairro, cidade, numeroCasa);
        paciente.alterarEndereco(endereco);
        GerenciadorRepository.getInstance().salvarPacientes();
    }

    public void ativarUsuario(Paciente paciente) {
        paciente.ativarUsuario();
        GerenciadorRepository.getInstance().salvarPacientes();
    }

    public void desativarUsuario(Paciente paciente) {
        paciente.desativarUsuario();
        GerenciadorRepository.getInstance().salvarPacientes();
    }

    public void hospitalizar(Paciente paciente) {
        paciente.hospitalizar();
        GerenciadorRepository.getInstance().salvarPacientes();
    }

    public void deshospitalizar(Paciente paciente) {
        paciente.deshospitalizar();
        GerenciadorRepository.getInstance().salvarPacientes();
    }

    public void permitirVisita(Paciente paciente) {
        paciente.permitirVisita();
        GerenciadorRepository.getInstance().salvarPacientes();
    }

    public void bloquearVisita(Paciente paciente) {
        paciente.bloquearVisita();
        GerenciadorRepository.getInstance().salvarPacientes();
    }

    // Ações
    public void marcarConsulta() {

    }

    public void desmarcarConsulta() {

    }

    // Auxiliares
}
