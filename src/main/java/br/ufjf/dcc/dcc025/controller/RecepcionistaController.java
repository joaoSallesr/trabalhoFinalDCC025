package br.ufjf.dcc.dcc025.controller;

import br.ufjf.dcc.dcc025.model.Recepcionista;
import br.ufjf.dcc.dcc025.model.dto.DadosRecepcionista;
import br.ufjf.dcc.dcc025.model.repository.GerenciadorRepository;
import br.ufjf.dcc.dcc025.view.RecepcionistaView;

public class RecepcionistaController {

    private Recepcionista recepcionista;
    private RecepcionistaView view;

    public RecepcionistaController(Recepcionista recepcionista, RecepcionistaView view) {
        this.recepcionista = recepcionista;
        this.view = view;
    }

    public void cadastrarRecepcionista(DadosRecepcionista dados) {
        Recepcionista novoRecepcionista = new Recepcionista(dados);
        GerenciadorRepository.getInstance().adicionarRecepcionista(novoRecepcionista);
    }
}
