package br.ufjf.dcc.dcc025.model.exception;

public class InvalidEmailException extends RuntimeException{
    public InvalidEmailException (String message) {
        super(message);
    }
}
