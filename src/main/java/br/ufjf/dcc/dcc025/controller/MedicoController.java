package br.ufjf.dcc.dcc025.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableModel;

import br.ufjf.dcc.dcc025.model.DocumentoMedico;
import br.ufjf.dcc.dcc025.model.Medico;
import br.ufjf.dcc.dcc025.model.Paciente;
import br.ufjf.dcc.dcc025.model.dto.DadosMedico;
import br.ufjf.dcc.dcc025.model.repository.GerenciadorRepository;
import br.ufjf.dcc.dcc025.model.valueobjects.Consulta;
import br.ufjf.dcc.dcc025.model.valueobjects.Email;
import br.ufjf.dcc.dcc025.model.valueobjects.Especialidade;
import br.ufjf.dcc.dcc025.model.valueobjects.EstadoConsulta;
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
            this.view.atualizarTabela(medico.getAgenda());
            this.view.setVisible(true);
            this.view.addSairListener(new SairListener());
            this.view.addGerenciarStatusListener(new GerenciarStatusListener());
            this.view.addAdicionarHorarioListener(new AdicionarHorarioListener());
            this.view.addRemoverHorarioListener(new RemoverHorarioListener());
            this.view.addNavegarAgendaListener(new NavegarAgendaListener());
            this.view.addEmitirDocumentoListener(new EmitirDocumentoListener());

            this.view.addVerConsultasListener(new ConsultasHojeListener());
            this.view.addConsultasDiaListener(new ConsultasHojeListener());

            this.view.addCarregarConsultasListener(new FiltrarConsultasListener());
            this.view.addFinalizarConsultaListener(new FinalizarConsultaListener());

            this.view.addVerHistoricoListener(new HistoricoListener());

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

    public void alternarAtivo(Medico medicoAlvo) {
        if (medicoAlvo.isAtivo()) {
            medicoAlvo.desativarUsuario();
            GerenciadorRepository.getInstance().salvarMedicos();
        } else {
            medicoAlvo.ativarUsuario();
            GerenciadorRepository.getInstance().salvarMedicos();
        }
    }

    // Buscas
    public List<Medico> buscarMedicos() {
        return GerenciadorRepository.getInstance().getMedicos();
    }

    public List<Medico> filtrarMedicosDisponiveis(Especialidade esp, DayOfWeek dia, LocalTime hora) {
        return GerenciadorRepository.getInstance().buscarMedicosHorario(esp, dia, hora);
    }

    // Gerenciador de status dos pacientes
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

    // Gerenciador de horários de trabalho
    private class AdicionarHorarioListener implements ActionListener {

        @Override
        @SuppressWarnings("UseSpecificCatch")
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
        @SuppressWarnings("UseSpecificCatch")
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

    private void adicionarHorario(DayOfWeek dia, LocalTime inicio, LocalTime fim) {
        HorarioTrabalho novoHorario = new HorarioTrabalho(dia, inicio, fim);
        medico.adicionarHorario(novoHorario);
        GerenciadorRepository.getInstance().salvarMedicos();
    }

    private void removerHorario(HorarioTrabalho horario) {
        medico.removerHorario(horario);
        GerenciadorRepository.getInstance().salvarMedicos();
    }

    private class NavegarAgendaListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            view.atualizarTabela(medico.getAgenda());
        }
    }

    // Gerenciador de consultas
    private class HistoricoListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            mostrarHistorico();
        }
    }

    private void mostrarHistorico() {
        List<Consulta> todas = new ArrayList<>(medico.getConsultas());
        List<Consulta> efetuadas = new ArrayList<>();

        for (Consulta c : todas) {
            if (c.getEstadoConsulta() == EstadoConsulta.EFETUADA) {
                efetuadas.add(c);
            }
        }

        if (efetuadas.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Nenhuma consulta efetuada encontrada no histórico.");
        }
        efetuadas.sort((c1, c2) -> {
            int cmp = c2.getDiaConsulta().compareTo(c1.getDiaConsulta());
            if (cmp != 0) {
                return cmp;
            }
            return c2.getHorarioConsulta().compareTo(c1.getHorarioConsulta());
        });

        String[] colunas = {"Data", "Hora", "Paciente", "Avaliação"};
        DefaultTableModel model = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (Consulta c : efetuadas) {
            model.addRow(new Object[]{
                c.getDiaConsulta(),
                c.getHorarioConsulta(),
                c.getNomePacienteDisplay(),
                c.getAvaliacaoSaude()
            });
        }

        JTable tabela = new JTable(model);
        JScrollPane scroll = new JScrollPane(tabela);

        view.atualizarPainelCentral(scroll);
    }

    private class FiltrarConsultasListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            DayOfWeek dia = view.getDiaFiltroConsulta();
            carregarConsultasDoDia(dia);
        }
    }

    private void carregarConsultasDoDia(DayOfWeek dia) {
        List<Consulta> todasConsultas = medico.getConsultasDoDia(dia);

        List<Consulta> consultasFiltradas = new ArrayList<>();

        for (Consulta c : todasConsultas) {
            if (c.getEstadoConsulta() == EstadoConsulta.MARCADA) {
                consultasFiltradas.add(c);
            }

        }

        view.atualizarListaConsultas(consultasFiltradas);
    }

    private class ConsultasHojeListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            DayOfWeek hoje = LocalDate.now().getDayOfWeek();
            carregarConsultasDoDia(hoje);
        }
    }

    private class FinalizarConsultaListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            int linha = view.getLinhaConsultaSelecionada();
            if (linha == -1) {
                JOptionPane.showMessageDialog(view, "Selecione uma consulta.");
                return;
            }

            DayOfWeek dia = view.getDiaFiltroConsulta();
            Consulta consultaAntiga = medico.getConsultasDoDia(dia).get(linha);

            if (consultaAntiga.getEstadoConsulta() != EstadoConsulta.MARCADA) {
                JOptionPane.showMessageDialog(view, "Esta consulta já foi finalizada ou cancelada.");
                return;
            }

            SpinnerNumberModel model = new SpinnerNumberModel(5, 1, 10, 1);
            JSpinner spinner = new JSpinner(model);

            int opcao = JOptionPane.showConfirmDialog(
                    view,
                    spinner,
                    "Avaliação da saúde (1 a 10)",
                    JOptionPane.OK_CANCEL_OPTION
            );

            if (opcao != JOptionPane.OK_OPTION) {
                return;
            }

            int nota = (int) spinner.getValue();

            Consulta consultaNova = consultaAntiga.finalizarConsulta(nota);

            medico.removerConsulta(consultaAntiga);
            medico.adicionarConsulta(consultaNova);

            Paciente paciente = consultaAntiga.getPaciente();

            if (paciente == null) {
                List<Paciente> todosPacientes = GerenciadorRepository.getInstance().getPacientes();
                String nomeAlvo = consultaAntiga.getNomePacienteDisplay();
                for (Paciente p : todosPacientes) {
                    if ((p.getNome().getNome() + " " + p.getNome().getSobrenome()).equals(nomeAlvo)) {
                        paciente = p;
                        break;
                    }
                }
            }

            if (paciente != null) {

                paciente.removerConsulta(consultaAntiga);
                paciente.adicionarConsulta(consultaNova);
            }

            GerenciadorRepository.getInstance().salvarMedicos();
            GerenciadorRepository.getInstance().salvarPacientes();

            carregarConsultasDoDia(dia);

            JOptionPane.showMessageDialog(view, "Consulta finalizada com sucesso.");
        }
    }

    // Gerenciador de emissão de documento
    private class EmitirDocumentoListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            emitirDocumento();
        }
    }

    private void emitirDocumento() {

        List<Paciente> pacientes = GerenciadorRepository.getInstance().getPacientes();

        if (pacientes.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Não há pacientes cadastrados.");
            return;
        }

        Paciente paciente = (Paciente) JOptionPane.showInputDialog(
                view,
                "Selecione o paciente:",
                "Emitir Documento",
                JOptionPane.QUESTION_MESSAGE,
                null,
                pacientes.toArray(),
                pacientes.get(0)
        );

        if (paciente == null) {
            return;
        }

        JTextArea areaTexto = new JTextArea(10, 40);
        JScrollPane scroll = new JScrollPane(areaTexto);

        int opcao = JOptionPane.showConfirmDialog(
                view,
                scroll,
                "Digite o documento médico",
                JOptionPane.OK_CANCEL_OPTION
        );

        if (opcao != JOptionPane.OK_OPTION) {
            return;
        }

        String texto = areaTexto.getText();

        if (texto.isBlank()) {
            JOptionPane.showMessageDialog(view, "Documento não pode estar vazio.");
            return;
        }

        DocumentoMedico documento = new DocumentoMedico(
                medico.getNome().getNome() + " " + medico.getNome().getSobrenome(),
                paciente.getNome().getNome() + " " + paciente.getNome().getSobrenome(),
                texto
        );

        paciente.adicionarDocumento(documento);
        GerenciadorRepository.getInstance().salvarPacientes();

        JOptionPane.showMessageDialog(view, "Documento emitido com sucesso.");
    }

    // Sair da janela - voltar para tela de login
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
