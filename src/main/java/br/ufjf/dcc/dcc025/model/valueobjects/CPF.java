package br.ufjf.dcc.dcc025.model.valueobjects;

import br.ufjf.dcc.dcc025.model.exception.InvalidCPFException;

public class CPF {
    private final String cpf;

    public CPF(String cpf) {
        if(!isValid(cpf))
            throw new InvalidCPFException("CPF inválido: " + cpf);
        this.cpf = cpf;
    }

    private static boolean isValid(String cpf) {
        // implementar lógica de validação do CPF
        return true;
    }

    public String getCPF() {
        return cpf;
    }
}
