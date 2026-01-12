package br.ufjf.dcc.dcc025.controller;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import br.ufjf.dcc.dcc025.model.Medico;
import br.ufjf.dcc.dcc025.model.Paciente;
import br.ufjf.dcc.dcc025.model.Recepcionista;
import br.ufjf.dcc.dcc025.model.dto.DadosRecepcionista;
import br.ufjf.dcc.dcc025.model.repository.GerenciadorRepository;
import br.ufjf.dcc.dcc025.model.valueobjects.Consulta;
import br.ufjf.dcc.dcc025.model.valueobjects.EstadoConsulta;
import br.ufjf.dcc.dcc025.view.LoginView;
import br.ufjf.dcc.dcc025.view.RecepcionistaView;

public class RecepcionistaController {

    private final Recepcionista recepcionista;
    private final RecepcionistaView view;

    public RecepcionistaController(Recepcionista recepcionista, RecepcionistaView view) {
        this.recepcionista = recepcionista;
        this.view = view;

        if (this.view != null) {
            this.view.addSairListener(new SairListener());
            this.view.addConsultarFaltasListener(new VerificarFaltasListener());
        }
    }

    public void cadastrarRecepcionista(DadosRecepcionista dados) {
        Recepcionista novoRecepcionista = new Recepcionista(dados);
        GerenciadorRepository.getInstance().adicionarRecepcionista(novoRecepcionista);
    }

    private class VerificarFaltasListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JDialog dialog = new JDialog(view, "Verificar faltas", true);

            List<Paciente> pacientes = GerenciadorRepository.getInstance().getPacientes();

            String[] colunas = {"Dia", "Hora", "Paciente", "Médico", "Especialidade"};

            DefaultTableModel model = new DefaultTableModel(colunas, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            List<AbstractMap.SimpleEntry<Paciente, Consulta>> mapping = new ArrayList<>();

            for (Paciente p : pacientes) {
                for (Consulta c : p.getConsultas()) {
                    if (c.getEstadoConsulta() == EstadoConsulta.MARCADA) {
                        model.addRow(new Object[]{
                            c.getDiaConsulta(),
                            c.getHorarioConsulta(),
                            p.getNome().getNome() + " " + p.getNome().getSobrenome(),
                            c.getNomeMedicoDisplay(),
                            c.getEspecialidadeDisplay()
                        });
                        mapping.add(new AbstractMap.SimpleEntry<>(p, c));
                    }
                }
            }

            JTable tabela = new JTable(model);
            tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            JScrollPane scroll = new JScrollPane(tabela);

            JButton btnMarcarFalta = new JButton("Marcar falta (Ausente)");
            JButton btnFechar = new JButton("Fechar");

            btnFechar.addActionListener(ev -> dialog.dispose());

            btnMarcarFalta.addActionListener(ev -> {
                int row = tabela.getSelectedRow();
                if (row == -1) {
                    JOptionPane.showMessageDialog(dialog, "Selecione uma consulta para marcar falta.");
                    return;
                }

                var entry = mapping.get(row);
                Paciente paciente = entry.getKey();
                Consulta consulta = entry.getValue();

                int confirm = JOptionPane.showConfirmDialog(dialog,
                        "Confirmar marcação de falta para " + paciente.getNome().getNome() + " " + paciente.getNome().getSobrenome() + "?",
                        "Confirmar",
                        JOptionPane.YES_NO_OPTION);

                if (confirm != JOptionPane.YES_OPTION) {
                    return;
                }

                Consulta consultaAtualizada = consulta.novoEstado("Ausente");

                paciente.atualizarConsulta(consulta, consultaAtualizada);

                List<Medico> medicos = GerenciadorRepository.getInstance().getMedicos();
                String nomeMedico = consulta.getNomeMedicoDisplay();
                boolean encontradoMedico = false;

                String alvo = nomeMedico == null ? "" : nomeMedico.trim().toLowerCase();

                for (Medico m : medicos) {
                    String nomeCompleto = m.getNome() == null ? "" : m.getNome().toString().trim().toLowerCase();
                    String sobrenome = m.getNome() == null ? "" : m.getNome().getSobrenome().trim().toLowerCase();

                    if (nomeCompleto.equals(alvo) || sobrenome.equals(alvo) || nomeCompleto.contains(alvo) || sobrenome.contains(alvo)) {
                        m.atualizarConsulta(consulta, consultaAtualizada);

                        String mensagem = "Paciente " + paciente.getNome().getNome() + " " + paciente.getNome().getSobrenome()
                                + " faltou à consulta em " + consulta.getDiaConsulta() + " " + consulta.getHorarioConsulta();
                        m.adicionarNotificacao(mensagem);

                        encontradoMedico = true;
                        break;
                    }
                }

                if (!encontradoMedico) {
                    System.out.println("Aviso: Médico não encontrado para notificar: " + nomeMedico);
                }

                GerenciadorRepository.getInstance().salvarPacientes();
                GerenciadorRepository.getInstance().salvarMedicos();

                JOptionPane.showMessageDialog(dialog, "Falta marcada e médico notificado.");

                dialog.dispose();
            });
            dialog.setLayout(new BorderLayout());
            dialog.add(scroll, BorderLayout.CENTER);

            JPanel painelBotoes = new JPanel(new FlowLayout());
            painelBotoes.add(btnMarcarFalta);
            painelBotoes.add(btnFechar);

            dialog.add(painelBotoes, BorderLayout.SOUTH);
            dialog.setSize(800, 500);
            dialog.setLocationRelativeTo(view);
            dialog.setVisible(true);
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
