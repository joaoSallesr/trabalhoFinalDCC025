package br.ufjf.dcc.dcc025.model.dto;

public class DadosMedico implements DadosUsuario {

    private final String nome;
    private final String sobrenome;
    private final String cpf;    
    private final String senha;
    private final String email;
    private final String especialidade;

    public DadosMedico(
            String nome, String sobrenome,
            String cpf,
            String senha,
            String email,
            String especialidade) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.cpf = cpf;
        this.senha = senha;
        this.email = email;
        this.especialidade = especialidade;
    }

    // Getters
    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public String getSobrenome() {
        return sobrenome;
    }

    @Override
    public String getCPF() {
        return cpf;
    }

    @Override
    public String getSenha() {
        return senha;
    }

    @Override
    public String getEmail() {
        return email;
    }

    public String getEspecialidade() {
        return especialidade;
    }
}
