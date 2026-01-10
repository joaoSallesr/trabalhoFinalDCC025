package br.ufjf.dcc.dcc025.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class LoginView extends JFrame {

    // Tamanho da janela
    private static final int JANELA_LARGURA = 500;
    private static final int JANELA_ALTURA = 400;

    // Cor de fundo da tela
    private static final Color COR_FUNDO = new Color(176, 196, 222);

    // Fonte padrão dos textos
    private static final Font FONTE_PADRAO = new Font("Arial", Font.PLAIN, 16);
    private static final Font FONTE_TITULO = new Font("Arial", Font.BOLD, 50);

    // Espaçamentos
    private static final int ESPACAMENTO_FORM_VERTICAL = 10;   // espaço entre usuário e senha
    private static final int ESPACAMENTO_FORM_HORIZONTAL = 10; // espaço entre label e campo
    private static final int MARGEM_LATERAL = 120;             // margem esquerda/direita
    private static final int MARGEM_VERTICAL = 60;             // margem topo/baixo do formulário

    // btão
    private static final Dimension TAMANHO_BOTAO = new Dimension(120, 40);


    //componentes da tela
    private JLabel labelTitulo;
    private JLabel labelUsuario;
    private JLabel labelSenha;

    private JTextField campoUsuario;
    private JPasswordField campoSenha;

    private JButton botaoLogin;


    //construtor
    public LoginView() {
        configurarJanela();
        montarTela();
        setVisible(true);
    }


    //configurações da janela
    private void configurarJanela() {
        setTitle("www.Hospital.com.br/Login");
        setSize(JANELA_LARGURA, JANELA_ALTURA);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // centraliza na tela
    }


    //layout da janela
    private void montarTela() {
        setLayout(new BorderLayout());

        add(criarPainelTitulo(), BorderLayout.NORTH);
        add(criarPainelFormulario(), BorderLayout.CENTER);
        add(criarPainelBotao(), BorderLayout.SOUTH);
        
    }


    //painel do título
    private JPanel criarPainelTitulo() {
        JPanel painelTitulo = new JPanel();
        painelTitulo.setBackground(COR_FUNDO);

        painelTitulo.setBorder(
        BorderFactory.createEmptyBorder(5, 0, 1, 0)
        );
        labelTitulo = new JLabel("Hospital", SwingConstants.CENTER);
        labelTitulo.setFont(FONTE_TITULO);

        painelTitulo.add(labelTitulo);
        return painelTitulo;
    }


     //painel do formulário
    private JPanel criarPainelFormulario() {

        // Painel que contém as linhas do formulário
        JPanel painelFormulario = new JPanel(
                new GridLayout(2, 1, 0, ESPACAMENTO_FORM_VERTICAL)
        );
        painelFormulario.setBackground(COR_FUNDO);
        painelFormulario.setBorder(
                BorderFactory.createEmptyBorder(
                        MARGEM_VERTICAL,
                        MARGEM_LATERAL,
                        MARGEM_VERTICAL,
                        MARGEM_LATERAL
                )
        );

        painelFormulario.add(criarLinhaUsuario());
        painelFormulario.add(criarLinhaSenha());

        return painelFormulario;
    }


    //linha do usuário
    private JPanel criarLinhaUsuario() {
        JPanel linhaUsuario = new JPanel(
                new FlowLayout(FlowLayout.LEFT, ESPACAMENTO_FORM_HORIZONTAL, 5)
        );
        linhaUsuario.setBackground(COR_FUNDO);

        labelUsuario = new JLabel("Usuário:");
        labelUsuario.setFont(FONTE_PADRAO);

        campoUsuario = new JTextField(28);
        campoUsuario.setFont(FONTE_PADRAO);

        linhaUsuario.add(labelUsuario);
        linhaUsuario.add(campoUsuario);

        return linhaUsuario;
    }


    // linha da senha
    private JPanel criarLinhaSenha() {
        JPanel linhaSenha = new JPanel(
                new FlowLayout(FlowLayout.LEFT, ESPACAMENTO_FORM_HORIZONTAL, 5)
        );
        linhaSenha.setBackground(COR_FUNDO);

        labelSenha = new JLabel("Senha:");
        labelSenha.setFont(FONTE_PADRAO);

        campoSenha = new JPasswordField(28);
        campoSenha.setFont(FONTE_PADRAO);

        linhaSenha.add(labelSenha);
        linhaSenha.add(campoSenha);

        return linhaSenha;
    }


    //painel do botão login
    private JPanel criarPainelBotao() {
        JPanel painelBotao = new JPanel();
        painelBotao.setBackground(COR_FUNDO);

        botaoLogin = new JButton("Login");
        botaoLogin.setPreferredSize(TAMANHO_BOTAO);

        painelBotao.add(botaoLogin);
        return painelBotao;
    }


    //getters       
    public String getUsuario() {
        return campoUsuario.getText();
    }
    public String getSenha() {
        return new String(campoSenha.getPassword());
    }
    
    public void addLoginListener(ActionListener listener) {
        botaoLogin.addActionListener(listener);
}

    public void mostrarMensagem(String mensagem) {
        JOptionPane.showMessageDialog(this, mensagem);
}



}
