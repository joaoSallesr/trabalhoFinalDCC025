package br.ufjf.dcc.dcc025.model.valueobjects;

import java.text.Collator;
import java.util.Locale;

import br.ufjf.dcc.dcc025.model.exception.InvalidSpecialtyException;

public enum EstadoConsulta {
    MARCADA("Marcada"),
    CANCELADA("Cancelada"),
    EFETUADA("Efetuada"),
    AUSENTE("Ausente");

    private final String descricao;

    private static final Collator COLLATOR = Collator.getInstance(Locale.forLanguageTag("pt-BR"));

    static {
        COLLATOR.setStrength(Collator.PRIMARY);
    }

    EstadoConsulta(String descricao) {
        this.descricao = descricao;
    }

    // Validação
    public static EstadoConsulta fromString(String entrada) {
        if (entrada == null || entrada.isBlank()) {
            throw new InvalidSpecialtyException("Estado obrigatório.");
        }

        for (EstadoConsulta estadoConsulta : EstadoConsulta.values()) {
            if (COLLATOR.compare(estadoConsulta.descricao, entrada) == 0
                    || COLLATOR.compare(estadoConsulta.name(), entrada) == 0) {
                return estadoConsulta;
            }
        }
        throw new IllegalArgumentException("Estado inválido: " + entrada);
    }

    // Getters
    public String getDescricao() {
        return descricao;
    }

    // Overrides
    @Override
    public String toString() {
        return descricao;
    }
}
