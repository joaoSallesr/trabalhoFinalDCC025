package br.ufjf.dcc.dcc025.model;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import br.ufjf.dcc.dcc025.model.dto.DadosMedico;
import br.ufjf.dcc.dcc025.model.exception.InvalidWorkingTimeException;
import br.ufjf.dcc.dcc025.model.valueobjects.CPF;
import br.ufjf.dcc.dcc025.model.valueobjects.Email;
import br.ufjf.dcc.dcc025.model.valueobjects.Especialidade;
import br.ufjf.dcc.dcc025.model.valueobjects.HorarioTrabalho;
import br.ufjf.dcc.dcc025.model.valueobjects.Nome;
import br.ufjf.dcc.dcc025.model.valueobjects.Senha;

public class Medico extends Usuario {

    private Especialidade especialidade;
    private final List<HorarioTrabalho> agenda;

    public Medico(DadosMedico dados) {
        super(
                new Nome(dados.getNome(), dados.getSobrenome()),
                new CPF(dados.getCPF()),
                new Senha(dados.getSenha()),
                new Email(dados.getEmail())
        );
        this.especialidade = Objects.requireNonNull(
                Especialidade.fromString(dados.getEspecialidade()),
                "Especialidade obrigatória.");
        this.agenda = new ArrayList<>();
    }

    // Alteração de atributos
    public void adicionarHorario(HorarioTrabalho novoHorario) {
        for (HorarioTrabalho horario : agenda) {
            if (horario.getDiaTrabalho() == novoHorario.getDiaTrabalho()) {
                throw new InvalidWorkingTimeException("Já exite um horário de trabalho para esse dia da semana.");
            }
        }
        this.agenda.add(novoHorario);
    }

    public void removerHorario(HorarioTrabalho horario) {
        this.agenda.remove(horario);
    }

    public void alterarEspecialidade(Especialidade novaEspecialidade) {
        this.especialidade = Objects.requireNonNull(
                novaEspecialidade, "Nova especialidade obrigatória.");
    }

    // Buscas
    public boolean estaTrabalhando(DayOfWeek dia, LocalTime hora) {
        for (HorarioTrabalho horario : this.agenda) {
            if (horario.getDiaTrabalho() == dia) {
                boolean horarioValido = !hora.isBefore(horario.getHorarioComeco())
                        && !hora.isAfter(horario.getHorarioFinal());
                if (horarioValido) {
                    return true;
                }
            }
        }
        return false;
    }

    // Getters
    public Especialidade getEspecialidade() {
        return this.especialidade;
    }

    public List<HorarioTrabalho> getAgenda() {
        return this.agenda;
    }
}
