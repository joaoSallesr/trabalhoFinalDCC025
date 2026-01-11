package br.ufjf.dcc.dcc025.model.valueobjects;

import org.junit.Test;
import static org.junit.Assert.*;

import br.ufjf.dcc.dcc025.model.exception.InvalidEmailException;

public class EmailTest {

    @Test
    public void deveCriarEmailValido() {
        Email email = new Email("teste@email.com");
        assertNotNull(email);
    }

    @Test(expected = InvalidEmailException.class)
    public void deveLancarExcecaoParaEmailInvalido() {
        new Email("email-invalido");
    }
}
