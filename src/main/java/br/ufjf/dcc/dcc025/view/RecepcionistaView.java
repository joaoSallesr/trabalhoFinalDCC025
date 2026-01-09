package br.ufjf.dcc.dcc025.view;

import javax.swing.*; //'*' importa todas as classes públicas desse pacote
import java.awt.*;

import br.ufjf.dcc.dcc025.model.Paciente;
import br.ufjf.dcc.dcc025.model.valueobjects.Nome;
import br.ufjf.dcc.dcc025.model.valueobjects.CPF;
import br.ufjf.dcc.dcc025.model.valueobjects.Email;
import br.ufjf.dcc.dcc025.model.valueobjects.Senha;
import br.ufjf.dcc.dcc025.model.valueobjects.Contato;
import br.ufjf.dcc.dcc025.model.valueobjects.Endereco;

import br.ufjf.dcc.dcc025.controller.RecepcionistaController;

public class RecepcionistaView extends JFrame {

    private JPanel painelEsquerdo;
    private JPanel painelCentral;
    private JPanel painelDireito;
    private CardLayout cardLayout; // reutilizar a janela para as diversas opções

    private RecepcionistaController controller;

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
    private JTextField txtNumeroCasa;

    public RecepcionistaView() {
        setTitle("Área Recepcionista");
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
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
        txtNumeroCasa = new JTextField();

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
            Nome nome = new Nome(
                    txtNome.getText(),
                    txtSobrenome.getText());

            CPF cpf = new CPF(txtCpf.getText());
            Email email = new Email(txtEmail.getText());
            Contato contato = new Contato(txtTelefone.getText());
            Senha senha = new Senha(new String(txtSenha.getPassword()));

            Endereco endereco = new Endereco(
                    txtCep.getText(),
                    txtRua.getText(),
                    txtBairro.getText(),
                    txtCidade.getText(),
                    Integer.parseInt(txtNumeroCasa.getText()));

            Paciente paciente = new Paciente(
                    nome,
                    cpf,
                    email,
                    contato,
                    endereco);

            controller.cadastrarPaciente(paciente);

            JOptionPane.showMessageDialog(
                    this,
                    "Paciente cadastrado com sucesso!");

            limparCampos();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limparCampos() {
        txtNome.setText("");
        txtCpf.setText("");
        txtEmail.setText("");
        txtSenha.setText("");
        txtTelefone.setText("");
        txtRua.setText("");
        txtNumero.setText("");
        txtCidade.setText("");
    }

}
