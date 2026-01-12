package br.ufjf.dcc.dcc025.controller;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import br.ufjf.dcc.dcc025.model.DocumentoMedico;
import br.ufjf.dcc.dcc025.model.Medico;
import br.ufjf.dcc.dcc025.model.Paciente;
import br.ufjf.dcc.dcc025.model.dto.DadosPaciente;
import br.ufjf.dcc.dcc025.model.repository.GerenciadorRepository;
import br.ufjf.dcc.dcc025.model.valueobjects.Consulta;
import br.ufjf.dcc.dcc025.model.valueobjects.Contato;
import br.ufjf.dcc.dcc025.model.valueobjects.Email;
import br.ufjf.dcc.dcc025.model.valueobjects.Endereco;
import br.ufjf.dcc.dcc025.model.valueobjects.Especialidade;
import br.ufjf.dcc.dcc025.model.valueobjects.EstadoConsulta;
import br.ufjf.dcc.dcc025.model.valueobjects.Senha;
import br.ufjf.dcc.dcc025.view.LoginView;
import br.ufjf.dcc.dcc025.view.PacienteView;

public class PacienteController {

    private Paciente paciente;
    private PacienteView view;

    private List<SlotDisponibilidade> slotsAtuaisNaTela;

    public PacienteController(Paciente paciente, PacienteView view) {
        this.paciente = paciente;
        this.view = view;
        this.slotsAtuaisNaTela = new ArrayList<>();
        if (this.view != null) {
            this.view.addSairListener(new SairListener());
            this.view.addVerStatusListener(e -> mostrarStatusInternacao());
            this.view.addVerDadosListener(e -> mostrarMeusDados());
            this.view.addMeusDocumentosListener(new MeusDocumentosListener());
            this.view.addMinhasConsultasListener(e -> gerenciarMinhasConsultas());
            this.view.addConferirHistoricoListener(e -> mostrarHistoricoConsultas());
            this.view.addAgendarConsultaNavegacaoListener(e -> {
                JPanel painelAgendamento = view.criarTelaAgendamento();
                view.atualizarPainelCentral(painelAgendamento);
                reatribuirListenersAgendamento();
            });
        }
    }

    public PacienteController() {
    }

    public void cadastrarPaciente(DadosPaciente dados) {
        Paciente novoPaciente = new Paciente(dados);
        GerenciadorRepository.getInstance().adicionarPaciente(novoPaciente);
    }

    // Atualização de atributos
    public void alterarSenha(Paciente paciente, String novaSenha) {
        Senha senha = new Senha(novaSenha);
        paciente.alterarSenha(senha);
        GerenciadorRepository.getInstance().salvarPacientes();
    }

    public void alterarEmail(Paciente paciente, String novoEmail) {
        Email email = new Email(novoEmail);
        paciente.alterarEmail(email);
        GerenciadorRepository.getInstance().salvarPacientes();
    }

    public void alterarContato(Paciente paciente, String novoNumero) {
        Contato contato = new Contato(novoNumero);
        paciente.alterarContato(contato);
        GerenciadorRepository.getInstance().salvarPacientes();
    }

    public void alterarEndereco(
            Paciente paciente, String cep, String rua,
            String bairro, String cidade, int numeroCasa) {
        Endereco endereco = new Endereco(cep, rua, bairro, cidade, numeroCasa);
        paciente.alterarEndereco(endereco);
        GerenciadorRepository.getInstance().salvarPacientes();
    }

    public void ativarUsuario(Paciente paciente) {
        paciente.ativarUsuario();
        GerenciadorRepository.getInstance().salvarPacientes();
    }

    public void desativarUsuario(Paciente paciente) {
        paciente.desativarUsuario();
        GerenciadorRepository.getInstance().salvarPacientes();
    }

    public void hospitalizar(Paciente paciente) {
        paciente.hospitalizar();
        GerenciadorRepository.getInstance().salvarPacientes();
    }

    public void deshospitalizar(Paciente paciente) {
        paciente.deshospitalizar();
        GerenciadorRepository.getInstance().salvarPacientes();
    }

    public void permitirVisita(Paciente paciente) {
        paciente.permitirVisita();
        GerenciadorRepository.getInstance().salvarPacientes();
    }

    public void bloquearVisita(Paciente paciente) {
        paciente.bloquearVisita();
        GerenciadorRepository.getInstance().salvarPacientes();
    }

    // Ações
    private void reatribuirListenersAgendamento() {
        this.view.addBuscarHorariosListener(new BuscarHorariosAction());
        this.view.addConfirmarAgendamentoListener(new ConfirmarAgendamentoAction());
    }

    private class BuscarHorariosAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                Especialidade esp = view.getEspecialidadeSelecionada();
                DayOfWeek dia = view.getDiaSelecionado();

                List<Medico> todosMedicos = GerenciadorRepository.getInstance().getMedicos();
                List<Object[]> linhasTabela = new ArrayList<>();

                slotsAtuaisNaTela.clear();
                for (Medico medico : todosMedicos) {

                    if (medico.isAtivo() && medico.getEspecialidade() == esp) {

                        List<LocalTime> horariosLivres = medico.getHorariosDisponiveis(dia);

                        for (LocalTime hora : horariosLivres) {

                            linhasTabela.add(new Object[]{
                                "Dr(a). " + medico.getNome().getSobrenome(),
                                medico.getEspecialidade(),
                                dia,
                                hora
                            });

                            slotsAtuaisNaTela.add(new SlotDisponibilidade(medico, dia, hora));
                        }
                    }
                }

                if (linhasTabela.isEmpty()) {
                    // Mensagem de depuração explicando por que nenhum horário foi encontrado
                    StringBuilder debug = new StringBuilder();
                    debug.append("Nenhum horário encontrado para essa especialidade neste dia.\n\n");
                    debug.append("Médicos verificados:\n");
                    for (Medico medico : todosMedicos) {
                        if (medico.isAtivo() && medico.getEspecialidade() == esp) {
                            List<LocalTime> horas = medico.getHorariosDisponiveis(dia);
                            debug.append("- ").append(medico.getNome().getNome()).append(" ").append(medico.getNome().getSobrenome())
                                    .append(" -> horários livres: ").append(horas.size()).append("\n");
                        }
                    }
                    JOptionPane.showMessageDialog(view, debug.toString());
                }

                view.preencherTabelaHorarios(linhasTabela);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Erro ao buscar horários: " + ex.getMessage());
            }
        }
    }

    private class ConfirmarAgendamentoAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int linha = view.getLinhaHorarioSelecionada();

            if (linha == -1) {
                JOptionPane.showMessageDialog(view, "Selecione um horário na tabela para agendar.");
                return;
            }

            SlotDisponibilidade slot = slotsAtuaisNaTela.get(linha);

            int confirm = JOptionPane.showConfirmDialog(view,
                    "Confirmar agendamento com " + slot.medico.getNome().getSobrenome() + " às " + slot.hora + "?",
                    "Confirmar", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    Consulta novaConsulta = new Consulta(slot.dia, slot.hora, paciente, slot.medico);

                    slot.medico.agendarConsulta(novaConsulta);

                    paciente.adicionarConsulta(novaConsulta);

                    GerenciadorRepository.getInstance().salvarMedicos();
                    GerenciadorRepository.getInstance().salvarPacientes();

                    JOptionPane.showMessageDialog(view, "Consulta agendada com sucesso!");

                    view.preencherTabelaHorarios(new ArrayList<>());
                    slotsAtuaisNaTela.clear();

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(view, "Erro ao agendar: " + ex.getMessage());
                }
            }
        }
    }

    private static class SlotDisponibilidade {

        Medico medico;
        DayOfWeek dia;
        LocalTime hora;

        public SlotDisponibilidade(Medico medico, DayOfWeek dia, LocalTime hora) {
            this.medico = medico;
            this.dia = dia;
            this.hora = hora;
        }
    }

    private void gerenciarMinhasConsultas() {
        List<Consulta> todasConsultas = paciente.getConsultas();

        List<Consulta> consultasMarcadas = new ArrayList<>();
        for (Consulta c : todasConsultas) {
            if (c.getEstadoConsulta() == EstadoConsulta.MARCADA) {
                consultasMarcadas.add(c);
            }
        }

        String[] colunas = {"Dia", "Hora", "Médico", "Especialidade", "Estado", "Avaliação"};

        DefaultTableModel model = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (Consulta c : consultasMarcadas) {
            model.addRow(new Object[]{
                c.getDiaConsulta(),
                c.getHorarioConsulta(),
                c.getNomeMedicoDisplay(),
                c.getEspecialidadeDisplay(),
                c.getEstadoConsulta(),
                c.getAvaliacaoSaude() == null ? "" : c.getAvaliacaoSaude()
            });
        }

        JTable table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(table);

        JButton btnCancelar = new JButton("Cancelar Selecionada");

        btnCancelar.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                Consulta consultaSelecionada = consultasMarcadas.get(row);

                cancelarConsulta(consultaSelecionada);
            } else {
                JOptionPane.showMessageDialog(view, "Selecione uma consulta para cancelar.");
            }
        });

        JPanel painel = new JPanel(new BorderLayout());
        painel.add(scrollPane, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel();
        btnPanel.add(btnCancelar);
        painel.add(btnPanel, BorderLayout.SOUTH);

        view.atualizarPainelCentral(painel);
    }

    private void cancelarConsulta(Consulta consulta) {

        if (consulta.getEstadoConsulta() != EstadoConsulta.MARCADA) {
            JOptionPane.showMessageDialog(
                    view,
                    "Apenas consultas MARCADAS podem ser canceladas.",
                    "Operação inválida",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                view,
                "Tem certeza que deseja cancelar esta consulta?",
                "Cancelar Consulta",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        Consulta consultaAtualizada = consulta.novoEstado("Cancelada");

        paciente.atualizarConsulta(consulta, consultaAtualizada);

        if (consulta.getMedico() != null) {
            consulta.getMedico().atualizarConsulta(consulta, consultaAtualizada);
        } else {
            List<Medico> todosMedicos = GerenciadorRepository.getInstance().getMedicos();
            String nomeMedicoAlvo = consulta.getNomeMedicoDisplay();

            for (Medico m : todosMedicos) {
                if (m.getNome().toString().equals(nomeMedicoAlvo)) {
                    m.atualizarConsulta(consulta, consultaAtualizada);
                    break;
                }
            }
        }

        GerenciadorRepository.getInstance().salvarMedicos();
        GerenciadorRepository.getInstance().salvarPacientes();
        JOptionPane.showMessageDialog(view, "Consulta cancelada com sucesso.");
        gerenciarMinhasConsultas();
    }

    private void mostrarHistoricoConsultas() {
        List<Consulta> consultas = new ArrayList<>(paciente.getConsultas());

        if (consultas.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Nenhuma consulta registrada.");
            return;
        }

        String[] colunas = {"Dia", "Hora", "Médico", "Especialidade", "Estado", "Avaliação"};

        DefaultTableModel model = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        consultas.sort((c1, c2) -> {
            int cmp = c2.getDiaConsulta().compareTo(c1.getDiaConsulta());
            if (cmp != 0) {
                return cmp;
            }
            return c2.getHorarioConsulta().compareTo(c1.getHorarioConsulta());
        });

        for (Consulta c : consultas) {
            model.addRow(new Object[]{
                c.getDiaConsulta(),
                c.getHorarioConsulta(),
                c.getNomeMedicoDisplay(),
                c.getEspecialidadeDisplay(),
                c.getEstadoConsulta(),
                c.getAvaliacaoSaude() == null ? "" : c.getAvaliacaoSaude()
            });
        }

        JTable tabela = new JTable(model);
        JScrollPane scroll = new JScrollPane(tabela);

        view.atualizarPainelCentral(scroll);
    }

    private void mostrarStatusInternacao() {

        List<Paciente> hospitalizados = GerenciadorRepository.getInstance().buscarHospitalizados();

        if (hospitalizados.isEmpty()) {
            JOptionPane.showMessageDialog(
                    view,
                    "Não há pacientes hospitalizados no momento.");
            return;
        }

        String[] colunas = {"Nome", "Pode receber visitas?"};
        DefaultTableModel model = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (Paciente p : hospitalizados) {
            model.addRow(new Object[]{
                p.getNome().getNome() + " " + p.getNome().getSobrenome(),
                p.isRecebeVisita() ? "Apto" : "Não apto"
            });
        }

        JTable tabela = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(tabela);

        view.atualizarPainelCentral(scrollPane);
    }

    @SuppressWarnings("UseSpecificCatch")
    private void mostrarMeusDados() {

        JPanel painel = new JPanel(new GridLayout(0, 2, 10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField txtEmail = new JTextField(paciente.getEmail().getEmail());
        JPasswordField txtSenha = new JPasswordField(paciente.getSenha().getSenha());
        JTextField txtContato = new JTextField(paciente.getContato().getNumero());

        JTextField txtCep = new JTextField(paciente.getEndereco().getCEP());
        JTextField txtRua = new JTextField(paciente.getEndereco().getRua());
        JTextField txtBairro = new JTextField(paciente.getEndereco().getBairro());
        JTextField txtCidade = new JTextField(paciente.getEndereco().getCidade());
        JTextField txtNumero = new JTextField(
                String.valueOf(paciente.getEndereco().getNumeroCasa()));

        JButton btnSalvar = new JButton("Salvar alterações");

        painel.add(new JLabel("Email:"));
        painel.add(txtEmail);

        painel.add(new JLabel("Senha:"));
        painel.add(txtSenha);

        painel.add(new JLabel("Contato:"));
        painel.add(txtContato);

        painel.add(new JLabel("CEP:"));
        painel.add(txtCep);

        painel.add(new JLabel("Rua:"));
        painel.add(txtRua);

        painel.add(new JLabel("Bairro:"));
        painel.add(txtBairro);

        painel.add(new JLabel("Cidade:"));
        painel.add(txtCidade);

        painel.add(new JLabel("Número:"));
        painel.add(txtNumero);

        painel.add(new JLabel());
        painel.add(btnSalvar);

        btnSalvar.addActionListener(e -> {
            try {
                alterarEmail(paciente, txtEmail.getText());
                alterarSenha(paciente, new String(txtSenha.getPassword()));
                alterarContato(paciente, txtContato.getText());

                int numeroCasa = Integer.parseInt(txtNumero.getText());

                alterarEndereco(
                        paciente,
                        txtCep.getText(),
                        txtRua.getText(),
                        txtBairro.getText(),
                        txtCidade.getText(),
                        numeroCasa);

                JOptionPane.showMessageDialog(
                        view,
                        "Dados atualizados com sucesso!");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(
                        view,
                        "Erro ao salvar dados: " + ex.getMessage());
            }
        });

        view.atualizarPainelCentral(painel);
    }

    // Buscas
    public List<Paciente> buscarTodosPacientes() {
        return GerenciadorRepository.getInstance().getPacientes();
    }

    public List<Paciente> buscarPacientesHospitalizados() {
        return GerenciadorRepository.getInstance().buscarHospitalizados();
    }

    public List<Paciente> buscarRecebemVisitas() {
        return GerenciadorRepository.getInstance().buscarRecebemVisitas();
    }

    public boolean existePacienteVisitavel(String nome, String sobrenome) {
        return GerenciadorRepository.getInstance().existePacienteVisitavel(nome, sobrenome);
    }

    private class SairListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            voltarParaLogin();
        }
    }

    // listener para meus documentos
    private class MeusDocumentosListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            mostrarDocumentos();
        }
    }

    private void mostrarDocumentos() {

        List<DocumentoMedico> documentos = paciente.getDocumentos();

        if (documentos.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Nenhum documento disponível.");
            return;
        }

        DefaultListModel<String> model = new DefaultListModel<>();

        for (DocumentoMedico doc : documentos) {
            model.addElement(
                    "Data: " + doc.getData()
                    + " | Médico: " + doc.getNomeMedico()
            );
        }

        JList<String> lista = new JList<>(model);
        JScrollPane scroll = new JScrollPane(lista);

        lista.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                DocumentoMedico doc = documentos.get(lista.getSelectedIndex());

                JOptionPane.showMessageDialog(
                        view,
                        doc.getTexto(),
                        "Documento Médico",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        });

        JOptionPane.showMessageDialog(
                view,
                scroll,
                "Meus Documentos",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void voltarParaLogin() {
        LoginView loginView = new LoginView();
        new LoginController(loginView);
        loginView.setVisible(true);
        if (view != null) {
            view.dispose();
        }
    }
}
