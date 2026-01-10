package br.ufjf.dcc.dcc025.view;

import javax.swing.*;
import java.awt.*;

public class PacienteView extends JFrame {

    private JPanel painelEsquerdo;
    private JPanel painelCentral;
    private JPanel painelDireito;

    private JButton btnMinhasConsultas;
    private JButton btnConferirHistorico;
    private JButton btnMeusDocumentos;
    private JButton btnAlterarDados;
    private JButton btnVerStatus;

    private JButton btnSair;

    public PacienteView() {

        setTitle("Área do Paciente");
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        ImageIcon image = new ImageIcon("hospital-building.png"); // icone
        this.setIconImage(image.getImage()); // icone

        setLayout(new BorderLayout());

        criarPainelEsquerdo();
        criarPainelCentral();
        criarPainelDireito();

        add(painelEsquerdo, BorderLayout.WEST);
        add(painelCentral, BorderLayout.CENTER);
        add(painelDireito, BorderLayout.EAST);
    }

    private void criarPainelEsquerdo() {
        painelEsquerdo = new JPanel();
        painelEsquerdo.setLayout(new GridLayout(0, 1, 10, 10));
        painelEsquerdo.setBorder(
                BorderFactory.createTitledBorder("Ações"));

        btnMinhasConsultas = new JButton("Minhas Consultas");
        btnConferirHistorico = new JButton("Conferir Histórico");
        btnMeusDocumentos = new JButton("Meus Documentos");
        btnAlterarDados = new JButton("Alterar meus dados");
        btnVerStatus = new JButton("Verificar status de internação de outros pacientes");

        painelEsquerdo.add(btnMinhasConsultas);
        painelEsquerdo.add(btnConferirHistorico);
        painelEsquerdo.add(btnMeusDocumentos);
        painelEsquerdo.add(btnAlterarDados);
        painelEsquerdo.add(btnVerStatus);
    }

    private void criarPainelCentral() {
        painelCentral = new JPanel();
        painelCentral.setLayout(new BorderLayout());
        painelCentral.setBorder(
                BorderFactory.createTitledBorder("Informações"));

        JLabel label = new JLabel(
                "Selecione uma opção ao lado",
                SwingConstants.CENTER);

        painelCentral.add(label, BorderLayout.CENTER);
    }

    private void criarPainelDireito() {
        painelDireito = new JPanel();
        painelDireito.setLayout(new GridLayout(1, 1, 10, 10));
        painelDireito.setBorder(
                BorderFactory.createTitledBorder("Sistema"));

        btnSair = new JButton("Sair");
        painelDireito.add(btnSair);
    }

    public void atualizarPainelCentral(JComponent componente) {
        painelCentral.removeAll();
        painelCentral.setLayout(new BorderLayout());
        painelCentral.add(componente, BorderLayout.CENTER);
        painelCentral.revalidate();
        painelCentral.repaint();
    }

    public void addVerStatusListener(java.awt.event.ActionListener listener) {
        btnVerStatus.addActionListener(listener);
    }

    public void addVerDadosListener(java.awt.event.ActionListener listener) {
        btnAlterarDados.addActionListener(listener);
    }

    public void addSairListener(java.awt.event.ActionListener listener) {
        btnSair.addActionListener(listener);
    }

}
