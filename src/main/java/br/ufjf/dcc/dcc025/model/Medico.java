package br.ufjf.dcc.dcc025.model;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.stream.Collectors;

import br.ufjf.dcc.dcc025.model.dto.DadosMedico;
import br.ufjf.dcc.dcc025.model.exception.InvalidWorkingTimeException;
import br.ufjf.dcc.dcc025.model.valueobjects.CPF;
import br.ufjf.dcc.dcc025.model.valueobjects.Consulta;
import br.ufjf.dcc.dcc025.model.valueobjects.Email;
import br.ufjf.dcc.dcc025.model.valueobjects.Especialidade;
import br.ufjf.dcc.dcc025.model.valueobjects.EstadoConsulta;
import br.ufjf.dcc.dcc025.model.valueobjects.HorarioTrabalho;
import br.ufjf.dcc.dcc025.model.valueobjects.Nome;
import br.ufjf.dcc.dcc025.model.valueobjects.Senha;

public class Medico extends Usuario {

    private Especialidade especialidade;
    private final List<HorarioTrabalho> agenda;
    private final Map<DayOfWeek, List<Consulta>> consultasSemana;

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
        this.consultasSemana = new TreeMap<>();
    }

    // Alteração de atributos
    // Especialidade
    public void alterarEspecialidade(Especialidade novaEspecialidade) {
        this.especialidade = Objects.requireNonNull(
                novaEspecialidade, "Nova especialidade obrigatória.");
    }

    // Gerenciador de horários de trabalho
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

    // Gestão de consulta
    public List<LocalTime> getHorariosDisponiveis(DayOfWeek dia) {
        HorarioTrabalho turno = agenda.stream()
                .filter(h -> h.getDiaTrabalho() == dia)
                .findFirst()
                .orElse(null);

        if (turno == null) {
            return Collections.emptyList();
        }

        List<Consulta> consultasDoDia = getConsultasMap().getOrDefault(dia, new ArrayList<>());

        List<LocalTime> horariosOcupados = consultasDoDia.stream()
                .filter(c -> c.getEstadoConsulta() != EstadoConsulta.CANCELADA)
                .map(Consulta::getHorarioConsulta)
                .collect(Collectors.toList());

        List<LocalTime> horariosLivres = new ArrayList<>();
        LocalTime cursor = turno.getHorarioComeco();
        LocalTime fim = turno.getHorarioFinal();

        while (cursor.isBefore(fim)) {
            if (!horariosOcupados.contains(cursor)) {
                horariosLivres.add(cursor);
            }
            cursor = cursor.plusMinutes(60);
        }

        return horariosLivres;
    }

    public void agendarConsulta(Consulta consulta) {
        DayOfWeek dia = consulta.getDiaConsulta();
        LocalTime hora = consulta.getHorarioConsulta();

        if (!estaTrabalhando(dia, hora)) {
            throw new InvalidWorkingTimeException("Médico não atende neste dia ou horário.");
        }

        if (hora.getMinute() != 0) {
            throw new InvalidWorkingTimeException("Consultas apenas em horas cheias (ex: 14:00, 15:00).");
        }

        List<Consulta> agendamentosDoDia = getConsultasMap().computeIfAbsent(dia, k -> new ArrayList<>());

        boolean horarioOcupado = agendamentosDoDia.stream()
                .anyMatch(c -> c.getHorarioConsulta().equals(hora) && c.getEstadoConsulta() != EstadoConsulta.CANCELADA);

        if (horarioOcupado) {
            throw new IllegalArgumentException("Horário " + hora + " já está ocupado.");
        }

        agendamentosDoDia.add(consulta);
    }

    public void adicionarConsulta(Consulta consulta) {
        if (consulta == null) {
            return;
        }

        DayOfWeek dia = consulta.getDiaConsulta();
        List<Consulta> lista = getConsultasMap().computeIfAbsent(dia, k -> new ArrayList<>());
        lista.add(consulta);
    }

    public void removerConsulta(Consulta consulta) {
        if (consulta == null) {
            return;
        }

        DayOfWeek dia = consulta.getDiaConsulta();

        if (getConsultasMap().containsKey(dia)) {
            List<Consulta> listaDia = getConsultasMap().get(dia);

            listaDia.remove(consulta);
        }
    }

    private Map<DayOfWeek, List<Consulta>> getConsultasMap() {
        if (this.consultasSemana == null) {
            return new HashMap<>();
        }
        return this.consultasSemana;
    }

    // Buscas
    public boolean estaTrabalhando(DayOfWeek dia, LocalTime hora) {
        for (HorarioTrabalho horario : this.agenda) {
            if (horario.getDiaTrabalho() == dia) {
                return !hora.isBefore(horario.getHorarioComeco())
                        && hora.isBefore(horario.getHorarioFinal());
            }
        }
        return false;
    }

    public List<Consulta> getConsultasDoDia(DayOfWeek dia) {
        return new ArrayList<>(getConsultasMap().getOrDefault(dia, new ArrayList<>()));
    }

    public List<Consulta> getConsultas() {
        List<Consulta> todasAsConsultas = new ArrayList<>();

        for (List<Consulta> listaDia : getConsultasMap().values()) {
            todasAsConsultas.addAll(listaDia);
        }

        return todasAsConsultas;
    }

    public void atualizarConsulta(Consulta antiga, Consulta nova) {

        if (antiga == null || nova == null) {
            return;
        }

        DayOfWeek dia = antiga.getDiaConsulta();

        List<Consulta> listaDia = consultasSemana.get(dia);
        if (listaDia == null) {
            return;
        }

        for (int i = 0; i < listaDia.size(); i++) {
            Consulta c = listaDia.get(i);
            boolean mesmoHorario = c.getHorarioConsulta().equals(antiga.getHorarioConsulta());
            boolean mesmoPaciente = (c.getNomePacienteDisplay() != null && antiga.getNomePacienteDisplay() != null)
                    && c.getNomePacienteDisplay().equals(antiga.getNomePacienteDisplay());
            if (mesmoHorario && mesmoPaciente) {
                listaDia.set(i, nova);
                return;
            }
        }
    }

    public void finalizarConsulta(Consulta consulta, int avaliacao) {
        if (consulta == null) {
            return;
        }

        Consulta nova = consulta.finalizarConsulta(avaliacao);
        atualizarConsulta(consulta, nova);
    }

    // Getters
    public Especialidade getEspecialidade() {
        return this.especialidade;
    }

    public List<HorarioTrabalho> getAgenda() {
        return Collections.unmodifiableList(this.agenda);
    }
}
