package br.ufjf.dcc.dcc025.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import br.ufjf.dcc.dcc025.model.Recepcionista;
import br.ufjf.dcc.dcc025.model.dto.DadosRecepcionista;
import br.ufjf.dcc.dcc025.model.repository.GerenciadorRepository;
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

    private class VerificarFaltasListener implements java.awt.event.ActionListener {

        @Override
        public void actionPerformed(java.awt.event.ActionEvent e) {
            // monta lista de todas as consultas marcadas nos pacientes
            java.util.List<br.ufjf.dcc.dcc025.model.Paciente> pacientes = br.ufjf.dcc.dcc025.model.repository.GerenciadorRepository.getInstance().getPacientes();

            String[] colunas = {"Dia", "Hora", "Paciente", "Médico", "Especialidade"};
            javax.swing.table.DefaultTableModel model = new javax.swing.table.DefaultTableModel(colunas, 0) {
                @Override
                public boolean isCellEditable(int row, int column) { return false; }
            };

            java.util.List<java.util.AbstractMap.SimpleEntry<br.ufjf.dcc.dcc025.model.Paciente, br.ufjf.dcc.dcc025.model.valueobjects.Consulta>> mapping = new java.util.ArrayList<>();

            for (br.ufjf.dcc.dcc025.model.Paciente p : pacientes) {
                for (br.ufjf.dcc.dcc025.model.valueobjects.Consulta c : p.getConsultas()) {
                    if (c.getEstadoConsulta() == br.ufjf.dcc.dcc025.model.valueobjects.EstadoConsulta.MARCADA) {
                        model.addRow(new Object[] { c.getDiaConsulta(), c.getHorarioConsulta(), p.getNome().getNome() + " " + p.getNome().getSobrenome(), c.getNomeMedicoDisplay(), c.getEspecialidadeDisplay() });
                        mapping.add(new java.util.AbstractMap.SimpleEntry<>(p, c));
                    }
                }
            }

            javax.swing.JTable tabela = new javax.swing.JTable(model);
            tabela.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
            javax.swing.JScrollPane scroll = new javax.swing.JScrollPane(tabela);

            javax.swing.JButton btnMarcarFalta = new javax.swing.JButton("Marcar falta (Ausente)");

            btnMarcarFalta.addActionListener(ev -> {
                int row = tabela.getSelectedRow();
                if (row == -1) {
                    javax.swing.JOptionPane.showMessageDialog(view, "Selecione uma consulta para marcar falta.");
                    return;
                }

                var entry = mapping.get(row);
                br.ufjf.dcc.dcc025.model.Paciente paciente = entry.getKey();
                br.ufjf.dcc.dcc025.model.valueobjects.Consulta consulta = entry.getValue();

                int confirm = javax.swing.JOptionPane.showConfirmDialog(view, "Confirmar marcação de falta para " + paciente.getNome().getNome() + " " + paciente.getNome().getSobrenome() + "?", "Confirmar", javax.swing.JOptionPane.YES_NO_OPTION);
                if (confirm != javax.swing.JOptionPane.YES_OPTION) return;

                // Atualiza consulta para AUSENTE
                br.ufjf.dcc.dcc025.model.valueobjects.Consulta consultaAtualizada = consulta.novoEstado("Ausente");

                paciente.atualizarConsulta(consulta, consultaAtualizada);

                // Atualiza médico correspondente e adiciona notificação
                java.util.List<br.ufjf.dcc.dcc025.model.Medico> medicos = br.ufjf.dcc.dcc025.model.repository.GerenciadorRepository.getInstance().getMedicos();
                String nomeMedico = consulta.getNomeMedicoDisplay();
                boolean encontradoMedico = false;
                String alvo = nomeMedico == null ? "" : nomeMedico.trim().toLowerCase();
                for (br.ufjf.dcc.dcc025.model.Medico m : medicos) {
                    String nomeCompleto = m.getNome() == null ? "" : m.getNome().toString().trim().toLowerCase();
                    String sobrenome = m.getNome() == null ? "" : m.getNome().getSobrenome().trim().toLowerCase();
                    if (nomeCompleto.equals(alvo) || sobrenome.equals(alvo) || nomeCompleto.contains(alvo) || sobrenome.contains(alvo)) {
                        m.atualizarConsulta(consulta, consultaAtualizada);
                        String mensagem = "Paciente " + paciente.getNome().getNome() + " " + paciente.getNome().getSobrenome() + " faltou à consulta em " + consulta.getDiaConsulta() + " " + consulta.getHorarioConsulta();
                        m.adicionarNotificacao(mensagem);
                        encontradoMedico = true;
                        break;
                    }
                }

                if (!encontradoMedico) {
                    javax.swing.JOptionPane.showMessageDialog(view, "Médico não encontrado para notificar: " + nomeMedico);
                }

                br.ufjf.dcc.dcc025.model.repository.GerenciadorRepository.getInstance().salvarPacientes();
                br.ufjf.dcc.dcc025.model.repository.GerenciadorRepository.getInstance().salvarMedicos();

                javax.swing.JOptionPane.showMessageDialog(view, "Falta marcada e médico notificado.");
                ((javax.swing.JDialog) javax.swing.SwingUtilities.getWindowAncestor(view)).dispose();
            });

            javax.swing.JDialog dialog = new javax.swing.JDialog(view, "Verificar faltas", true);
            dialog.setLayout(new java.awt.BorderLayout());
            dialog.add(scroll, java.awt.BorderLayout.CENTER);
            javax.swing.JPanel painelBotoes = new javax.swing.JPanel(new java.awt.FlowLayout());
            painelBotoes.add(btnMarcarFalta);
            dialog.add(painelBotoes, java.awt.BorderLayout.SOUTH);
            dialog.setSize(800, 500);
            dialog.setLocationRelativeTo(view);
            dialog.setVisible(true);
        }
    }
}
