package br.ufjf.dcc.dcc025;

//import br.ufjf.dcc.dcc025.controller.LoginController;
//import br.ufjf.dcc.dcc025.controller.MedicoController;
import br.ufjf.dcc.dcc025.controller.PacienteController;
import br.ufjf.dcc.dcc025.model.Paciente;
import br.ufjf.dcc.dcc025.model.repository.GerenciadorRepository;
//import br.ufjf.dcc.dcc025.view.LoginView;
//import br.ufjf.dcc.dcc025.view.MedicoView;
import br.ufjf.dcc.dcc025.view.PacienteView;

public class Main {

        public static void main(String[] args) {
                GerenciadorRepository repo = GerenciadorRepository.getInstance();

                // pega qualquer paciente cadastrado
                if (repo.getPacientes().isEmpty()) {
                        System.out.println("Nenhum paciente cadastrado!");
                        return;
                }

                Paciente paciente = repo.getPacientes().get(0);

                PacienteView view = new PacienteView();
                new PacienteController(paciente, view);

                view.setVisible(true);
        }
}

// new RecepcionistaView().setVisible(true);
// LoginView view = new LoginView();
// new LoginController(view);
// view.setVisible(true);
