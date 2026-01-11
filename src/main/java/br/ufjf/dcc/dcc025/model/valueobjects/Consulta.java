package br.ufjf.dcc.dcc025.model.valueobjects;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Objects;

import br.ufjf.dcc.dcc025.model.Paciente;

public class Consulta {

    private final Paciente paciente;
    private final DayOfWeek diaConsulta;
    private final LocalTime horarioConsulta;
    private final EstadoConsulta estadoConsulta;

    public Consulta(Paciente paciente, DayOfWeek diaConsulta, LocalTime horarioConsulta, String estadoConsulta) {
        this.paciente = Objects.requireNonNull(paciente, "Paciente obrigatório.");
        this.diaConsulta = Objects.requireNonNull(diaConsulta, "Dia da consulta obrigatório.");
        this.horarioConsulta = Objects.requireNonNull(horarioConsulta, "Horário da consulta obrigatório.");
        this.estadoConsulta = Objects.requireNonNull(EstadoConsulta.fromString(estadoConsulta), "Estado da consulta obrigatório.");
    }

    public Consulta novoEstado(String novoEstado) {
        return new Consulta(this.paciente, this.diaConsulta, this.horarioConsulta, novoEstado);
    }

    // Getters
    public Paciente getPaciente() {
        return paciente;
    }

    public DayOfWeek getDiaConsulta() {
        return diaConsulta;
    }

    public LocalTime getHorarioConsulta() {
        return horarioConsulta;
    }

    public EstadoConsulta getEstadoConsulta() {
        return estadoConsulta;
    }
}
