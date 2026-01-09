package br.ufjf.dcc.dcc025.model.dto;

public class DadosPaciente implements DadosUsuario {

    private final String nome;
    private final String sobrenome;
    private final String cpf;
    private final String senha;
    private final String email;
    private final String numeroContato;
    private final String cep;
    private final String rua;
    private final String bairro;
    private final String cidade;
    private final int numeroCasa;

    public DadosPaciente(
            String nome, String sobrenome,
            String cpf,
            String senha,
            String email,
            String numeroContato,
            String cep, String rua, String bairro, String cidade, int numeroCasa) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.cpf = cpf;
        this.senha = senha;
        this.email = email;
        this.numeroContato = numeroContato;
        this.cep = cep;
        this.rua = rua;
        this.bairro = bairro;
        this.cidade = cidade;
        this.numeroCasa = numeroCasa;
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

    public String getNumeroContato() {
        return numeroContato;
    }

    public String getCEP() {
        return cep;
    }

    public String getRua() {
        return rua;
    }

    public String getBairro() {
        return bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public int getNumeroCasa() {
        return numeroCasa;
    }
}
