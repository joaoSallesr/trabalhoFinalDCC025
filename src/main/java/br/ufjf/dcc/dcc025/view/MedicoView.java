package br.ufjf.dcc.dcc025.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import br.ufjf.dcc.dcc025.model.Paciente;
import br.ufjf.dcc.dcc025.model.valueobjects.Consulta;
import br.ufjf.dcc.dcc025.model.valueobjects.HorarioTrabalho;

public class MedicoView extends JFrame {

    // Paineis principais
    private JPanel painelEsquerdo;
    private JPanel painelCentral;
    private JPanel painelDireito;
    private CardLayout cardLayout;

    // Botões do Menu Lateral
    private JButton btnAgenda;
    private JButton btnDocumento;
    private JButton btnConsulta;
    private JButton btnStatus;
    private JButton btnNotificacoes;
    private JButton btnSair;

    // Agenda
    private JComboBox<DayOfWeek> cbDiaSemana;
    private JSpinner spinnerInicio;
    private JSpinner spinnerFim;
    private JButton btnAdicionarHorario;
    private JButton btnRemoverHorario;
    private JTable tabelaHorarios;
    private DefaultTableModel tableModelHorarios;

    // Consultas
    private JComboBox<DayOfWeek> cbDiaFiltroConsulta;
    private JTable tabelaConsultasAgendadas;
    private DefaultTableModel tableModelConsultas;
    private JButton btnFiltrarConsultas;
    private JButton btnFinalizarConsulta;


    public MedicoView() {
        setTitle("Área do Médico");
        setSize(950, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        criarPainelEsquerdo();
        criarPainelCentral();
        criarPainelDireito();

        // Adicionando ao Frame
        add(painelEsquerdo, BorderLayout.WEST);
        add(painelCentral, BorderLayout.CENTER);
        add(painelDireito, BorderLayout.EAST);
    }

    private void criarPainelEsquerdo() {
        painelEsquerdo = new JPanel();
        painelEsquerdo.setLayout(new GridLayout(5, 1, 5, 5));
        painelEsquerdo.setBorder(BorderFactory.createTitledBorder("Menu"));

        btnAgenda = new JButton("Minha Agenda");
        btnDocumento = new JButton("Emitir Documento");
        btnConsulta = new JButton("Histórico");
        btnStatus = new JButton("Gerenciar Pacientes");
        btnNotificacoes = new JButton("Verificar notificações");

        btnAgenda.addActionListener(e -> cardLayout.show(painelCentral, "AGENDA"));

        painelEsquerdo.add(btnAgenda);
        painelEsquerdo.add(btnDocumento);
        painelEsquerdo.add(btnConsulta);
        painelEsquerdo.add(btnStatus);
        painelEsquerdo.add(btnNotificacoes);
    }

    private void criarPainelCentral() {
        cardLayout = new CardLayout();
        painelCentral = new JPanel(cardLayout);
        painelCentral.setBorder(BorderFactory.createTitledBorder("Área de Trabalho"));

        JPanel homePanel = new JPanel(new BorderLayout());
        JLabel lblBemVindo = new JLabel("Bem-vindo, Doutor(a). Selecione uma opção no menu.", SwingConstants.CENTER);
        homePanel.add(lblBemVindo, BorderLayout.CENTER);

        JPanel agendaPanel = criarPainelAgenda();
        JPanel consultasPanel = criarPainelConsultas();

        painelCentral.add(homePanel, "HOME");
        painelCentral.add(agendaPanel, "AGENDA");
        painelCentral.add(consultasPanel, "CONSULTAS");

        // Exibe Home por padrão
        cardLayout.show(painelCentral, "HOME");
    }

    private JPanel criarPainelAgenda() {
        JPanel painel = new JPanel(new BorderLayout());

        JPanel formPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        cbDiaSemana = new JComboBox<>(DayOfWeek.values());

        SpinnerNumberModel modeloInicio = new SpinnerNumberModel(8, 0, 23, 1);
        spinnerInicio = new JSpinner(modeloInicio);

        spinnerInicio.setEditor(new JSpinner.NumberEditor(spinnerInicio, "00':00'"));

        SpinnerNumberModel modeloFim = new SpinnerNumberModel(18, 0, 23, 1);
        spinnerFim = new JSpinner(modeloFim);
        spinnerFim.setEditor(new JSpinner.NumberEditor(spinnerFim, "00':00'"));

        btnAdicionarHorario = new JButton("Adicionar");

        formPanel.add(new JLabel("Dia:"));
        formPanel.add(cbDiaSemana);
        formPanel.add(new JLabel("Início:"));
        formPanel.add(spinnerInicio);
        formPanel.add(new JLabel("Fim:"));
        formPanel.add(spinnerFim);
        formPanel.add(btnAdicionarHorario);

        String[] colunas = {"Dia da Semana", "Início", "Fim"};
        tableModelHorarios = new DefaultTableModel(colunas, 0) {
            @Override // Bloqueia edição das células
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelaHorarios = new JTable(tableModelHorarios);
        JScrollPane scrollPane = new JScrollPane(tabelaHorarios);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnRemoverHorario = new JButton("Remover Horário Selecionado");
        bottomPanel.add(btnRemoverHorario);

        painel.add(formPanel, BorderLayout.NORTH);
        painel.add(scrollPane, BorderLayout.CENTER);
        painel.add(bottomPanel, BorderLayout.SOUTH);

        return painel;
    }

    private JPanel criarPainelConsultas() {
        JPanel painel = new JPanel(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        cbDiaFiltroConsulta = new JComboBox<>(DayOfWeek.values());
        btnFiltrarConsultas = new JButton("Carregar Consultas");
        btnFinalizarConsulta = new JButton("Finalizar Consulta");


        topPanel.add(new JLabel("Selecione o Dia:"));
        topPanel.add(cbDiaFiltroConsulta);
        topPanel.add(btnFiltrarConsultas);
        topPanel.add(btnFinalizarConsulta);


        String[] colunas = {"Horário", "Paciente", "Status"};
        tableModelConsultas = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelaConsultasAgendadas = new JTable(tableModelConsultas);
        tabelaConsultasAgendadas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scroll = new JScrollPane(tabelaConsultasAgendadas);

        painel.add(topPanel, BorderLayout.NORTH);
        painel.add(scroll, BorderLayout.CENTER);

        return painel;
    }

    private void criarPainelDireito() {
        painelDireito = new JPanel();
        painelDireito.setLayout(new GridLayout(1, 1, 10, 10));
        painelDireito.setBorder(
                BorderFactory.createTitledBorder("Sistema"));

        btnSair = new JButton("Sair");
        painelDireito.add(btnSair);
    }

    // Listeners status
    public void addGerenciarStatusListener(ActionListener listener) {
        btnStatus.addActionListener(listener);
    }

    // Listeners agenda
    public void addAdicionarHorarioListener(ActionListener listener) {
        btnAdicionarHorario.addActionListener(listener);
    }

    public void addRemoverHorarioListener(ActionListener listener) {
        btnRemoverHorario.addActionListener(listener);
    }

    public void addNavegarAgendaListener(ActionListener listener) {
        btnAgenda.addActionListener(listener);
    }

    // Listeners consultas
    public void addCarregarConsultasListener(ActionListener listener) {
        btnFiltrarConsultas.addActionListener(listener);
    }

    public void addNotificacoesListener(ActionListener listener) {
        btnNotificacoes.addActionListener(listener);
    }

    public void addVerConsultasListener(ActionListener listener) {
        btnConsulta.addActionListener(e -> {
            cardLayout.show(painelCentral, "CONSULTAS");
            listener.actionPerformed(e);
        });
    }

    // Listeners sair
    public void addSairListener(ActionListener listener) {
        btnSair.addActionListener(listener);
    }

    // Getters de Dados da Tela
    public DayOfWeek getDiaSelecionado() {
        return (DayOfWeek) cbDiaSemana.getSelectedItem();
    }

    public DayOfWeek getDiaFiltroConsulta() {
        return (DayOfWeek) cbDiaFiltroConsulta.getSelectedItem();
    }

    public int getLinhaConsultaSelecionada() {
        return tabelaConsultasAgendadas.getSelectedRow();
    }

    public void atualizarListaConsultas(List<Consulta> consultas) {
        tableModelConsultas.setRowCount(0);
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("HH:mm");

        for (Consulta c : consultas) {
            tableModelConsultas.addRow(new Object[]{
                c.getHorarioConsulta().format(fmt),
                c.getNomePacienteDisplay(),
                c.getEstadoConsulta().toString()
            });
        }
    }

    // Converte o Date do JSpinner para LocalTime
    public LocalTime getHorarioInicio() {
        int hora = (Integer) spinnerInicio.getValue();
        return LocalTime.of(hora, 0);
    }

    public LocalTime getHorarioFim() {
        int hora = (Integer) spinnerFim.getValue();
        return LocalTime.of(hora, 0);
    }

    public int getLinhaSelecionada() {
        return tabelaHorarios.getSelectedRow();
    }

    // Atualização Visual
    public void atualizarTabela(List<HorarioTrabalho> agenda) {
        tableModelHorarios.setRowCount(0);
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("HH:mm");

        for (HorarioTrabalho h : agenda) {
            tableModelHorarios.addRow(new Object[]{
                h.getDiaTrabalho(),
                h.getHorarioComeco().format(fmt),
                h.getHorarioFinal().format(fmt)
            });
        }
    }

    public void abrirDialogoGerenciamento(List<Paciente> todosPacientes,
            Consumer<Paciente> onAlternarVisita,
            Consumer<Paciente> onAlternarHospitalizacao) {

        String[] colunas = {"Nome", "CPF", "Hospitalizado", "Pode receber visita"};
        DefaultTableModel model = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (Paciente p : todosPacientes) {
            model.addRow(new Object[]{
                p.getNome().getNome() + " " + p.getNome().getSobrenome(),
                p.getCPF().getCPF(),
                p.isHospitalizado() ? "Sim" : "Não",
                p.isRecebeVisita() ? "Apto" : "Não apto"
            });
        }

        JTable tabela = new JTable(model);
        JScrollPane scroll = new JScrollPane(tabela);

        JButton btnAlternarVisita = new JButton("Alternar status de visita");
        btnAlternarVisita.addActionListener(ev -> {
            int linha = tabela.getSelectedRow();
            if (linha == -1) {
                JOptionPane.showMessageDialog(this, "Selecione um paciente.");
                return;
            }
            Paciente p = todosPacientes.get(linha);
            if (!p.isHospitalizado()) {
                JOptionPane.showMessageDialog(this, "Paciente não está hospitalizado para receber visitas.");
                return;
            }

            onAlternarVisita.accept(p);

            model.setValueAt(p.isRecebeVisita() ? "Apto" : "Não apto", linha, 3);
        });

        JButton btnAlternarHospitalizar = new JButton("Hospitalizar / Dar Alta");
        btnAlternarHospitalizar.addActionListener(ev -> {
            int linha = tabela.getSelectedRow();
            if (linha == -1) {
                JOptionPane.showMessageDialog(this, "Selecione um paciente.");
                return;
            }

            Paciente p = todosPacientes.get(linha);
            String acao = p.isHospitalizado() ? "Dar Alta" : "Internar";

            int confirm = JOptionPane.showConfirmDialog(this,
                    "Deseja realmente " + acao + " o paciente?", "Confirmar", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                onAlternarHospitalizacao.accept(p);

                model.setValueAt(p.isHospitalizado() ? "Sim" : "Não", linha, 2);

                model.setValueAt(p.isRecebeVisita() ? "Apto" : "Não apto", linha, 3);

                JOptionPane.showMessageDialog(this, "Status alterado com sucesso.");
            }
        });

        JDialog dialog = new JDialog(this, "Gerenciar Todos os Pacientes", true);
        dialog.setLayout(new BorderLayout());
        dialog.add(scroll, BorderLayout.CENTER);

        JPanel botoes = new JPanel();
        botoes.add(btnAlternarVisita);
        botoes.add(btnAlternarHospitalizar);

        dialog.add(botoes, BorderLayout.SOUTH);
        dialog.setSize(800, 500);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    public void addEmitirDocumentoListener(java.awt.event.ActionListener listener) {
        btnDocumento.addActionListener(listener);
    }

    public void addFinalizarConsultaListener(ActionListener listener) {
        btnFinalizarConsulta.addActionListener(listener);
    }
}