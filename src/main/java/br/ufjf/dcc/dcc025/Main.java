package br.ufjf.dcc.dcc025;

import br.ufjf.dcc.dcc025.controller.LoginController;
import br.ufjf.dcc.dcc025.controller.PacienteController;
import br.ufjf.dcc.dcc025.model.Paciente;
import br.ufjf.dcc.dcc025.view.LoginView;
import br.ufjf.dcc.dcc025.view.PacienteView;

public class Main {

    public static void main(String[] args) {
        //GerenciadorRepository repo = GerenciadorRepository.getInstance();

        /*
                  Paciente paciente = repo.getPacientes().get(0);
                 PacienteView view1 = new PacienteView();
                 new PacienteController(paciente, view1);
                 */
         

 /*
                 Recepcionista recepcionista = repo.getRecepcionistas().get(0);
                 RecepcionistaView view = new RecepcionistaView();
                 new RecepcionistaController(recepcionista, view);
         */
 /*                Medico medico = repo.getMedicos().get(0);
                Medico medico = repo.getMedicos().get(0);
                MedicoView view = new MedicoView();
                new MedicoController(medico, view);
         */
        LoginView view = new LoginView();
        new LoginController(view);
        view.setVisible(true);
    }
}

// new RecepcionistaView().setVisible(true);
// LoginView view = new LoginView();
// new LoginController(view);
// view.setVisible(true);
