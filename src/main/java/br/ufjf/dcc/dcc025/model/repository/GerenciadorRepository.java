package br.ufjf.dcc.dcc025.model.repository;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.reflect.TypeToken;

import br.ufjf.dcc.dcc025.model.Medico;
import br.ufjf.dcc.dcc025.model.Paciente;
import br.ufjf.dcc.dcc025.model.Recepcionista;

public class GerenciadorRepository {

    private static GerenciadorRepository instance;

    private List<Paciente> listaPacientes;
    private List<Medico> listaMedicos;
    private List<Recepcionista> listaRecepcionistas;

    private final Repository<Paciente> repositorioPacientes;
    private final Repository<Medico> repositorioMedicos;
    private final Repository<Recepcionista> repositorioRecepcionista;

    public static GerenciadorRepository getInstance() {
        if (instance == null) {
            instance = new GerenciadorRepository();
        }
        return instance;
    }

    private GerenciadorRepository() {
        repositorioPacientes = new Repository<>("pacientes.json");
        repositorioMedicos = new Repository<>("medicos.json");
        repositorioRecepcionista = new Repository<>("recepcionistas.json");

        listaPacientes = new ArrayList<>();
        listaMedicos = new ArrayList<>();
        listaRecepcionistas = new ArrayList<>();

        carregarRepositorio();
    }

    // Sempre que o sistema for iniciado, carrega os dados atuais do repositório
    private void carregarRepositorio() {
        Type tipoListaPaciente = new TypeToken<List<Paciente>>() {
        }.getType();
        Type tipoListaMedico = new TypeToken<List<Medico>>() {
        }.getType();
        Type tipoListaRecepcionista = new TypeToken<List<Recepcionista>>() {
        }.getType();

        List<Paciente> pacientesCarregados = repositorioPacientes.carregarUsuarios(tipoListaPaciente);
        if (pacientesCarregados != null) {
            this.listaPacientes = pacientesCarregados;
        }
        List<Medico> medicosCarregados = repositorioMedicos.carregarUsuarios(tipoListaMedico);
        if (medicosCarregados != null) {
            this.listaMedicos = medicosCarregados;
        }
        List<Recepcionista> recepcionistasCarregados = repositorioRecepcionista.carregarUsuarios(tipoListaRecepcionista);
        if (recepcionistasCarregados != null) {
            this.listaRecepcionistas = recepcionistasCarregados;
        }
    }

    // Adicionar usuário no repositório
    public void adicionarPaciente(Paciente paciente) {
        this.listaPacientes.add(paciente);
        repositorioPacientes.salvar(listaPacientes);
    }

    public void adicionarMedico(Medico medico) {
        this.listaMedicos.add(medico);
        repositorioMedicos.salvar(listaMedicos);
    }

    public void adicionarRecepcionista(Recepcionista recepcionista) {
        this.listaRecepcionistas.add(recepcionista);
        repositorioRecepcionista.salvar(listaRecepcionistas);
    }

    // Atualizar usuário no repositório
    public void salvarPacientes() {
        this.repositorioPacientes.salvar(listaPacientes);
    }

    public void salvarMedicos() {
        this.repositorioMedicos.salvar(this.listaMedicos);
    }

    public void salvarRecepcionistas() {
        this.repositorioRecepcionista.salvar(this.listaRecepcionistas);
    }

    // Buscas
    public List<Paciente> buscarHospitalizados() {
        List<Paciente> hospitalizados  = new ArrayList<>();
        for (Paciente p : this.listaPacientes) {
            if (p.isHospitalizado()) {
                hospitalizados .add(p);
            }
        }
        return hospitalizados;
    }

    public List<Paciente> buscarRecebemVisitas() {
        List<Paciente> recebemVisitas = new ArrayList<>();
        for (Paciente p : this.listaPacientes) {
            if (p.isRecebeVisita()) {
                recebemVisitas.add(p);
            }
        }
        return recebemVisitas;
    }

    public boolean existePacienteVisitavel(String nome, String sobrenome) {
        for (Paciente p : this.listaPacientes) {
            boolean nomeIgual = p.getNome().getNome().equalsIgnoreCase(nome);
            boolean sobreNomeIgual = p.getNome().getSobrenome().equalsIgnoreCase(sobrenome);
            
            if (nomeIgual && sobreNomeIgual && p.isRecebeVisita()) {
                return true;
            }
        }
        return false;
    }

    // Getters
    public List<Paciente> getPacientes() {
        return listaPacientes;
    }

    public List<Medico> getMedicos() {
        return listaMedicos;
    }

    public List<Recepcionista> getRecepcionistas() {
        return listaRecepcionistas;
    }
}
