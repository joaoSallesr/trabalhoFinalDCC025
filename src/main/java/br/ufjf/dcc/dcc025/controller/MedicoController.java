package br.ufjf.dcc.dcc025.controller;

import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import br.ufjf.dcc.dcc025.model.Medico;
import br.ufjf.dcc.dcc025.model.Paciente;
import br.ufjf.dcc.dcc025.model.dto.DadosMedico;
import br.ufjf.dcc.dcc025.model.repository.GerenciadorRepository;
import br.ufjf.dcc.dcc025.model.valueobjects.Email;
import br.ufjf.dcc.dcc025.model.valueobjects.Senha;
import br.ufjf.dcc.dcc025.view.MedicoView;
import br.ufjf.dcc.dcc025.view.LoginView;
import br.ufjf.dcc.dcc025.controller.LoginController;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MedicoController {

    private Medico medico;
    private MedicoView view;

    public MedicoController(Medico medico, MedicoView view) {
        this.medico = medico;
        this.view = view;

        if (view == null) {
            throw new IllegalArgumentException("MedicoView não pode ser null");
        }
        // só adiciona listeners se houver view - evitar npe
        if (this.view != null) {
            this.view.addSairListener(new SairListener());
            this.view.addGerenciarStatusListener(new GerenciarStatusListener());
        }
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

    public void alternarAtivo(Medico medico) {
        if (medico.isAtivo()) {
            medico.desativarUsuario();
            GerenciadorRepository.getInstance().salvarMedicos();
        } else {
            medico.ativarUsuario();
            GerenciadorRepository.getInstance().salvarMedicos();
        }
    }

    private class GerenciarStatusListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            mostrarPacientesParaGerenciamento();
        }
    }

    private void mostrarPacientesParaGerenciamento() {
        List<Paciente> pacientes = GerenciadorRepository
                .getInstance()
                .buscarHospitalizados();

        String[] colunas = { "Nome", "CPF", "Hospitalizado", "Pode receber visita" };
        DefaultTableModel model = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (Paciente p : pacientes) {
            model.addRow(new Object[] {
                    p.getNome().getNome() + " " + p.getNome().getSobrenome(),
                    p.getCPF().getCPF(),
                    p.isHospitalizado() ? "Sim" : "Não",
                    p.isRecebeVisita() ? "Apto" : "Não apto"
            });
        }

        JTable tabela = new JTable(model);
        JScrollPane scroll = new JScrollPane(tabela);

        JButton btnAlternar = new JButton("Alternar status de visita");

        btnAlternar.addActionListener(ev -> {
            int linha = tabela.getSelectedRow();
            if (linha == -1) {
                JOptionPane.showMessageDialog(view, "Selecione um paciente.");
                return;
            }

            Paciente p = pacientes.get(linha);

            if (!p.isHospitalizado()) {
                JOptionPane.showMessageDialog(view, "Paciente não está hospitalizado.");
                return;
            }

            if (p.isRecebeVisita()) {
                p.bloquearVisita();
            } else {
                p.permitirVisita();
            }

            GerenciadorRepository.getInstance().salvarPacientes();

            model.setValueAt(
                    p.isRecebeVisita() ? "Apto" : "Não apto",
                    linha,
                    3);
        });

        JDialog dialog = new JDialog(view, "Status dos Pacientes", true);
        dialog.setLayout(new BorderLayout());
        dialog.add(scroll, BorderLayout.CENTER);

        JPanel botoes = new JPanel();
        botoes.add(btnAlternar);

        dialog.add(botoes, BorderLayout.SOUTH);
        dialog.setSize(800, 500);
        dialog.setLocationRelativeTo(view);
        dialog.setVisible(true);
    }

    // Buscas
    public List<Medico> buscarMedicos() {
        return GerenciadorRepository.getInstance().getMedicos();
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
        view.dispose();
    }

}
