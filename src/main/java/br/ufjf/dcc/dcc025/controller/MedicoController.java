package br.ufjf.dcc.dcc025.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

import javax.swing.JOptionPane;

import br.ufjf.dcc.dcc025.model.Medico;
import br.ufjf.dcc.dcc025.model.Paciente;
import br.ufjf.dcc.dcc025.model.dto.DadosMedico;
import br.ufjf.dcc.dcc025.model.repository.GerenciadorRepository;
import br.ufjf.dcc.dcc025.model.valueobjects.Email;
import br.ufjf.dcc.dcc025.model.valueobjects.Especialidade;
import br.ufjf.dcc.dcc025.model.valueobjects.HorarioTrabalho;
import br.ufjf.dcc.dcc025.model.valueobjects.Senha;
import br.ufjf.dcc.dcc025.view.LoginView;
import br.ufjf.dcc.dcc025.view.MedicoView;

public class MedicoController {

    private Medico medico;
    private MedicoView view;

    public MedicoController(Medico medico, MedicoView view) {
        this.medico = medico;
        this.view = view;

        // só adiciona listeners se houver view - evitar npe
        if (this.view != null) {
            this.view.addSairListener(new SairListener());
            this.view.addGerenciarStatusListener(new GerenciarStatusListener());
            this.view.addAdicionarHorarioListener(new AdicionarHorarioListener());
            this.view.addRemoverHorarioListener(new RemoverHorarioListener());
            this.view.addNavegarAgendaListener(new NavegarAgendaListener());
        }
    }

    public MedicoController() {
    }

    public void cadastrarMedico(DadosMedico dados) {
        Medico novoMedico = new Medico(dados);
        GerenciadorRepository.getInstance().adicionarMedico(novoMedico);
    }

    // Atualização de atributos
    public void alterarSenha(String novaSenha) {
        Senha senha = new Senha(novaSenha);
        medico.alterarSenha(senha);
        GerenciadorRepository.getInstance().salvarMedicos();
    }

    public void alterarEmail(String novoEmail) {
        Email email = new Email(novoEmail);
        medico.alterarEmail(email);
        GerenciadorRepository.getInstance().salvarMedicos();
    }

    public void alternarAtivo() {
        if (medico.isAtivo()) {
            medico.desativarUsuario();
            GerenciadorRepository.getInstance().salvarMedicos();
        } else {
            medico.ativarUsuario();
            GerenciadorRepository.getInstance().salvarMedicos();
        }
    }

    public void adicionarHorario(DayOfWeek dia, LocalTime inicio, LocalTime fim) {
        HorarioTrabalho novoHorario = new HorarioTrabalho(dia, inicio, fim);
        medico.adicionarHorario(novoHorario);
        GerenciadorRepository.getInstance().salvarMedicos();
    }

    public void removerHorario(HorarioTrabalho horario) {
        medico.removerHorario(horario);
        GerenciadorRepository.getInstance().salvarMedicos();
    }

    // Buscas
    public List<Medico> buscarMedicos() {
        return GerenciadorRepository.getInstance().getMedicos();
    }

    public List<Medico> filtrarMedicosDisponiveis(Especialidade esp, DayOfWeek dia, LocalTime hora) {
        return GerenciadorRepository.getInstance().buscarMedicosHorario(esp, dia, hora);
    }

    private class GerenciarStatusListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            List<Paciente> todosPacientes = GerenciadorRepository.getInstance().getPacientes();

            view.abrirDialogoGerenciamento(
                    todosPacientes,
                    (paciente) -> {
                        if (paciente.isRecebeVisita()) {
                            paciente.bloquearVisita();
                        } else {
                            paciente.permitirVisita();
                        }
                        GerenciadorRepository.getInstance().salvarPacientes();
                    },
                    (paciente) -> {
                        if (paciente.isHospitalizado()) {
                            paciente.deshospitalizar();
                        } else {
                            paciente.hospitalizar();
                        }
                        GerenciadorRepository.getInstance().salvarPacientes();
                    }
            );
        }
    }

    private class AdicionarHorarioListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                DayOfWeek dia = view.getDiaSelecionado();
                LocalTime inicio = view.getHorarioInicio();
                LocalTime fim = view.getHorarioFim();

                if (inicio.isAfter(fim)) {
                    JOptionPane.showMessageDialog(view, "O horário de início não pode ser depois do horário de fim.");
                    return;
                }

                if (inicio.equals(fim)) {
                    JOptionPane.showMessageDialog(view, "O horário de início e fim não podem ser iguais.");
                    return;
                }

                adicionarHorario(dia, inicio, fim);
                view.atualizarTabela(medico.getAgenda());
                JOptionPane.showMessageDialog(view, "Horário adicionado com sucesso!");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Erro ao adicionar: " + ex.getMessage());
            }
        }
    }

    private class RemoverHorarioListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int linhaSelecionada = view.getLinhaSelecionada();

            if (linhaSelecionada == -1) {
                JOptionPane.showMessageDialog(view, "Selecione um horário na tabela para remover.");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(view, "Tem certeza que deseja remover este horário?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }

            try {
                HorarioTrabalho horarioParaRemover = medico.getAgenda().get(linhaSelecionada);

                removerHorario(horarioParaRemover);

                view.atualizarTabela(medico.getAgenda());
                JOptionPane.showMessageDialog(view, "Horário removido.");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Erro ao remover: " + ex.getMessage());
            }
        }
    }

    private class NavegarAgendaListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            view.atualizarTabela(medico.getAgenda());
        }
    }

    private class SairListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            voltarParaLogin();
        }
    }

    private void voltarParaLogin() {
        LoginView loginView = new LoginView();
        new LoginController(loginView);
        loginView.setVisible(true);
        if (view != null) {
            view.dispose();
        }
    }
}
