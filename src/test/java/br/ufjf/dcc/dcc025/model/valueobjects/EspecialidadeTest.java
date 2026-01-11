package br.ufjf.dcc.dcc025.model.valueobjects;

import org.junit.Test;
import static org.junit.Assert.*;

import br.ufjf.dcc.dcc025.model.exception.InvalidSpecialtyException;

public class EspecialidadeTest {
    @Test
    public void deveConverterDescricaoCorreta() {
        Especialidade esp = Especialidade.fromString("Cardiologista");
        assertEquals(Especialidade.CARDIOLOGISTA, esp);
    }

    @Test
    public void deveConverterNomeDoEnum() {
        Especialidade esp = Especialidade.fromString("CARDIOLOGISTA");
        assertEquals(Especialidade.CARDIOLOGISTA, esp);
    }

    @Test
    public void deveIgnorarMaiusculasMinusculasEAcentos() {
        Especialidade esp = Especialidade.fromString("clinico");
        assertEquals(Especialidade.CLINICO, esp);
    }

    @Test
    public void deveRetornarDescricaoCorreta() {
        Especialidade esp = Especialidade.PEDIATRA;
        assertEquals("Pediatra", esp.getDescricao());
    }

    @Test(expected = InvalidSpecialtyException.class)
    public void naoDeveAceitarEntradaNula() {
        Especialidade.fromString(null);
    }

    @Test(expected = InvalidSpecialtyException.class)
    public void naoDeveAceitarEntradaVazia() {
        Especialidade.fromString("");
    }

    @Test(expected = InvalidSpecialtyException.class)
    public void naoDeveAceitarEspecialidadeInexistente() {
        Especialidade.fromString("Neurologista");
    }
}
