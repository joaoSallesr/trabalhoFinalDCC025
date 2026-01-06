package br.ufjf.dcc.dcc025.model.valueobjects;

import br.ufjf.dcc.dcc025.model.exception.InvalidPasswordException;
import java.util.regex.*;

public class Password {
    private final String password;

    private static final String PASSWORD_PATTERN = 
        "^(?=.*[0-9])(?=\\S+$).{8,20}$";
        // Password pattern:
        // Numbers from 0 to 9
        // No spaces
        // 8 to 20 digits

    private static final Pattern pattern = 
        Pattern.compile(PASSWORD_PATTERN);

    public Password getInstance(String password) {
        return new Password(password);
    }

    private Password(String password) {
        if (!isValid(password))
            throw new InvalidPasswordException("Invalid password.");
        this.password = password;
    }

    private boolean isValid(String password) {
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
}
