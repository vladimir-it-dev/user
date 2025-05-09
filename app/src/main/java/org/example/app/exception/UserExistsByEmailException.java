package org.example.app.exception;

import lombok.Getter;


@Getter
public class UserExistsByEmailException extends RuntimeException {

    public static final String USER_EXISTS_BY_EMAIL_EXCEPTION = "Пользователь с таким Email уже есть в базе данных." +
            " Пожалуйста, используйте другой Email.";

    public UserExistsByEmailException(String message) {
        super(message);
    }
}
