package br.ufjf.dcc.dcc025.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import br.ufjf.dcc.dcc025.model.Medico;
import br.ufjf.dcc.dcc025.model.Paciente;
import br.ufjf.dcc.dcc025.model.Recepcionista;
import br.ufjf.dcc.dcc025.model.Usuario;
import br.ufjf.dcc.dcc025.model.repository.GerenciadorRepository;
import br.ufjf.dcc.dcc025.view.LoginView;
import br.ufjf.dcc.dcc025.view.MedicoView;
import br.ufjf.dcc.dcc025.view.PacienteView;
import br.ufjf.dcc.dcc025.view.RecepcionistaView;

public class LoginController {

    private final LoginView view;
    private final GerenciadorRepository repository;

    public LoginController(LoginView view) {
        this.view = view;
        this.repository = GerenciadorRepository.getInstance();

        // View avisa o Controller quando o usuário clica em "Login"
        this.view.addLoginListener(new LoginListener());
    }

    private class LoginListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            realizarLogin();
        }
    }

    // Realiza o processo de login
    private void realizarLogin() {

        String emailDigitado = view.getUsuario();
        String senhaDigitada = view.getSenha();

        if (emailDigitado.isBlank() || senhaDigitada.isBlank()) {
            view.mostrarMensagem("Preencha email e senha.");
            return;
        }

        Usuario usuario = autenticar(emailDigitado, senhaDigitada);

        if (usuario == null) {
            view.mostrarMensagem("Email ou senha inválidos.");
            return;
        }
        if (!usuario.isAtivo()) {
            view.mostrarMensagem("Usuário desativado. Procure a recepção.");
            return;
        }

        abrirTelaUsuario(usuario);
    }

    // Autentica o usuário nos repositórios
    private Usuario autenticar(String email, String senhaDigitada) {

        for (Medico medico : repository.getMedicos()) {
            if (credenciaisValidas(medico, email, senhaDigitada)) {
                return medico;
            }
        }

        for (Recepcionista recep : repository.getRecepcionistas()) {
            if (credenciaisValidas(recep, email, senhaDigitada)) {
                return recep;
            }
        }

        for (Paciente paciente : repository.getPacientes()) {
            if (credenciaisValidas(paciente, email, senhaDigitada)) {
                return paciente;
            }
        }

        return null;
    }

    // Verifica se o email e senha são válidos
    private boolean credenciaisValidas(Usuario usuario, String email, String senhaDigitada) {
        if (usuario.getEmail() == null || usuario.getSenha() == null) {
            return false;
        }

        return usuario.getEmail().getEmail().equalsIgnoreCase(email)
                && usuario.getSenha().getSenha().equals(senhaDigitada);
    }

    // Abre a tela correspondente ao tipo de usuário
    private void abrirTelaUsuario(Usuario usuario) {

        switch (usuario) {
            case Medico medico -> {
                MedicoView medicoView = new MedicoView();
                new MedicoController(medico, medicoView);
                medicoView.setVisible(true);
            }
            case Recepcionista recep -> {
                RecepcionistaView recepcionistaView = new RecepcionistaView();
                new RecepcionistaController(recep, recepcionistaView);
                recepcionistaView.setVisible(true);
            }
            case Paciente paciente -> {
                PacienteView pacienteView = new PacienteView();
                new PacienteController(paciente, pacienteView);
                pacienteView.setVisible(true);
            }
            default -> {
            }
        }

        this.view.dispose();
    }
}
