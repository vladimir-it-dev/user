package org.example.app.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.app.dto.SubscriptionRequest;
import org.example.app.dto.SubscriptionResponse;
import org.example.app.dto.SubscriptionResponseTOP3;
import org.example.app.entity.Subscription;
import org.example.app.entity.User;
import org.example.app.exception.SubscriptionExistsByUserException;
import org.example.app.exception.SubscriptionNotFoundByIDException;
import org.example.app.exception.UserNotFoundByIDException;
import org.example.app.mapper.SubscriptionMapper;
import org.example.app.repository.SubscriptionRepository;
import org.example.app.repository.UserRepository;
import org.example.app.service.SubscriptionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.example.app.exception.SubscriptionExistsByUserException.SUBSCRIPTION_EXISTS_BY_USER_EXCEPTION;
import static org.example.app.exception.SubscriptionNotFoundByIDException.SUBSCRIPTION_NOT_FOUND_BY_ID_EXCEPTION;
import static org.example.app.exception.UserNotFoundByIDException.USER_NOT_FOUND_BY_ID_EXCEPTION;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final UserRepository userRepository;

    private final SubscriptionRepository subscriptionRepository;

    private final SubscriptionMapper subscriptionMapper;

    @Override
    @Transactional
    public String createSubscriptionUser(Long idUser, SubscriptionRequest subscriptionRequest) {
        // Проверяем, существует ли пользователь с данным id
        User user = userRepository.findById(idUser).orElseThrow(() -> new UserNotFoundByIDException(USER_NOT_FOUND_BY_ID_EXCEPTION));
        // Проверяем, существует ли подписка у данного пользователя
        if (subscriptionRepository.existsByUserIdAndSubscriptionName(idUser, subscriptionRequest.getSubscriptionName())) {
            throw new SubscriptionExistsByUserException(SUBSCRIPTION_EXISTS_BY_USER_EXCEPTION);
        }

        Subscription subscription = subscriptionMapper.mapperSubscriptionRequestToSubscription(subscriptionRequest);
        subscription.setUser(user);
        subscriptionRepository.save(subscription);
        log.info("Подписка {} успешно сохранена у пользователя с ID: {}, имя: {}, email: {}",
                subscription.getSubscriptionName(),
                user.getId(),
                user.getName(),
                user.getEmail());
        return String.format("Подписка %s успешно сохранена у пользователя с ID: %d, имя: %s, email: %s",
                subscription.getSubscriptionName(),
                user.getId(),
                user.getName(),
                user.getEmail());
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubscriptionResponse> getSubscriptionsUser(Long idUser) {
        // Проверяем, существует ли пользователь с данным id
        User user = userRepository.findWithSubscriptionsById(idUser).orElseThrow(() ->
                new UserNotFoundByIDException(USER_NOT_FOUND_BY_ID_EXCEPTION));
        log.info("Успешно получены подписки для пользователя с ID: {}, имя: {}, email: {}",
                user.getId(),
                user.getName(),
                user.getEmail());
        return subscriptionMapper.mapperListSubscriptionToListSubscriptionResponse(user.getSubscriptions());

    }

    @Override
    @Transactional
    public String deleteSubscriptionUser(Long idUser, Long idSubscription) {
        // Проверяем, существует ли пользователь с данным id
        User user = userRepository.findWithSubscriptionsById(idUser).orElseThrow(() ->
                new UserNotFoundByIDException(USER_NOT_FOUND_BY_ID_EXCEPTION));
        // Проверяем, существует ли подписка с данным id
        Subscription subscription = subscriptionRepository.findByIdAndUserId(idSubscription, idUser)
                .orElseThrow(() -> new SubscriptionNotFoundByIDException(SUBSCRIPTION_NOT_FOUND_BY_ID_EXCEPTION));

        user.getSubscriptions().remove(subscription);
        subscription.setUser(null);

        userRepository.save(user);
        subscriptionRepository.delete(subscription);
        log.info("Подписка {} успешно удалена у пользователя с ID: {}, имя: {}, email: {}",
                subscription.getSubscriptionName(),
                user.getId(),
                user.getName(),
                user.getEmail());
        return String.format("Подписка %s успешно удалена у пользователя с ID: %d, имя: %s, email: %s",
                subscription.getSubscriptionName(),
                user.getId(),
                user.getName(),
                user.getEmail());
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubscriptionResponseTOP3> getSubscriptionTOP3() {
        List<SubscriptionResponseTOP3> subscriptionResponseTOP3s = subscriptionRepository
                .findTop3PopularSubscriptions()
                .stream()
                .map(result -> SubscriptionResponseTOP3
                        .builder()
                        .subscriptionName((String) result[0])
                        .usersCount(((Number) result[1]).intValue())
                        .build())
                .toList();
        log.info("Успешно получены топ 3 подписок");
        return subscriptionResponseTOP3s;
    }
}
