package br.ufjf.dcc.dcc025.view;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;

public class LoginView {
    public static void main(String[] args) throws Exception {
        JFrame janela = new JFrame();

        janela.setTitle("www.Hospital.com.br/Login");
        Font fontePadrao = new Font("Arial", Font.PLAIN, 16);

        // Tamanho e posição da janela de login
        janela.setBounds(600, 200, 600, 500);
        janela.getContentPane().setBackground(new Color(176, 196, 222));
        janela.setLayout(null);

        // Botão de Login
        JButton botaoLogin = new JButton("Login");
        botaoLogin.setBounds(250, 400, 100, 40);
        janela.add(botaoLogin);

        // Títulos da sistema
        JLabel labelTitulo = new JLabel("Hospital");
        labelTitulo.setBounds(200, 30, 200, 30);
        labelTitulo.setFont(new Font("Arial", Font.BOLD, 32));
        labelTitulo.setHorizontalAlignment(JLabel.CENTER);
        janela.add(labelTitulo);

        // Campo de texto para o usuário
        JLabel labelUsuario = new JLabel("Usuário:");
        labelUsuario.setBounds(100, 100, 100, 30);
        labelUsuario.setFont(fontePadrao);
        janela.add(labelUsuario);

        JTextField campoUsuario = new JTextField();
        campoUsuario.setBounds(200, 100, 200, 30);
        campoUsuario.setFont(fontePadrao);
        janela.add(campoUsuario);

        // Campo de texto para a senha
        JLabel labelSenha = new JLabel("Senha:");
        labelSenha.setBounds(100, 150, 100, 30);
        labelSenha.setFont(fontePadrao);
        janela.add(labelSenha);

        JTextField campoSenha = new JTextField();
        campoSenha.setBounds(200, 150, 200, 30);
        campoSenha.setFont(fontePadrao);
        janela.add(campoSenha);

        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setVisible(true);
    }

    public void setVisible(boolean b) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setVisible'");
    }
}