package br.ufjf.dcc.dcc025.model.valueobjects;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Objects;

import br.ufjf.dcc.dcc025.model.Medico;
import br.ufjf.dcc.dcc025.model.Paciente;

public class Consulta {

    private final transient Paciente paciente;
    private final transient Medico medico;
    private final DayOfWeek diaConsulta;
    private final LocalTime horarioConsulta;
    private final EstadoConsulta estadoConsulta;

    // Criados para não corromper o json
    private final String nomePacienteDisplay;
    private final String nomeMedicoDisplay;
    private final String especialidadeDisplay;

    public Consulta(DayOfWeek diaConsulta, LocalTime horarioConsulta, Paciente paciente, Medico medico) {
        this.diaConsulta = Objects.requireNonNull(diaConsulta, "Dia obrigatório");
        this.horarioConsulta = Objects.requireNonNull(horarioConsulta, "Horário obrigatório");
        this.paciente = Objects.requireNonNull(paciente, "Paciente obrigatório");
        this.medico = Objects.requireNonNull(medico, "Médico obrigatório");
        this.estadoConsulta = EstadoConsulta.MARCADA;

        this.nomePacienteDisplay = paciente.getNome().getNome() + " " + paciente.getNome().getSobrenome();
        this.nomeMedicoDisplay = medico.getNome().getSobrenome();
        this.especialidadeDisplay = medico.getEspecialidade().toString();
    }

    private Consulta(DayOfWeek dia, LocalTime hora, Paciente p, Medico m, EstadoConsulta estado, String nP, String nM, String esp) {
        this.diaConsulta = dia;
        this.horarioConsulta = hora;
        this.paciente = p;
        this.medico = m;
        this.estadoConsulta = estado;

        this.nomePacienteDisplay = nP;
        this.nomeMedicoDisplay = nM;
        this.especialidadeDisplay = esp;
    }

    public Consulta novoEstado(String novoEstadoString) {
        EstadoConsulta novoEstadoEnum = EstadoConsulta.fromString(novoEstadoString);

        return new Consulta(
                this.diaConsulta,
                this.horarioConsulta,
                this.paciente,
                this.medico,
                novoEstadoEnum,
                this.nomePacienteDisplay,
                this.nomeMedicoDisplay,
                this.especialidadeDisplay
        );
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public Medico getMedico() {
        return medico;
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

    public String getNomePacienteDisplay() {
        if (paciente != null) {
            return paciente.getNome().getNome() + " " + paciente.getNome().getSobrenome();
        }
        return nomePacienteDisplay;
    }

    public String getNomeMedicoDisplay() {
        if (medico != null) {
            return medico.getNome().getSobrenome();
        }
        return nomeMedicoDisplay;
    }

    public String getEspecialidadeDisplay() {
        if (medico != null) {
            return medico.getEspecialidade().toString();
        }
        return especialidadeDisplay;
    }
}
