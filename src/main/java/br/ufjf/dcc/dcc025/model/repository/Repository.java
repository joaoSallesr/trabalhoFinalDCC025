package br.ufjf.dcc.dcc025.model.repository;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.ufjf.dcc.dcc025.model.utils.LocalDateAdapter;
import br.ufjf.dcc.dcc025.model.utils.LocalTimeAdapter;

public class Repository<T> {

    private final String caminhoRepositorio;
    private final Gson gson;

    public Repository(String nomeArquivo) {
        this.caminhoRepositorio = "data" + File.separator + nomeArquivo;
        this.gson = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(LocalTime.class, new LocalTimeAdapter()).registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();
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
            return gson.fromJson(reader, tipoUsuario);
        } catch (IOException e) {
            System.out.println("Erro ao carregar usuários.");
            return new ArrayList<>();
        }
    }
}
