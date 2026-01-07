package br.ufjf.dcc.dcc025.model.valueobjects;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Objects;

import br.ufjf.dcc.dcc025.model.exception.InvalidWorkingTimeException;

public class HorarioTrabalho {

    private DayOfWeek diaTrabalho; // Número (1 a 7 = Segunda a Domingo)
    private LocalTime horarioComeco; // Número (aa, bb) = aa:bb
    private LocalTime horarioFinal;

    public HorarioTrabalho(DayOfWeek diaTrabalho, LocalTime horarioComeco, LocalTime horarioFinal) {
        validaHorario(horarioComeco, horarioFinal);

        this.diaTrabalho = Objects.requireNonNull(diaTrabalho, "Dia de trabalho obrigatório.");
        this.horarioComeco = Objects.requireNonNull(horarioComeco, "Horário de começo obrigatório.");
        this.horarioFinal = Objects.requireNonNull(horarioFinal, "Horário de final obrigatório.");
    }

    // Validação
    private static void validaHorario(LocalTime horarioComeco, LocalTime horarioFinal) {
        if (horarioComeco.isAfter(horarioFinal)) {
            throw new InvalidWorkingTimeException("Horário de trabalho inválido:" + horarioComeco + " - " + horarioFinal);
        }
    }

    // Getters
    public DayOfWeek getDiaTrabalho() {
        return diaTrabalho;
    }

    public LocalTime getHorarioComeco() {
        return horarioComeco;
    }

    public LocalTime getHorarioFinal() {
        return horarioFinal;
    }
}
