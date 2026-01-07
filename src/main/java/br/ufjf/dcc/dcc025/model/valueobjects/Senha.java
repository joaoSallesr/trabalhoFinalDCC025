package br.ufjf.dcc.dcc025.model.valueobjects;

import java.util.regex.Pattern;

import br.ufjf.dcc.dcc025.model.exception.InvalidPasswordException;

public class Senha {

    private final String senha;

    private static final Pattern PADRAO_SENHA
            = Pattern.compile("^(?=.*[0-9])(?=\\S+$).{8,20}$");
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
        if (senha != null && PADRAO_SENHA.matcher(senha).matches()) {
            throw new InvalidPasswordException("Senha inválida.");
        }

    }
}
