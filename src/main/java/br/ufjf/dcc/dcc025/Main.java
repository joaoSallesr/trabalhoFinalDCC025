package br.ufjf.dcc.dcc025;

import br.ufjf.dcc.dcc025.model.repository.GerenciadorRepository;
import br.ufjf.dcc.dcc025.view.RecepcionistaView;

public class Main {

        public static void main(String[] args) {
                System.out.println("Teste");
                GerenciadorRepository.getInstance(); // Cria o repositório para o sistema atual
                new RecepcionistaView().setVisible(true);
                //new LoginView().setVisible(true);
        }
}
