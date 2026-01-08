package br.ufjf.dcc.dcc025.controller;

import java.util.ArrayList;
import java.util.List;

import br.ufjf.dcc.dcc025.model.Paciente;

public class RecepcionistaController {

    private List<Paciente> pacientes = new ArrayList<>();

    public void cadastrarPaciente(Paciente paciente) {
        pacientes.add(paciente);
        System.out.println("Paciente cadastrado: " + paciente);
    }

    public List<Paciente> getPacientes() {
        return pacientes;
    }
}
