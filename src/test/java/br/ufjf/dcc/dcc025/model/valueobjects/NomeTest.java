package br.ufjf.dcc.dcc025.model.valueobjects;

import org.junit.Test;
import static org.junit.Assert.*;

public class NomeTest {

    @Test
    public void deveCriarNomeValido() {
        Nome nome = new Nome("João", "Silva");

        assertEquals("João", nome.getNome());
        assertEquals("Silva", nome.getSobrenome());
    }

    @Test
    public void devePermitirNomeVazio() {
        Nome nome = new Nome("", "Silva");
        assertEquals("", nome.getNome());
    }

    @Test
    public void devePermitirSobrenomeVazio() {
        Nome nome = new Nome("João", "");
        assertEquals("", nome.getSobrenome());
    }
}
