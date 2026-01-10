package br.ufjf.dcc.dcc025;

import br.ufjf.dcc.dcc025.controller.LoginController;
import br.ufjf.dcc.dcc025.controller.MedicoController;
import br.ufjf.dcc.dcc025.model.repository.GerenciadorRepository;
import br.ufjf.dcc.dcc025.view.LoginView;
import br.ufjf.dcc.dcc025.view.MedicoView;

public class Main {

        public static void main(String[] args) {
                System.out.println("Teste");
                GerenciadorRepository.getInstance(); // Cria o repositório para o sistema atual
                LoginView view = new LoginView();
                new LoginController(view);
                view.setVisible(true);

        }
}

// new RecepcionistaView().setVisible(true);
// LoginView view = new LoginView();
// new LoginController(view);
// view.setVisible(true);
