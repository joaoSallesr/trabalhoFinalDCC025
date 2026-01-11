package br.ufjf.dcc.dcc025.model.valueobjects;

import org.junit.Test;
import static org.junit.Assert.*;

import br.ufjf.dcc.dcc025.model.exception.InvalidAddressException;

public class EnderecoTest {

    @Test
    public void deveCriarEnderecoValido() {
        Endereco endereco = new Endereco(
                "36000000",
                "Rua Principal",
                "Centro",
                "Juiz de Fora",
                100);

        assertEquals("Juiz de Fora", endereco.getCidade());
        assertEquals("Centro", endereco.getBairro());
        assertEquals("Rua Principal", endereco.getRua());
        assertEquals(100, endereco.getNumeroCasa());
    }

    @Test(expected = InvalidAddressException.class)
    public void naoDeveAceitarCepInvalido() {
        new Endereco(
                "abc",
                "Rua",
                "Bairro",
                "Cidade",
                10);
    }
}
