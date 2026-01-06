package br.ufjf.dcc.dcc025.model.valueobjects;

import java.util.regex.*;

import br.ufjf.dcc.dcc025.model.exception.InvalidAddressException;

public class Endereco {
    private String cep;
    private String rua;
    private String bairro;
    private String cidade;
    private int numeroCasa;

    private static final Pattern PADRAO_CEP = 
        Pattern.compile("^(\\d{5}-\\d{3}|\\d{8})$");
    
    public static Endereco getInstance(String cep, String rua, String bairro, String cidade, int numeroCasa) {
        return new Endereco(cep, rua, bairro, cidade, numeroCasa);
    }

    private Endereco(String cep, String rua, String bairro, String cidade, int numeroCasa) {
        validaCep(cep);
        validaNumeroCasa(numeroCasa);

        this.cep = cep;
        this.rua = validaLocal(rua, "Rua obrigatória.");
        this.bairro = validaLocal(bairro, "Bairro obrigatório.");
        this.cidade = validaLocal(cidade, "Cidade obrigatória.");
        this.numeroCasa = numeroCasa;
    }

    // Alteração de endereço
    public void alterarCep(String cep) {
        validaCep(cep);
        this.cep = cep;
    }

    public void alterarRua(String rua) {
        this.rua = validaLocal(rua, "Rua obrigatória.");
    }

    public void alterarBairro(String bairro) {
        this.bairro = validaLocal(bairro, "Bairro obrigatório.");
    }

    public void alterarCidade(String cidade) {
        this.cidade = validaLocal(cidade, "Cidade obrigatória.");
    }

    public void alterarNumeroCasa(int numeroCasa) {
        validaNumeroCasa(numeroCasa);
        this.numeroCasa = numeroCasa;
    }

    // Validações
    private static void validaCep(String cep) {
        if(cep != null && PADRAO_CEP.matcher(cep).matches())
            throw new InvalidAddressException("Cep inválido: " + cep + " - XXXXX-XXX / XXXXXXXX");  
    }

    private static String validaLocal(String local, String message) {
        if (local == null || local.isBlank())
           throw new InvalidAddressException(message);
        return local;
    }

    private static void validaNumeroCasa(int numeroCasa) {
        if (numeroCasa <= 0) 
            throw new InvalidAddressException("Número de casa inválido:");
    }

    // Getters
    public String getCep() {
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
