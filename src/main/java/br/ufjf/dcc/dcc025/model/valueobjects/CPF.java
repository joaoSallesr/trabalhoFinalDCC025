package br.ufjf.dcc.dcc025.model.valueobjects;

import br.ufjf.dcc.dcc025.model.exception.InvalidCPFException;

public class CPF {
    private final String cpf;

    public CPF getInstance(String cpf) {
        return new CPF(cpf);
    }

    private CPF(String cpf) {
        if(!isValid(cpf))
            throw new InvalidCPFException("Invalid CPF: " + cpf);
        this.cpf = cpf;
    }

    private boolean isValid(String cpf) {
        // lógica de validação do CPF
        return true;
    }

    public String getCPF() {
        return cpf;
    }
}
