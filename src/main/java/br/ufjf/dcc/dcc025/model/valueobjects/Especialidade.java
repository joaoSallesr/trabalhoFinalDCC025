package br.ufjf.dcc.dcc025.model.valueobjects;

import java.text.Collator;
import java.util.Locale;

import br.ufjf.dcc.dcc025.model.exception.InvalidSpecialtyException;

public enum Especialidade {
    // Definir especialidades
    CARDIOLOGISTA("Cardiologista"),
    PEDIATRA("Pediatra"),
    CLINICO("Clínico");

    private final String descricao;

    private static final Collator COLLATOR = Collator.getInstance(Locale.forLanguageTag("pt-BR"));

    static {
        COLLATOR.setStrength(Collator.PRIMARY);
    }

    Especialidade(String descricao) {
        this.descricao = descricao;
    }

    // Validação
    public static Especialidade fromString(String entrada) {
        if (entrada == null || entrada.isBlank()) {
            throw new InvalidSpecialtyException("Especialidade obrigatória.");
        }

        for (Especialidade especialidade : Especialidade.values()) {
            if (COLLATOR.compare(especialidade.descricao, entrada) == 0
                    || COLLATOR.compare(especialidade.name(), entrada) == 0) {
                return especialidade;
            }
        }
        throw new InvalidSpecialtyException("Especialidade inválida: " + entrada);
    }

    // Getters
    public String getDescricao() {
        return descricao;
    }
}
