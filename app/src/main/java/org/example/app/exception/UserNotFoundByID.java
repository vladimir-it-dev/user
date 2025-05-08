package org.example.app.exception;

import lombok.Getter;

@Getter
public class UserNotFoundByID extends RuntimeException {

    public static final String USER_NOT_FOUND_BY_ID = "Пользователь с таким ID не найден";

    public UserNotFoundByID(String message) {
        super(message);
    }
}
