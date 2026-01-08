package br.ufjf.dcc.dcc025.model.repository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Arquivo {

    public static String leitura(String caminho) {

        StringBuilder dado = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(caminho))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                dado.append(linha).append("\n");
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler: " + e.getMessage());
            e.printStackTrace();
        }

        return dado.toString();
    }

    public static void salvamento(String caminho, String dado) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminho, false))) {
            writer.write(dado);
        } catch (IOException e) {
            System.err.println("Erro ao salvar: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
