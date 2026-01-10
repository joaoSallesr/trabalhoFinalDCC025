package br.ufjf.dcc.dcc025.model;

import java.time.DayOfWeek;
import java.time.LocalTime;
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
    private List<HorarioTrabalho> horariosTrabalho;

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
    }

    // Alteração de atributos
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

    public void alterarEspecialidade(Especialidade novaEspecialidade) {
        this.especialidade = Objects.requireNonNull(
                novaEspecialidade, "Nova especialidade obrigatória.");
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
