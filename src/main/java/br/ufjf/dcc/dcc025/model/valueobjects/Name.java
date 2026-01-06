package br.ufjf.dcc.dcc025.model.valueobjects;

import br.ufjf.dcc.dcc025.model.exception.InvalidNameException;

public class Name {
    private final String name;
    private final String surname;

    public static Name getInstance(String name, String surname) {
        return new Name(name, surname);
    }

    private Name (String name, String surname) {
        if (!isValid(name, surname))
            throw new InvalidNameException("Invalid name / surname: " + name + " " + surname);
        this.name = name;
        this.surname = surname;
    }

    private boolean isValid (String name, String surname) { 
        return name.length() > 1 && surname.length() > 1;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }
}
