package br.ufjf.dcc.dcc025.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import br.ufjf.dcc.dcc025.model.Recepcionista;
import br.ufjf.dcc.dcc025.model.dto.DadosRecepcionista;
import br.ufjf.dcc.dcc025.model.repository.GerenciadorRepository;
import br.ufjf.dcc.dcc025.view.LoginView;
import br.ufjf.dcc.dcc025.view.RecepcionistaView;

public class RecepcionistaController {

    private final Recepcionista recepcionista;
    private final RecepcionistaView view;

    public RecepcionistaController(Recepcionista recepcionista, RecepcionistaView view) {
        this.recepcionista = recepcionista;
        this.view = view;

        if (this.view != null) {
            this.view.addSairListener(new SairListener());
        }
    }

    public void cadastrarRecepcionista(DadosRecepcionista dados) {
        Recepcionista novoRecepcionista = new Recepcionista(dados);
        GerenciadorRepository.getInstance().adicionarRecepcionista(novoRecepcionista);
    }

    private class SairListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            voltarParaLogin();
        }
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
