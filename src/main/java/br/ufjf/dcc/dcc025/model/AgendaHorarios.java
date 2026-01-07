package br.ufjf.dcc.dcc025.model;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import br.ufjf.dcc.dcc025.model.exception.InvalidUserException;
import br.ufjf.dcc.dcc025.model.valueobjects.Especialidade;

public class AgendaHorarios {

    private final List<Medico> listaMedicos;

    public AgendaHorarios() {
        this.listaMedicos = new ArrayList<>();
    }
    
    // Alterações
    public void adicionarMedico(Medico medico) {
        validaMedico(medico);
        this.listaMedicos.add(medico);
    }

    public void removerMedico(Medico medico) {
        this.listaMedicos.remove(medico);
    }

    // Buscas
    public List<Medico> buscarHorario(Especialidade especialidade, DayOfWeek dia, LocalTime hora) {
        List<Medico> disponivel = new ArrayList<>();
        for (Medico medico : listaMedicos) {
            if (medico.estaTrabalhando(dia, hora) && medico.getEspecialidade() == especialidade) {
                disponivel.add(medico);
            }
        }
        return disponivel;
    }

    // Validações
    private static void validaMedico(Medico medico) {
        if (medico == null) {
            throw new InvalidUserException("Médico obrigatório.");
        }
    }

    // Getters
    public List<Medico> getListaMedicos() {
        return listaMedicos;
    }
}
