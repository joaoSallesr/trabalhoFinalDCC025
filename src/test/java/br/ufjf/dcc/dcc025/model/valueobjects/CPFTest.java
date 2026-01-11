package br.ufjf.dcc.dcc025.model.valueobjects;

import org.junit.Test;
import static org.junit.Assert.*;

public class CPFTest {
    @Test
    public void deveCriarCPFValido() {
        CPF cpf = new CPF("11144477735");
        assertNotNull(cpf);
    }
}
