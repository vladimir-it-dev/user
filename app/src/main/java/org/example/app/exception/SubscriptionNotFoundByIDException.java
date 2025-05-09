package org.example.app.exception;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class SubscriptionNotFoundByIDException extends RuntimeException {

    public static final String SUBSCRIPTION_NOT_FOUND_BY_ID_EXCEPTION = "Подписка не найдена у пользователя";

    public SubscriptionNotFoundByIDException(String message) {
        super(message);
        log.error(SUBSCRIPTION_NOT_FOUND_BY_ID_EXCEPTION);
    }
}
