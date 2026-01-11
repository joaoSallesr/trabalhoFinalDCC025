package br.ufjf.dcc.dcc025.model.valueobjects;

import org.junit.Test;
import static org.junit.Assert.*;

import java.time.DayOfWeek;
import java.time.LocalTime;

import br.ufjf.dcc.dcc025.model.exception.InvalidWorkingTimeException;

public class HorarioTrabalhoTest {

    @Test
    public void deveCriarHorarioTrabalhoValido() {
        HorarioTrabalho horario = new HorarioTrabalho(
                DayOfWeek.MONDAY,
                LocalTime.of(8, 0),
                LocalTime.of(12, 0));

        assertEquals(DayOfWeek.MONDAY, horario.getDiaTrabalho());
        assertEquals(LocalTime.of(8, 0), horario.getHorarioComeco());
        assertEquals(LocalTime.of(12, 0), horario.getHorarioFinal());
    }

    @Test
    public void horariosIguaisSaoConsideradosIguais() {
        HorarioTrabalho h1 = new HorarioTrabalho(
                DayOfWeek.TUESDAY,
                LocalTime.of(14, 0),
                LocalTime.of(18, 0));

        HorarioTrabalho h2 = new HorarioTrabalho(
                DayOfWeek.TUESDAY,
                LocalTime.of(14, 0),
                LocalTime.of(18, 0));

        assertEquals(h1, h2);
        assertEquals(h1.hashCode(), h2.hashCode());
    }

    @Test(expected = InvalidWorkingTimeException.class)
    public void naoDeveAceitarHorarioComecoDepoisDoFinal() {
        new HorarioTrabalho(
                DayOfWeek.WEDNESDAY,
                LocalTime.of(18, 0),
                LocalTime.of(12, 0));
    }

    @Test(expected = InvalidWorkingTimeException.class)
    public void naoDeveAceitarHorarioComMinutosDiferentesDeZero() {
        new HorarioTrabalho(
                DayOfWeek.THURSDAY,
                LocalTime.of(8, 30),
                LocalTime.of(12, 0));
    }

    @Test(expected = InvalidWorkingTimeException.class)
    public void naoDeveAceitarHorarioFinalComMinutosDiferentesDeZero() {
        new HorarioTrabalho(
                DayOfWeek.FRIDAY,
                LocalTime.of(8, 0),
                LocalTime.of(12, 45));
    }
}
