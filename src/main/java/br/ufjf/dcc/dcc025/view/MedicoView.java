package br.ufjf.dcc.dcc025.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
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
import javax.swing.SpinnerDateModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import br.ufjf.dcc.dcc025.model.Paciente;
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

        painelCentral.add(homePanel, "HOME");
        painelCentral.add(agendaPanel, "AGENDA");

        // Exibe Home por padrão
        cardLayout.show(painelCentral, "HOME");
    }

    private JPanel criarPainelAgenda() {
        JPanel painel = new JPanel(new BorderLayout());

        JPanel formPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        cbDiaSemana = new JComboBox<>(DayOfWeek.values());

        Date date = new Date();
        SpinnerDateModel smInicio = new SpinnerDateModel(date, null, null, Calendar.HOUR_OF_DAY);
        spinnerInicio = new JSpinner(smInicio);
        JSpinner.DateEditor deInicio = new JSpinner.DateEditor(spinnerInicio, "HH:mm");
        spinnerInicio.setEditor(deInicio);

        SpinnerDateModel smFim = new SpinnerDateModel(date, null, null, Calendar.HOUR_OF_DAY);
        spinnerFim = new JSpinner(smFim);
        JSpinner.DateEditor deFim = new JSpinner.DateEditor(spinnerFim, "HH:mm");
        spinnerFim.setEditor(deFim);

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

    private void criarPainelDireito() {
        painelDireito = new JPanel();
        painelDireito.setLayout(new GridLayout(1, 1, 10, 10));
        painelDireito.setBorder(
                BorderFactory.createTitledBorder("Sistema"));

        btnSair = new JButton("Sair");
        painelDireito.add(btnSair);
    }

    // Listeners
    public void addAdicionarHorarioListener(ActionListener listener) {
        btnAdicionarHorario.addActionListener(listener);
    }

    public void addRemoverHorarioListener(ActionListener listener) {
        btnRemoverHorario.addActionListener(listener);
    }

    public void addSairListener(ActionListener listener) {
        btnSair.addActionListener(listener);
    }

    public void addGerenciarStatusListener(ActionListener listener) {
        btnStatus.addActionListener(listener);
    }

    public void addNavegarAgendaListener(ActionListener listener) {
        btnAgenda.addActionListener(listener);
    }

    // Getters de Dados da Tela
    public DayOfWeek getDiaSelecionado() {
        return (DayOfWeek) cbDiaSemana.getSelectedItem();
    }

    // Converte o Date do JSpinner para LocalTime
    public LocalTime getHorarioInicio() {
        Date date = (Date) spinnerInicio.getValue();
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
    }

    public LocalTime getHorarioFim() {
        Date date = (Date) spinnerFim.getValue();
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
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
}
