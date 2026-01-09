package br.ufjf.dcc.dcc025;

import br.ufjf.dcc.dcc025.model.Paciente;
import br.ufjf.dcc.dcc025.model.Recepcionista;
import br.ufjf.dcc.dcc025.model.dto.DadosPaciente;
import br.ufjf.dcc.dcc025.model.valueobjects.CPF;
import br.ufjf.dcc.dcc025.model.valueobjects.Contato;
import br.ufjf.dcc.dcc025.model.valueobjects.Email;
import br.ufjf.dcc.dcc025.model.valueobjects.Endereco;
import br.ufjf.dcc.dcc025.model.valueobjects.Nome;
import br.ufjf.dcc.dcc025.view.RecepcionistaView;

public class Main {

    public static void main(String[] args) {
        System.out.println("Teste");
        //new RecepcionistaView().setVisible(true);

        // TESTE
        
        Nome recNome = new Nome("Teste", "Recepcionista");
        CPF recCPF = new CPF("12345678910");
        Email recEmail = new Email("rec.teste@gmail.com");
        
        DadosPaciente novoDados = new DadosPaciente("João", "Salles", "12345678910", 
        "joao.teste@gmail.com", "32999881234", 
        "12345678", "rua teste", "bairro teste", "cidade teste", 12);

        Recepcionista novoRecepcionista = new Recepcionista(recNome, recCPF, recEmail);
        //novoRecepcionista.cadastrarPaciente(novoDados);
        novoRecepcionista.carregarLista();
        
    }
}
