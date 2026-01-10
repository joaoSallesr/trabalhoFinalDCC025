package br.ufjf.dcc.dcc025.controller;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import br.ufjf.dcc.dcc025.model.Paciente;
import br.ufjf.dcc.dcc025.model.dto.DadosPaciente;
import br.ufjf.dcc.dcc025.model.repository.GerenciadorRepository;
import br.ufjf.dcc.dcc025.model.valueobjects.Contato;
import br.ufjf.dcc.dcc025.model.valueobjects.Email;
import br.ufjf.dcc.dcc025.model.valueobjects.Endereco;
import br.ufjf.dcc.dcc025.model.valueobjects.Senha;
import br.ufjf.dcc.dcc025.view.LoginView;
import br.ufjf.dcc.dcc025.view.PacienteView;

public class PacienteController {

    private Paciente paciente;
    private PacienteView view;

    public PacienteController(Paciente paciente, PacienteView view) {
        this.paciente = paciente;
        this.view = view;
        if (this.view != null) {
            this.view.addSairListener(new SairListener());
            this.view.addVerStatusListener(e -> mostrarStatusInternacao());
            this.view.addVerDadosListener(e -> mostrarMeusDados());
        }

    }

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

    private void mostrarStatusInternacao() {

        List<Paciente> hospitalizados = GerenciadorRepository.getInstance().buscarHospitalizados();

        if (hospitalizados.isEmpty()) {
            JOptionPane.showMessageDialog(
                    view,
                    "Não há pacientes hospitalizados no momento.");
            return;
        }

        String[] colunas = { "Nome", "Pode receber visitas?" };
        DefaultTableModel model = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (Paciente p : hospitalizados) {
            model.addRow(new Object[] {
                    p.getNome().getNome() + " " + p.getNome().getSobrenome(),
                    p.isRecebeVisita() ? "Apto" : "Não apto"
            });
        }

        JTable tabela = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(tabela);

        view.atualizarPainelCentral(scrollPane);
    }

    private void mostrarMeusDados() {

        JPanel painel = new JPanel(new GridLayout(0, 2, 10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField txtEmail = new JTextField(paciente.getEmail().getEmail());
        JPasswordField txtSenha = new JPasswordField(paciente.getSenha().getSenha());
        JTextField txtContato = new JTextField(paciente.getContato().getNumero());

        JTextField txtCep = new JTextField(paciente.getEndereco().getCEP());
        JTextField txtRua = new JTextField(paciente.getEndereco().getRua());
        JTextField txtBairro = new JTextField(paciente.getEndereco().getBairro());
        JTextField txtCidade = new JTextField(paciente.getEndereco().getCidade());
        JTextField txtNumero = new JTextField(
                String.valueOf(paciente.getEndereco().getNumeroCasa()));

        JButton btnSalvar = new JButton("Salvar alterações");

        painel.add(new JLabel("Email:"));
        painel.add(txtEmail);

        painel.add(new JLabel("Senha:"));
        painel.add(txtSenha);

        painel.add(new JLabel("Contato:"));
        painel.add(txtContato);

        painel.add(new JLabel("CEP:"));
        painel.add(txtCep);

        painel.add(new JLabel("Rua:"));
        painel.add(txtRua);

        painel.add(new JLabel("Bairro:"));
        painel.add(txtBairro);

        painel.add(new JLabel("Cidade:"));
        painel.add(txtCidade);

        painel.add(new JLabel("Número:"));
        painel.add(txtNumero);

        painel.add(new JLabel());
        painel.add(btnSalvar);

        btnSalvar.addActionListener(e -> {
            try {
                alterarEmail(paciente, txtEmail.getText());
                alterarSenha(paciente, new String(txtSenha.getPassword()));
                alterarContato(paciente, txtContato.getText());

                int numeroCasa = Integer.parseInt(txtNumero.getText());

                alterarEndereco(
                        paciente,
                        txtCep.getText(),
                        txtRua.getText(),
                        txtBairro.getText(),
                        txtCidade.getText(),
                        numeroCasa);

                JOptionPane.showMessageDialog(
                        view,
                        "Dados atualizados com sucesso!");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(
                        view,
                        "Erro ao salvar dados: " + ex.getMessage());
            }
        });

        view.atualizarPainelCentral(painel);
    }

    // Buscas
    public List<Paciente> buscarTodosPacientes() {
        return GerenciadorRepository.getInstance().getPacientes();
    }

    public List<Paciente> buscarPacientesHospitalizados() {
        return GerenciadorRepository.getInstance().buscarHospitalizados();
    }

    public List<Paciente> buscarRecebemVisitas() {
        return GerenciadorRepository.getInstance().buscarRecebemVisitas();
    }

    public boolean existePacienteVisitavel(String nome, String sobrenome) {
        return GerenciadorRepository.getInstance().existePacienteVisitavel(nome, sobrenome);
    }

    private class SairListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.dispose();

            LoginView loginView = new LoginView();
            new LoginController(loginView);
            loginView.setVisible(true);
        }
    }

}
