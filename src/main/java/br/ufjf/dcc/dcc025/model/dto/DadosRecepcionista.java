package br.ufjf.dcc.dcc025.model.dto;

public class DadosRecepcionista implements DadosUsuario {

    private final String nome;
    private final String sobrenome;
    private final String cpf;
    private final String senha;
    private final String email;

    public DadosRecepcionista(
            String nome, String sobrenome,
            String cpf,
            String senha,
            String email) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.cpf = cpf;
        this.senha = senha;
        this.email = email;
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
}
