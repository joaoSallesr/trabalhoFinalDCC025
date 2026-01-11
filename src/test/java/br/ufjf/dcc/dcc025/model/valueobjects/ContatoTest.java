package br.ufjf.dcc.dcc025.model.valueobjects;

import org.junit.Test;
import static org.junit.Assert.*;

import br.ufjf.dcc.dcc025.model.exception.InvalidPhoneNumberException;

public class ContatoTest {

    @Test
    public void deveCriarContatoValido() {
        Contato contato = new Contato("32999998888");
        assertNotNull(contato);
    }

    @Test(expected = InvalidPhoneNumberException.class)
    public void deveLancarExcecaoParaContatoInvalido() {
        new Contato("abc123");
    }
}
