package org.example.app.exception;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class UserNotFoundByIDException extends RuntimeException {

    public static final String USER_NOT_FOUND_BY_ID_EXCEPTION = "Пользователь с таким ID не найден";

    public UserNotFoundByIDException(String message) {
        super(message);
        log.error(USER_NOT_FOUND_BY_ID_EXCEPTION);
    }
}
