package br.ufjf.dcc.dcc025.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.time.DayOfWeek;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import br.ufjf.dcc.dcc025.model.valueobjects.Especialidade;

public class PacienteView extends JFrame {

    private JPanel painelEsquerdo;
    private JPanel painelCentral;
    private JPanel painelDireito;

    private JButton btnMinhasConsultas;
    private JButton btnAgendarConsulta;
    private JButton btnConferirHistorico;
    private JButton btnMeusDocumentos;
    private JButton btnAlterarDados;
    private JButton btnVerStatus;

    // Consultas
    private JComboBox<Especialidade> cbEspecialidade;
    private JComboBox<DayOfWeek> cbDiaSemana;
    private JButton btnBuscarHorarios;
    private JTable tabelaHorariosDisponiveis;
    private DefaultTableModel modelHorarios;
    private JButton btnConfirmarAgendamento;

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

        btnAgendarConsulta = new JButton("Nova Consulta");
        btnMinhasConsultas = new JButton("Minhas Consultas");
        btnConferirHistorico = new JButton("Conferir Histórico");
        btnMeusDocumentos = new JButton("Meus Documentos");
        btnAlterarDados = new JButton("Alterar meus dados");
        btnVerStatus = new JButton("Verificar status de internação de outros pacientes");

        painelEsquerdo.add(btnAgendarConsulta);
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

    public JPanel criarTelaAgendamento() {
        JPanel painel = new JPanel(new BorderLayout());

        JPanel filtrosPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        cbEspecialidade = new JComboBox<>(Especialidade.values());
        cbDiaSemana = new JComboBox<>(DayOfWeek.values());
        btnBuscarHorarios = new JButton("Buscar Horários");

        filtrosPanel.add(new JLabel("Especialidade:"));
        filtrosPanel.add(cbEspecialidade);
        filtrosPanel.add(new JLabel("Dia Preferido:"));
        filtrosPanel.add(cbDiaSemana);
        filtrosPanel.add(btnBuscarHorarios);

        String[] colunas = {"Médico", "Especialidade", "Dia", "Horário"};
        modelHorarios = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        tabelaHorariosDisponiveis = new JTable(modelHorarios);
        tabelaHorariosDisponiveis.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scroll = new JScrollPane(tabelaHorariosDisponiveis);

        JPanel botPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnConfirmarAgendamento = new JButton("Confirmar Agendamento");
        botPanel.add(btnConfirmarAgendamento);

        painel.add(filtrosPanel, BorderLayout.NORTH);
        painel.add(scroll, BorderLayout.CENTER);
        painel.add(botPanel, BorderLayout.SOUTH);

        return painel;
    }

    // Listeners consulta
    public void addAgendarConsultaNavegacaoListener(ActionListener listener) {
        btnAgendarConsulta.addActionListener(listener);
    }

    public void addBuscarHorariosListener(ActionListener listener) {
        if (btnBuscarHorarios != null) {
            btnBuscarHorarios.addActionListener(listener);
        }
    }

    public void addConfirmarAgendamentoListener(ActionListener listener) {
        if (btnConfirmarAgendamento != null) {
            btnConfirmarAgendamento.addActionListener(listener);
        }
    }

    public void addMinhasConsultasListener(ActionListener listener) {
        btnMinhasConsultas.addActionListener(listener);
    }

    // Listener status
    public void addVerStatusListener(ActionListener listener) {
        btnVerStatus.addActionListener(listener);
    }

    public void addVerDadosListener(ActionListener listener) {
        btnAlterarDados.addActionListener(listener);
    }

    // Listeners documento
    public void addMeusDocumentosListener(ActionListener listener) {
        btnMeusDocumentos.addActionListener(listener);
    }

    // Listeners sair
    public void addSairListener(ActionListener listener) {
        btnSair.addActionListener(listener);
    }

    // Getters
    public Especialidade getEspecialidadeSelecionada() {
        return (Especialidade) cbEspecialidade.getSelectedItem();
    }

    public DayOfWeek getDiaSelecionado() {
        return (DayOfWeek) cbDiaSemana.getSelectedItem();
    }

    public int getLinhaHorarioSelecionada() {
        return tabelaHorariosDisponiveis.getSelectedRow();
    }

    public void preencherTabelaHorarios(List<Object[]> dados) {
        modelHorarios.setRowCount(0);
        for (Object[] linha : dados) {
            modelHorarios.addRow(linha);
        }
    }
}
