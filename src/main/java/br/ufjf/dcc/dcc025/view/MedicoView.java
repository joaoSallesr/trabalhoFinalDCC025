package br.ufjf.dcc.dcc025.view;

import javax.swing.*;
import java.awt.*;

public class MedicoView extends JFrame {

    private JPanel painelEsquerdo;
    private JPanel painelCentral;
    private JPanel painelDireito;

    private JButton btnAgenda;
    private JButton btnDocumento;
    private JButton btnConsulta;
    private JButton btnStatus;
    private JButton btnSair;

    public MedicoView() {

        setTitle("Área do Médico");
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // centraliza
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
        painelEsquerdo.setLayout(new GridLayout(4, 1, 5, 5)); // 5px de espaçamento entre botões
        painelEsquerdo.setBorder(BorderFactory.createTitledBorder("Ações"));

        btnAgenda = new JButton("Agenda");
        btnDocumento = new JButton("Emitir Documento");
        btnConsulta = new JButton("Histórico de consultas");
        btnStatus = new JButton("Gerenciar status dos pacientes");

        painelEsquerdo.add(btnAgenda);
        painelEsquerdo.add(btnDocumento);
        painelEsquerdo.add(btnConsulta);
        painelEsquerdo.add(btnStatus);
    }

    private void criarPainelCentral() {
        painelCentral = new JPanel();
        painelCentral.setLayout(new BorderLayout());
        painelCentral.setBorder(BorderFactory.createTitledBorder("Informações"));

        JLabel label = new JLabel(
                "Selecione uma opção ao lado",
                SwingConstants.CENTER);

        painelCentral.add(label, BorderLayout.CENTER);
    }

    private void criarPainelDireito() {
        painelDireito = new JPanel();
        painelDireito.setLayout(new GridLayout(2, 1, 10, 10));
        painelDireito.setBorder(BorderFactory.createTitledBorder("Sistema"));

        btnSair = new JButton("Sair");

        painelDireito.add(btnSair);
    }

    public void addGerenciarStatusListener(java.awt.event.ActionListener listener) {
        btnStatus.addActionListener(listener);
    }

    public void addSairListener(java.awt.event.ActionListener listener) {
        btnSair.addActionListener(listener);
    }

}
