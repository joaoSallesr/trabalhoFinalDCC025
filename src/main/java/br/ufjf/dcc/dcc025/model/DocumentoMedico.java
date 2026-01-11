package br.ufjf.dcc.dcc025.model;

import java.time.LocalDate;

public class DocumentoMedico {

    private final String nomeMedico;
    private final String nomePaciente;
    private final String texto;
    private final LocalDate data;

    public DocumentoMedico(String nomeMedico, String nomePaciente, String texto) {
        this.nomeMedico = nomeMedico;
        this.nomePaciente = nomePaciente;
        this.texto = texto;
        this.data = LocalDate.now();
    }

    public String getNomeMedico() {
        return nomeMedico;
    }

    public String getNomePaciente() {
        return nomePaciente;
    }

    public String getTexto() {
        return texto;
    }

    public LocalDate getData() {
        return data;
    }
}
