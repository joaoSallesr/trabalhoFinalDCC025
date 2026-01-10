package br.ufjf.dcc.dcc025.controller;

import br.ufjf.dcc.dcc025.model.Medico;
import br.ufjf.dcc.dcc025.model.dto.DadosMedico;
import br.ufjf.dcc.dcc025.model.repository.GerenciadorRepository;
import br.ufjf.dcc.dcc025.model.valueobjects.Email;
import br.ufjf.dcc.dcc025.model.valueobjects.Senha;
import br.ufjf.dcc.dcc025.view.MedicoView;

public class MedicoController {

    private Medico medico;
    private MedicoView view;

    public MedicoController(Medico medico, MedicoView view) {
        this.medico = medico;
        this.view = view;
    }
    public void cadastrarMedico(DadosMedico dados) {
        Medico novoMedico = new Medico(dados);
        GerenciadorRepository.getInstance().adicionarMedico(novoMedico);
    }

    // Atualização de atributos
    public void alterarSenha(Medico medico, String novaSenha) {
        Senha senha = new Senha(novaSenha);
        medico.alterarSenha(senha);
        GerenciadorRepository.getInstance().salvarMedicos();
    }

    public void alterarEmail(Medico medico, String novoEmail) {
        Email email = new Email(novoEmail);
        medico.alterarEmail(email);
        GerenciadorRepository.getInstance().salvarMedicos();
    }

    public void ativarUsuario(Medico medico) {
        medico.ativarUsuario();
        GerenciadorRepository.getInstance().salvarMedicos();
    }

    public void desativarUsuario(Medico medico) {
        medico.desativarUsuario();
        GerenciadorRepository.getInstance().salvarMedicos();
    }
}
