package br.ufjf.dcc.dcc025.model.repository;

import java.util.List;

public interface Repository<T> {

    String DIRECTORY = "data";
    
    public void salvar(List<T> usuarios);
    public List<T> carregar();
}
