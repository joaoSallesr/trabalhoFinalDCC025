package br.ufjf.dcc.dcc025.model.repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Repository<T> {

    private final String caminhoRepositorio;
    private final Gson gson;

    public Repository(String nomeArquivo) {
        this.caminhoRepositorio = "data" + File.separator + nomeArquivo;
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    public void salvar(List<T> usuarios) {
        try (Writer writer = new FileWriter(caminhoRepositorio)) {
            gson.toJson(usuarios, writer);
        } catch (IOException e) {
            System.out.println("Erro ao salvar usuário.");
        }
    }

    public List<T> carregarUsuarios(Type tipoUsuario) {
        File arquivo = new File(caminhoRepositorio);
        if (!arquivo.exists()) {
            return new ArrayList<>();
        }

        try (Reader reader = new FileReader(caminhoRepositorio)) {
            // Converte o JSON de volta para uma Lista de Objetos
            return gson.fromJson(reader, tipoUsuario);
        } catch (IOException e) {
            System.out.println("Erro ao carregar usuários.");
            return new ArrayList<>();
        }
    }
}
