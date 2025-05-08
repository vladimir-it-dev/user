package org.example.app.exception;

import lombok.Getter;

@Getter
public class SubscriptionNotFoundException extends RuntimeException {

    public static final String SUBSCRIPTION_NOT_FOUND_EXCEPTION = "Подписка не найдена у пользователя";

    public SubscriptionNotFoundException(String message) {
        super(message);
    }
}
