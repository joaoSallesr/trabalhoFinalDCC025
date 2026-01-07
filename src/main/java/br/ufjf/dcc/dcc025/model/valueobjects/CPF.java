package br.ufjf.dcc.dcc025.model.valueobjects;

public class CPF {

    private final String cpf;

    public CPF(String cpf) {
        validaCPF(cpf);
        this.cpf = cpf;
    }

    // Validação
    private static void validaCPF(String cpf) {
        // implementar lógica de validação do CPF
        //throw new InvalidCPFException("CPF inválido: " + cpf);
    }

    // Getters
    public String getCPF() {
        return cpf;
    }
}
