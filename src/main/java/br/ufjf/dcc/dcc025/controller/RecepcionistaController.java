package br.ufjf.dcc.dcc025.controller;

import br.ufjf.dcc.dcc025.model.Recepcionista;
import br.ufjf.dcc.dcc025.model.dto.DadosRecepcionista;
import br.ufjf.dcc.dcc025.model.repository.GerenciadorRepository;

public class RecepcionistaController {

    public void cadastrarRecepcionista(DadosRecepcionista dados) {
        Recepcionista novoRecepcionista = new Recepcionista(dados);
        GerenciadorRepository.getInstance().adicionarRecepcionista(novoRecepcionista);
    }
}
