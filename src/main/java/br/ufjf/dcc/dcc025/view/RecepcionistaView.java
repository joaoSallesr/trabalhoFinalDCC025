package br.ufjf.dcc.dcc025.view;

import java.awt.BorderLayout; //'*' importa todas as classes públicas desse pacote
import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import br.ufjf.dcc.dcc025.controller.PacienteController;
import br.ufjf.dcc.dcc025.model.dto.DadosPaciente;

public class RecepcionistaView extends JFrame {

    private JPanel painelEsquerdo;
    private JPanel painelCentral;
    private JPanel painelDireito;
    private CardLayout cardLayout; // reutilizar a janela para as diversas opções

    private final PacienteController pacienteController;

    private JButton btnCadastroPaciente;
    private JButton btnStatus;
    private JButton btnListaMedico;
    private JButton btnAgendaMedico;
    private JButton btnConsultarFaltas;
    private JButton btnSair;
    // cadastro paciente:
    private JTextField txtNome;
    private JTextField txtSobrenome;
    private JTextField txtCpf;
    private JTextField txtEmail;
    private JPasswordField txtSenha;
    private JTextField txtTelefone;
    private JTextField txtRua;
    private JTextField txtNumero;
    private JTextField txtCidade;
    private JTextField txtCep;
    private JTextField txtBairro;

    public RecepcionistaView() {
        setTitle("Área Recepcionista");
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        this.pacienteController = new PacienteController();
        ImageIcon image = new ImageIcon("hospital-building.png"); // icone
        this.setIconImage(image.getImage()); // icone

        criarPainelEsquerdo();
        criarPainelCentral();
        criarPainelDireito();

        add(painelEsquerdo, BorderLayout.WEST);
        add(painelCentral, BorderLayout.CENTER);
        add(painelDireito, BorderLayout.EAST);

    }

    private void criarPainelEsquerdo() {
        painelEsquerdo = new JPanel();
        painelEsquerdo.setLayout(new GridLayout(5, 1, 5, 5));
        painelEsquerdo.setBorder(
                BorderFactory.createTitledBorder("Ações"));

        btnCadastroPaciente = new JButton("Cadastrar Paciente");
        btnCadastroPaciente.addActionListener(e -> {
            cardLayout.show(painelCentral, "CADASTRO_PACIENTE");
        });
        btnStatus = new JButton("Conferir Status dos Pacientes");
        btnListaMedico = new JButton("Lista de médicos");
        btnAgendaMedico = new JButton("Verificar agenda dos médicos");
        btnConsultarFaltas = new JButton("Verificar faltas");

        painelEsquerdo.add(btnCadastroPaciente);
        painelEsquerdo.add(btnStatus);
        painelEsquerdo.add(btnListaMedico);
        painelEsquerdo.add(btnAgendaMedico);
        painelEsquerdo.add(btnConsultarFaltas);
    }

    private void criarPainelDireito() {
        painelDireito = new JPanel();
        painelDireito.setLayout(new GridLayout(1, 1, 10, 10));
        painelDireito.setBorder(
                BorderFactory.createTitledBorder("Sistema"));

        btnSair = new JButton("Sair");
        painelDireito.add(btnSair);
    }

    private void criarPainelCentral() {
        cardLayout = new CardLayout();
        painelCentral = new JPanel(cardLayout);
        painelCentral.setBorder(
                BorderFactory.createTitledBorder("Informações"));

        painelCentral.add(criarPainelHome(), "HOME");
        painelCentral.add(criarPainelCadastroPaciente(), "CADASTRO_PACIENTE");

        cardLayout.show(painelCentral, "HOME");

    }

    private JPanel criarPainelHome() {
        JPanel painel = new JPanel(new BorderLayout());

        JLabel label = new JLabel(
                "Selecione uma opção ao lado",
                SwingConstants.CENTER);

        painel.add(label, BorderLayout.CENTER);
        return painel;
    }

    private JPanel criarPainelCadastroPaciente() {
        JPanel painel = new JPanel(new GridLayout(10, 4, 5, 5));
        painel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        txtNome = new JTextField();
        txtSobrenome = new JTextField();
        txtCpf = new JTextField();
        txtEmail = new JTextField();
        txtSenha = new JPasswordField();
        txtTelefone = new JTextField();
        txtRua = new JTextField();
        txtNumero = new JTextField();
        txtCidade = new JTextField();
        txtCep = new JTextField();
        txtBairro = new JTextField();

        JButton btnSalvar = new JButton("Salvar");

        painel.add(new JLabel("Nome:"));
        painel.add(txtNome);

        painel.add(new JLabel("Sobrenome:"));
        painel.add(txtSobrenome);

        painel.add(new JLabel("CPF:"));
        painel.add(txtCpf);

        painel.add(new JLabel("Email:"));
        painel.add(txtEmail);

        painel.add(new JLabel("Senha:"));
        painel.add(txtSenha);

        painel.add(new JLabel("Telefone:"));
        painel.add(txtTelefone);

        painel.add(new JLabel("Rua:"));
        painel.add(txtRua);
        painel.add(new JLabel("Bairro:"));
        painel.add(txtBairro);
        painel.add(new JLabel("Número:"));
        painel.add(txtNumero);
        painel.add(new JLabel("CEP:"));
        painel.add(txtCep);
        painel.add(new JLabel("Cidade:"));
        painel.add(txtCidade);
        painel.add(new JLabel());
        painel.add(btnSalvar);

        btnSalvar.addActionListener(e -> salvarPaciente());

        return painel;
    }

    private void salvarPaciente() {
        try {
            String nome = txtNome.getText();
            String sobrenome = txtSobrenome.getText();
            String cpf = txtCpf.getText();
            String senha = new String(txtSenha.getPassword());
            String email = txtEmail.getText();
            String telefone = txtTelefone.getText();
            String cep = txtCep.getText();
            String rua = txtRua.getText();
            String bairro = txtBairro.getText();
            String numeroString = txtNumero.getText();
            String cidade = txtCidade.getText();
            int numeroCasaInt;

            try {
                numeroCasaInt = Integer.parseInt(numeroString);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "O campo 'Número' deve conter apenas dígitos.");
                return;
            }

            DadosPaciente dadosPaciente = new DadosPaciente(
                    nome, sobrenome, cpf,
                    senha, email, telefone,
                    cep, rua, bairro, cidade, numeroCasaInt
            );
            pacienteController.cadastrarPaciente(dadosPaciente);

            JOptionPane.showMessageDialog(
                    this,
                    "Paciente cadastrado com sucesso!");

            limparCampos();

        } catch (HeadlessException | NumberFormatException e) {
            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limparCampos() {
        txtNome.setText("");
        txtSobrenome.setText("");
        txtCpf.setText("");
        txtSenha.setText("");
        txtEmail.setText("");
        txtTelefone.setText("");
        txtCep.setText("");
        txtRua.setText("");
        txtBairro.setText("");
        txtNumero.setText("");
        txtCidade.setText("");
    }

}
