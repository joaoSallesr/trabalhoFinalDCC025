package br.ufjf.dcc.dcc025;

import br.ufjf.dcc.dcc025.model.repository.GerenciadorRepository;
import br.ufjf.dcc.dcc025.view.LoginView;

public class Main {

        public static void main(String[] args) {
                System.out.println("Teste");
                GerenciadorRepository.getInstance(); // Cria o repositório para o sistema atual
                // new RecepcionistaView().setVisible(true);
                new LoginView().setVisible(true);

                /*
                 * // TESTE DE PERSISTÊNCIA
                 * Nome recNome = new Nome("Teste", "Recepcionista");
                 * CPF recCPF = new CPF("12345678910");
                 * Email recEmail = new Email("rec.teste@gmail.com");
                 * 
                 * DadosPaciente novoPaciente1 = new DadosPaciente("João", "Salles",
                 * "12345678910",
                 * "joao.teste@gmail.com", "32999881234",
                 * "12345678", "rua teste", "bairro teste", "cidade teste", 12);
                 * 
                 * DadosPaciente novoPaciente2 = new DadosPaciente("Tom", "Jobim",
                 * "12345678910",
                 * "tom.jobim@gmail.com", "32999123456",
                 * "12345-678", "rua teste", "bairro teste", "cidade teste", 13);
                 * 
                 * DadosMedico novoMedico1 = new DadosMedico("Doutor", "House", "11111111111",
                 * "house123@gmail.com", "Clínico");
                 * 
                 * DadosMedico novoMedico2 = new DadosMedico("Jose", "Mauro", "22222222222",
                 * "jose.mauro@yahoo.com", "Pediatra");
                 * 
                 * DadosRecepcionista novoRecepcionista1 = new DadosRecepcionista("Thiago",
                 * "Silva", "12345678910", "Thiago45@gmail.com");
                 * 
                 * Recepcionista novoRecepcionista = new Recepcionista(recNome, recCPF,
                 * recEmail);
                 * novoRecepcionista.cadastrarPaciente(novoPaciente1);
                 * novoRecepcionista.cadastrarPaciente(novoPaciente2);
                 * novoRecepcionista.cadastrarMedico(novoMedico1);
                 * novoRecepcionista.cadastrarMedico(novoMedico2);
                 * novoRecepcionista.cadastrarRecepcionista(novoRecepcionista1);
                 */
        }
}
