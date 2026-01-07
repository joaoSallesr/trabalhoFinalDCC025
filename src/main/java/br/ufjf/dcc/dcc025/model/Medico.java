package br.ufjf.dcc.dcc025.model;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

import br.ufjf.dcc.dcc025.model.exception.InvalidWorkingTimeException;
import br.ufjf.dcc.dcc025.model.valueobjects.CPF;
import br.ufjf.dcc.dcc025.model.valueobjects.Email;
import br.ufjf.dcc.dcc025.model.valueobjects.Especialidade;
import br.ufjf.dcc.dcc025.model.valueobjects.HorarioTrabalho;
import br.ufjf.dcc.dcc025.model.valueobjects.Nome;
import br.ufjf.dcc.dcc025.model.valueobjects.Senha;

public class Medico extends Usuario {

    private final Especialidade especialidade;
    private List<HorarioTrabalho> horariosTrabalho;

    public Medico(Nome nome, CPF cpf, Email email, Senha password, Especialidade especialidade) {
        super(nome, cpf, email, password);
        this.especialidade = Objects.requireNonNull(especialidade, "Especialidade obrigatória.");
    }

    // Alterações
    public void adicionarHorario(HorarioTrabalho novoHorario) {
        for (HorarioTrabalho horario : horariosTrabalho) {
            if (horario.getDiaTrabalho() == novoHorario.getDiaTrabalho()) {
                throw new InvalidWorkingTimeException("Já exite um horário de trabalho para esse dia da semana.");
            }
        }
        this.horariosTrabalho.add(novoHorario);
    }

    public void removerHorario(HorarioTrabalho horario) {
        this.horariosTrabalho.remove(horario);
    }

    // Buscas
    public boolean estaTrabalhando(DayOfWeek dia, LocalTime hora) {
        for (HorarioTrabalho horario : horariosTrabalho) {
            if (horario.getDiaTrabalho() != dia
                    || horario.getHorarioComeco().isAfter(hora)
                    || horario.getHorarioFinal().isBefore(hora)) {
                return false;
            }
        }
        return true;
    }

    // Getters
    public Especialidade getEspecialidade() {
        return this.especialidade;
    }
}
