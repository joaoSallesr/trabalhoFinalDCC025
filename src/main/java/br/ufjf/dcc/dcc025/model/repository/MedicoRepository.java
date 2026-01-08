package br.ufjf.dcc.dcc025.model.repository;

import java.io.File;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import br.ufjf.dcc.dcc025.model.Medico;

public class MedicoRepository implements Repository<Medico> {

    private static final String PATH = DIRECTORY + File.separator + "medicos.json";
    private final Gson gson;

    public MedicoRepository() {
        File dir = new File(DIRECTORY);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        this.gson = new GsonBuilder()
                .setPrettyPrinting() // Deixa o JSON formatado bonitinho (com enter e tab)
                .registerTypeAdapter(LocalTime.class,
                        (JsonSerializer<LocalTime>) (src, typeOfSrc,
                                context) -> new JsonPrimitive(src.format(DateTimeFormatter.ISO_LOCAL_TIME)))
                .registerTypeAdapter(LocalTime.class,
                        (JsonDeserializer<LocalTime>) (json, typeOfT, context) -> LocalTime.parse(json.getAsString(),
                                DateTimeFormatter.ISO_LOCAL_TIME))
                .create();
    }

    @Override
    public void salvar(List<Medico> medicos) {
        String json = gson.toJson(medicos);
        Arquivo.salvamento(PATH, json);
    }

    @Override
    public List<Medico> carregar() {
        String json = Arquivo.leitura(PATH);

        if (json.isEmpty()) {
            return new ArrayList<>();
        }

        Type tipo = new TypeToken<ArrayList<Medico>>() {
        }.getType();
        return gson.fromJson(json, tipo);
    }
}
