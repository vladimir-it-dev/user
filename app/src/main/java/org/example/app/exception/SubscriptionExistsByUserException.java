package org.example.app.exception;


import lombok.Getter;

@Getter
public class SubscriptionExistsByUserException extends RuntimeException{

    public static final String SUBSCRIPTION_EXISTS_BY_USER_EXCEPTION = "Подписка уже оформлена у пользователя";

    public SubscriptionExistsByUserException(String message) {
        super(message);
    }
}
