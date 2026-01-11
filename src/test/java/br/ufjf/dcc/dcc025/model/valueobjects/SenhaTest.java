package br.ufjf.dcc.dcc025.model.valueobjects;

import org.junit.Test;
import static org.junit.Assert.*;

import br.ufjf.dcc.dcc025.model.exception.InvalidPasswordException;

public class SenhaTest {

    @Test
    public void deveCriarSenhaValida() {
        Senha senha = new Senha("senha123");
        assertNotNull(senha);
    }

    @Test(expected = InvalidPasswordException.class)
    public void deveLancarExcecaoParaSenhaInvalida() {
        new Senha("123");
    }
}
