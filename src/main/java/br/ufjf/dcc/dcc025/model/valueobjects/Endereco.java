package br.ufjf.dcc.dcc025.model.valueobjects;

import java.util.regex.*;
import java.util.Objects;

import br.ufjf.dcc.dcc025.model.exception.InvalidAddressException;

public class Endereco {
    private String cep;
    private String rua;
    private int numeroCasa;
    private String bairro;
    private String cidade;

    private static final Pattern PADRAO_CEP = 
        Pattern.compile("^(\\d{5}-\\d{3}|\\d{8})$");
    
    public static Endereco getInstance(String cep, String rua, int numeroCasa, String bairro, String cidade) {
        return new Endereco(cep, rua, numeroCasa, bairro, cidade);
    }

    private Endereco(String cep, String rua, int numeroCasa, String bairro, String cidade) {
        if (!isCepValid(cep)) 
            throw new InvalidAddressException("Cep inválido: " + cep + " - XXXXX-XXX / XXXXXXXX");
        if (numeroCasa <= 0) 
            throw new InvalidAddressException("Número de casa inválido.");

        this.cep = Objects.requireNonNull(cep, "CEP obrigatório.");
        this.rua = Objects.requireNonNull(rua, "Rua obrigatória.");
        this.bairro = Objects.requireNonNull(bairro, "Bairro obrigatório.");
        this.cidade = Objects.requireNonNull(cidade, "Cidade obrigatória.");
        this.numeroCasa = numeroCasa;
    }

    private static boolean isCepValid(String cep) {
        return cep != null && PADRAO_CEP.matcher(cep).matches();
    }

    public String getCep() {
        return cep;
    }

    public String getRua() {
        return rua;
    }

    public int getNumeroCasa() {
        return numeroCasa;
    }

    public String getBairro() {
        return bairro;
    }

    public String getCidade() {
        return cidade;
    }

    /*private boolean isValid(String validador, String dado) {
        Pattern padrao = Pattern.compile(validador);
        Matcher matcher = padrao.matcher(dado);
        return matcher.matches();
    }
    */
}
