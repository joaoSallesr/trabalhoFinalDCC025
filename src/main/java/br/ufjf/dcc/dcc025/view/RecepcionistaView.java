package br.ufjf.dcc.dcc025.view;

import javax.swing.*; //'*' importa todas as classes públicas desse pacote

import java.awt.*;

public class RecepcionistaView extends JFrame {

    private JPanel painelEsquerdo;
    private JPanel painelCentral;
    private JPanel painelDireito;
    private CardLayout cardLayout; // reutilizar a janela para as diversas opções

    private JButton btnCadastroPaciente;
    private JButton btnStatus;
    private JButton btnListaMedico;
    private JButton btnAgendaMedico;
    private JButton btnConsultarFaltas;
    private JButton btnSair;

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
        JPanel painel = new JPanel(new GridLayout(5, 2, 10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JTextField txtNome = new JTextField();
        JTextField txtCpf = new JTextField();
        JTextField txtEmail = new JTextField();
        JPasswordField txtSenha = new JPasswordField();
        JButton btnSalvar = new JButton("Salvar");

        painel.add(new JLabel("Nome:"));
        painel.add(txtNome);

        painel.add(new JLabel("CPF:"));
        painel.add(txtCpf);

        painel.add(new JLabel("Email:"));
        painel.add(txtEmail);

        painel.add(new JLabel("Senha:"));
        painel.add(txtSenha);

        painel.add(new JLabel());
        painel.add(btnSalvar);

        return painel;
    }

}
