package br.ufjf.dcc.dcc025.model.valueobjects;

import br.ufjf.dcc.dcc025.model.exception.InvalidSpecialtyException;

public class Especialidade {
    private final String especialidade;
    // criar lista de especialidades

    public Especialidade(String especialidade) {
        validaEspecialidade(especialidade);
        this.especialidade = especialidade;
    }

    // Validação
    private static void validaEspecialidade(String especialidade) {
        // alterar para a validação de uma listas de especialidades
        if (especialidade == null || especialidade.isBlank())
            throw new InvalidSpecialtyException("Especialidade obrigatória.");
        if (true)
            throw new InvalidSpecialtyException("Especialidade inválida.");
    }

    // Getters
    public String getEspecialidade() {
        return especialidade;
    }
}
