package br.ufjf.dcc.dcc025.model.valueobjects;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Objects;

import br.ufjf.dcc.dcc025.model.exception.InvalidWorkingTimeException;

public class HorarioTrabalho {

    private final DayOfWeek diaTrabalho; // Número (1 a 7 = Segunda a Domingo)
    private final LocalTime horarioComeco; // Número (aa, bb) = aa:bb
    private final LocalTime horarioFinal;

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
        if (horarioComeco.getMinute() != 0 || horarioFinal.getMinute() != 0) {
            throw new InvalidWorkingTimeException(
                    "Apenas horas cheias são permitidas (ex: 14:00, 15:00). Valores recebidos: "
                    + horarioComeco + " - " + horarioFinal
            );
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

    // Overrides
    @Override
    public String toString() {
        return diaTrabalho + ": " + horarioComeco + " - " + horarioFinal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HorarioTrabalho that = (HorarioTrabalho) o;
        return diaTrabalho == that.diaTrabalho
                && Objects.equals(horarioComeco, that.horarioComeco)
                && Objects.equals(horarioFinal, that.horarioFinal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(diaTrabalho, horarioComeco, horarioFinal);
    }
}
