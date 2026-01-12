package br.ufjf.dcc.dcc025;

import br.ufjf.dcc.dcc025.controller.LoginController;
import br.ufjf.dcc.dcc025.view.LoginView;

public class Main {

    public static void main(String[] args) {
        LoginView view = new LoginView();
        new LoginController(view);
        view.setVisible(true);
    }
}
