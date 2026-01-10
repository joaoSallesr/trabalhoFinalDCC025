package br.ufjf.dcc.dcc025.view;

import java.awt.BorderLayout; //'*' importa todas as classes públicas desse pacote
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import br.ufjf.dcc.dcc025.controller.MedicoController;
import br.ufjf.dcc.dcc025.controller.PacienteController;
import br.ufjf.dcc.dcc025.controller.RecepcionistaController;
import br.ufjf.dcc.dcc025.model.Medico;
import br.ufjf.dcc.dcc025.model.Paciente;
import br.ufjf.dcc.dcc025.model.dto.DadosMedico;
import br.ufjf.dcc.dcc025.model.dto.DadosPaciente;
import br.ufjf.dcc.dcc025.model.dto.DadosRecepcionista;
import br.ufjf.dcc.dcc025.model.valueobjects.Especialidade;

public class RecepcionistaView extends JFrame {

    private JPanel painelEsquerdo;
    private JPanel painelCentral;
    private JPanel painelDireito;
    private CardLayout cardLayout; // reutilizar a janela para as diversas opções

    private final PacienteController pacienteController;
    private final MedicoController medicoController;
    private final RecepcionistaController recepcionistaController;

    private JButton btnCadastroPaciente;
    private JButton btnCadastroMedico;
    private JButton btnCadastroRecepcionista;
    private JButton btnStatus;
    private JButton btnListaMedico;
    private JButton btnAgendaMedico;
    private JButton btnConsultarFaltas;
    private JButton btnSair;

    // cadastro paciente:
    private JTextField txtNomePaciente, txtSobrenomePaciente, txtCpfPaciente, txtEmailPaciente, txtTelefonePaciente;
    private JTextField txtRuaPaciente, txtNumeroPaciente, txtCidadePaciente, txtCepPaciente, txtBairroPaciente; // Endereço
                                                                                                                // pode
                                                                                                                // ser
                                                                                                                // unico
                                                                                                                // se só
                                                                                                                // paciente
                                                                                                                // tem
                                                                                                                // endereço
    private JPasswordField txtSenhaPaciente;

    // cadastro médico:
    private JTextField txtNomeMedico, txtSobrenomeMedico, txtCpfMedico, txtEmailMedico;
    private JPasswordField txtSenhaMedico;
    private JComboBox<Especialidade> cbEspecialidade;

    // cadastro recepcionista:
    private JTextField txtNomeRecepcionista, txtSobrenomeRecepcionista, txtCpfRecepcionista, txtEmailRecepcionista;
    private JPasswordField txtSenhaRecepcionista;

    public RecepcionistaView() {
        setTitle("Área Recepcionista");
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        this.pacienteController = new PacienteController(null, null);
        this.medicoController = new MedicoController(null, null);
        this.recepcionistaController = new RecepcionistaController(null, null);
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
        btnCadastroMedico = new JButton("Cadastrar Medico");
        btnCadastroMedico.addActionListener(e -> {
            cardLayout.show(painelCentral, "CADASTRO_MEDICO");
        });
        btnCadastroRecepcionista = new JButton("Cadastrar Recepcionista");
        btnCadastroRecepcionista.addActionListener(e -> {
            cardLayout.show(painelCentral, "CADASTRO_RECEPCIONISTA");
        });
        btnStatus = new JButton("Conferir Status dos Pacientes");
        btnStatus.addActionListener(e -> mostrarStatusPacientes());
        btnListaMedico = new JButton("Lista de médicos");
        btnListaMedico.addActionListener(e -> gerenciarMedicos());
        btnAgendaMedico = new JButton("Verificar agenda dos médicos");
        btnConsultarFaltas = new JButton("Verificar faltas");

        painelEsquerdo.add(btnCadastroPaciente);
        painelEsquerdo.add(btnCadastroMedico);
        painelEsquerdo.add(btnCadastroRecepcionista);
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
        painelCentral.add(criarPainelCadastroMedico(), "CADASTRO_MEDICO");
        painelCentral.add(criarPainelCadastroRecepcionista(), "CADASTRO_RECEPCIONISTA");

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
        JPanel painel = new JPanel(new GridLayout(0, 2, 10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        txtNomePaciente = new JTextField();
        txtSobrenomePaciente = new JTextField();
        txtCpfPaciente = new JTextField();
        txtEmailPaciente = new JTextField();
        txtSenhaPaciente = new JPasswordField();
        txtTelefonePaciente = new JTextField();
        txtRuaPaciente = new JTextField();
        txtNumeroPaciente = new JTextField();
        txtCidadePaciente = new JTextField();
        txtCepPaciente = new JTextField();
        txtBairroPaciente = new JTextField();

        JButton btnSalvar = new JButton("Salvar");

        painel.add(new JLabel("Nome:"));
        painel.add(txtNomePaciente);

        painel.add(new JLabel("Sobrenome:"));
        painel.add(txtSobrenomePaciente);

        painel.add(new JLabel("CPF:"));
        painel.add(txtCpfPaciente);

        painel.add(new JLabel("Email:"));
        painel.add(txtEmailPaciente);

        painel.add(new JLabel("Senha:"));
        painel.add(txtSenhaPaciente);

        painel.add(new JLabel("Telefone:"));
        painel.add(txtTelefonePaciente);

        painel.add(new JLabel("Rua:"));
        painel.add(txtRuaPaciente);
        painel.add(new JLabel("Bairro:"));
        painel.add(txtBairroPaciente);
        painel.add(new JLabel("Número:"));
        painel.add(txtNumeroPaciente);
        painel.add(new JLabel("CEP:"));
        painel.add(txtCepPaciente);
        painel.add(new JLabel("Cidade:"));
        painel.add(txtCidadePaciente);
        painel.add(new JLabel());
        painel.add(btnSalvar);

        btnSalvar.addActionListener(e -> salvarPaciente());

        return painel;
    }

    private JPanel criarPainelCadastroMedico() {
        JPanel painel = new JPanel(new GridLayout(0, 2, 10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        txtNomeMedico = new JTextField();
        txtSobrenomeMedico = new JTextField();
        txtCpfMedico = new JTextField();
        txtEmailMedico = new JTextField();
        txtSenhaMedico = new JPasswordField();
        cbEspecialidade = new JComboBox<>(Especialidade.values());

        JButton btnSalvar = new JButton("Salvar");

        painel.add(new JLabel("Nome:"));
        painel.add(txtNomeMedico);

        painel.add(new JLabel("Sobrenome:"));
        painel.add(txtSobrenomeMedico);

        painel.add(new JLabel("CPF:"));
        painel.add(txtCpfMedico);

        painel.add(new JLabel("Email:"));
        painel.add(txtEmailMedico);

        painel.add(new JLabel("Senha:"));
        painel.add(txtSenhaMedico);

        painel.add(new JLabel("Especialidade:"));
        painel.add(cbEspecialidade);

        painel.add(new JLabel());
        painel.add(btnSalvar);

        btnSalvar.addActionListener(e -> salvarMedico());

        return painel;
    }

    private JPanel criarPainelCadastroRecepcionista() {
        JPanel painel = new JPanel(new GridLayout(10, 4, 5, 5));
        painel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        txtNomeRecepcionista = new JTextField();
        txtSobrenomeRecepcionista = new JTextField();
        txtCpfRecepcionista = new JTextField();
        txtEmailRecepcionista = new JTextField();
        txtSenhaRecepcionista = new JPasswordField();

        JButton btnSalvar = new JButton("Salvar");

        painel.add(new JLabel("Nome:"));
        painel.add(txtNomeRecepcionista);

        painel.add(new JLabel("Sobrenome:"));
        painel.add(txtSobrenomeRecepcionista);

        painel.add(new JLabel("CPF:"));
        painel.add(txtCpfRecepcionista);

        painel.add(new JLabel("Email:"));
        painel.add(txtEmailRecepcionista);

        painel.add(new JLabel("Senha:"));
        painel.add(txtSenhaRecepcionista);

        painel.add(new JLabel());
        painel.add(btnSalvar);

        btnSalvar.addActionListener(e -> salvarRecepcionista());

        return painel;
    }

    @SuppressWarnings("UseSpecificCatch")
    private void salvarPaciente() {
        try {
            String nome = txtNomePaciente.getText();
            String sobrenome = txtSobrenomePaciente.getText();
            String cpf = txtCpfPaciente.getText();
            String senha = new String(txtSenhaPaciente.getPassword());
            String email = txtEmailPaciente.getText();
            String telefone = txtTelefonePaciente.getText();
            String cep = txtCepPaciente.getText();
            String rua = txtRuaPaciente.getText();
            String bairro = txtBairroPaciente.getText();
            String numeroString = txtNumeroPaciente.getText();
            String cidade = txtCidadePaciente.getText();
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
                    cep, rua, bairro, cidade, numeroCasaInt);
            pacienteController.cadastrarPaciente(dadosPaciente);

            JOptionPane.showMessageDialog(
                    this,
                    "Paciente cadastrado com sucesso!");

            limparCampos();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Erro: " + e.getMessage());
        }
    }

    @SuppressWarnings("UseSpecificCatch")
    private void salvarMedico() {
        try {
            String nome = txtNomeMedico.getText();
            String sobrenome = txtSobrenomeMedico.getText();
            String cpf = txtCpfMedico.getText();
            String senha = new String(txtSenhaMedico.getPassword());
            String email = txtEmailMedico.getText();
            Especialidade espEnum = (Especialidade) cbEspecialidade.getSelectedItem();
            String especialidade = espEnum.toString();

            DadosMedico dadosMedico = new DadosMedico(
                    nome, sobrenome, cpf,
                    senha, email, especialidade);
            medicoController.cadastrarMedico(dadosMedico);

            JOptionPane.showMessageDialog(
                    this,
                    "Médico cadastrado com sucesso!");

            limparCampos();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Erro: " + e.getMessage());
        }
    }

    @SuppressWarnings("UseSpecificCatch")
    private void salvarRecepcionista() {
        try {
            String nome = txtNomeRecepcionista.getText();
            String sobrenome = txtSobrenomeRecepcionista.getText();
            String cpf = txtCpfRecepcionista.getText();
            String senha = new String(txtSenhaRecepcionista.getPassword());
            String email = txtEmailRecepcionista.getText();

            DadosRecepcionista dadosRecepcionista = new DadosRecepcionista(
                    nome, sobrenome, cpf,
                    senha, email);
            recepcionistaController.cadastrarRecepcionista(dadosRecepcionista);

            JOptionPane.showMessageDialog(
                    this,
                    "Recepcionista cadastrado com sucesso!");

            limparCampos();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Erro: " + e.getMessage());
        }
    }

    private void limparCampos() {
        // Limpa Paciente
        if (txtNomePaciente != null) {
            txtNomePaciente.setText("");
        }
        if (txtSobrenomePaciente != null) {
            txtSobrenomePaciente.setText("");
        }
        if (txtCpfPaciente != null) {
            txtCpfPaciente.setText("");
        }
        if (txtSenhaPaciente != null) {
            txtSenhaPaciente.setText("");
        }
        if (txtEmailPaciente != null) {
            txtEmailPaciente.setText("");
        }
        if (txtTelefonePaciente != null) {
            txtTelefonePaciente.setText("");
        }
        if (txtCepPaciente != null) {
            txtCepPaciente.setText("");
        }
        if (txtRuaPaciente != null) {
            txtRuaPaciente.setText("");
        }
        if (txtBairroPaciente != null) {
            txtBairroPaciente.setText("");
        }
        if (txtNumeroPaciente != null) {
            txtNumeroPaciente.setText("");
        }
        if (txtCidadePaciente != null) {
            txtCidadePaciente.setText("");
        }

        // Limpa Medico
        if (txtNomeMedico != null) {
            txtNomeMedico.setText("");
        }
        if (txtSobrenomeMedico != null) {
            txtSobrenomeMedico.setText("");
        }
        if (txtCpfMedico != null) {
            txtCpfMedico.setText("");
        }
        if (txtEmailMedico != null) {
            txtEmailMedico.setText("");
        }
        if (txtSenhaMedico != null) {
            txtSenhaMedico.setText("");
        }
        if (cbEspecialidade != null) {
            cbEspecialidade.setSelectedIndex(0);
        }

        // Limpa Recepcionista
        if (txtNomeRecepcionista != null) {
            txtNomeRecepcionista.setText("");
        }
        if (txtSobrenomeRecepcionista != null) {
            txtSobrenomeRecepcionista.setText("");
        }
        if (txtCpfRecepcionista != null) {
            txtCpfRecepcionista.setText("");
        }
        if (txtEmailRecepcionista != null) {
            txtEmailRecepcionista.setText("");
        }
        if (txtSenhaRecepcionista != null) {
            txtSenhaRecepcionista.setText("");
        }
    }

    private void mostrarStatusPacientes() {
        List<Paciente> hospitalizados = pacienteController.buscarPacientesHospitalizados();
        List<Paciente> podemReceberVisita = pacienteController.buscarRecebemVisitas();

        if (hospitalizados.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Não há pacientes hospitalizados no momento.");
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("*** SITUAÇÃO DOS PACIENTES ***\n\n");

        for (Paciente p : hospitalizados) {

            boolean liberaVisita = podemReceberVisita.contains(p);

            String statusTexto = liberaVisita ? "Sim" : "Não";

            sb.append("Nome: ").append(p.getNome().getNome()).append(" ").append(p.getNome().getSobrenome())
                    .append("\n");
            sb.append("CPF: ").append(p.getCPF().getCPF()).append("\n");
            sb.append("Pode receber visita? - ").append(statusTexto).append("\n");
            sb.append("--------------------------------------\n");
        }

        JTextArea textArea = new JTextArea(sb.toString());
        textArea.setEditable(false);
        textArea.setRows(20);
        textArea.setColumns(40);

        JScrollPane scrollPane = new JScrollPane(textArea);

        JOptionPane.showMessageDialog(
                this,
                scrollPane,
                "Relatório de Hospitalização",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void gerenciarMedicos() {
        List<Medico> medicos = medicoController.buscarMedicos();

        // Configura as colunas da tabela
        String[] colunas = { "Nome", "CPF", "Especialidade", "Status" };
        DefaultTableModel model = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Preenche a tabela com os dados
        for (Medico m : medicos) {
            String status = m.isAtivo() ? "Ativo" : "Inativo";
            Object[] linha = {
                    m.getNome().getNome() + " " + m.getNome().getSobrenome(),
                    m.getCPF().getCPF(),
                    m.getEspecialidade(),
                    status
            };
            model.addRow(linha);
        }

        JTable tabela = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(tabela);

        JButton btnAlterarStatus = new JButton("Ativar / Desativar Médico Selecionado");

        btnAlterarStatus.addActionListener(e -> {
            int linhaSelecionada = tabela.getSelectedRow();

            if (linhaSelecionada == -1) {
                JOptionPane.showMessageDialog(this, "Selecione um médico na tabela primeiro.");
                return;
            }

            Medico medicoSelecionado = medicos.get(linhaSelecionada);

            medicoController.alternarAtivo(medicoSelecionado);

            String novoStatus = medicoSelecionado.isAtivo() ? "Ativo" : "Inativo";
            model.setValueAt(novoStatus, linhaSelecionada, 3);

            JOptionPane.showMessageDialog(this, "Status alterado para: " + novoStatus);
        });

        JDialog dialog = new JDialog(this, "Gerenciamento de Médicos", true);
        dialog.setLayout(new BorderLayout());
        dialog.setLocationRelativeTo(this);

        dialog.add(scrollPane, BorderLayout.CENTER);
        dialog.setSize(800, 600);
        dialog.setLocationRelativeTo(this);

        JPanel painelBotoes = new JPanel(new FlowLayout());
        painelBotoes.add(btnAlterarStatus);

        dialog.add(painelBotoes, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }
}
