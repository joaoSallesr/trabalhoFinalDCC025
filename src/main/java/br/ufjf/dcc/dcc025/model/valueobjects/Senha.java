package br.ufjf.dcc.dcc025.model.valueobjects;

import java.util.regex.*;

import br.ufjf.dcc.dcc025.model.exception.InvalidPasswordException;

public class Senha {
    private final String senha;

    private static final Pattern PADRAO_SENHA = 
        Pattern.compile("^(?=.*[0-9])(?=\\S+$).{8,20}$");
        // Padrão de senha:
        // Números de 0 a 9
        // Sem espaços
        // 8 a 20 dígitos

    public static Senha getInstance(String senha) {
        return new Senha(senha);
    }

    private Senha(String senha) {
        if (!isValid(senha))
            throw new InvalidPasswordException("Senha inválida.");
        this.senha = senha;
    }

    private boolean isValid(String senha) {
        return senha != null && PADRAO_SENHA.matcher(senha).matches();
    }
}
