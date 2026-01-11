package br.ufjf.dcc.dcc025.model.valueobjects;

import java.util.Objects;
import java.util.regex.Pattern;

import br.ufjf.dcc.dcc025.model.exception.InvalidPasswordException;

public class Senha {

    private String senha;

    public Senha() {
        // construtor vazio para o Jackson
    }

    private static final Pattern PADRAO_SENHA = Pattern.compile("^(?=.*[0-9])(?=\\S+$).{8,20}$");
    // Padrão de senha:
    // Números de 0 a 9
    // Sem espaços
    // 8 a 20 dígitos

    public Senha(String senha) {
        validaSenha(senha);
        this.senha = senha;
    }

    // Validação
    private static void validaSenha(String senha) {
        if (senha != null && !PADRAO_SENHA.matcher(senha).matches()) {
            throw new InvalidPasswordException("Senha inválida.");
        }
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        validaSenha(senha);
        this.senha = senha;
    }

    // Overrrides
    @Override
    public String toString() {
        return "Senha{********}"; 
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Senha senhaObj = (Senha) o;
        return Objects.equals(senha, senhaObj.senha);
    }

    @Override
    public int hashCode() {
        return Objects.hash(senha);
    }
}
