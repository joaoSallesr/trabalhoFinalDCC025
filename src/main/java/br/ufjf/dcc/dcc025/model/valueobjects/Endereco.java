package br.ufjf.dcc.dcc025.model.valueobjects;

import java.util.regex.*;
import java.util.Objects;

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
        if (!isCepValid(cep)) 
            throw new InvalidAddressException("Cep inválido: " + cep + " - XXXXX-XXX / XXXXXXXX");
        if (numeroCasa <= 0) 
            throw new InvalidAddressException("Número de casa inválido:");

        this.cep = Objects.requireNonNull(cep, "CEP obrigatório.");
        this.rua = requireNotBlank(rua, "Rua obrigatória.");
        this.bairro = requireNotBlank(bairro, "Bairro obrigatório.");
        this.cidade = requireNotBlank(cidade, "Cidade obrigatória.");
        this.numeroCasa = numeroCasa;
    }

    public void alterarCep(String cep) {
        if (!isCepValid(cep)) 
            throw new InvalidAddressException("Cep inválido: " + cep + " - XXXXX-XXX / XXXXXXXX");
        this.cep = Objects.requireNonNull(cep, "CEP obrigatório.");
    }

    public void alterarRua(String rua) {
        this.rua = requireNotBlank(rua, "Rua obrigatória.");
    }

    public void alterarBairro(String bairro) {
        this.bairro = requireNotBlank(bairro, "Bairro obrigatório.");
    }

    public void alterarCidade(String cidade) {
        this.cidade = requireNotBlank(cidade, "Cidade obrigatória.");
    }

    public void alterarNumeroCasa(int numeroCasa) {
        if (numeroCasa <= 0) 
            throw new InvalidAddressException("Número de casa inválido:");
        this.numeroCasa = numeroCasa;
    }

    private static boolean isCepValid(String cep) {
        return cep != null && PADRAO_CEP.matcher(cep).matches();
    }

    private static String requireNotBlank(String dado, String message) {
        if (dado == null || dado.isBlank())
           throw new InvalidAddressException(message);
        return dado;
    }

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
