package org.example.app.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.app.dto.SubscriptionRequest;
import org.example.app.dto.SubscriptionResponse;
import org.example.app.dto.SubscriptionResponseTOP3;
import org.example.app.entity.Subscription;
import org.example.app.entity.User;
import org.example.app.exception.SubscriptionNotFoundException;
import org.example.app.exception.UserNotFoundByID;
import org.example.app.mapper.SubscriptionMapper;
import org.example.app.repository.SubscriptionRepository;
import org.example.app.repository.UserRepository;
import org.example.app.service.SubscriptionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.example.app.exception.SubscriptionNotFoundException.SUBSCRIPTION_NOT_FOUND_EXCEPTION;
import static org.example.app.exception.UserNotFoundByID.USER_NOT_FOUND_BY_ID;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final UserRepository userRepository;

    private final SubscriptionRepository subscriptionRepository;

    private final SubscriptionMapper subscriptionMapper;

    @Override
    @Transactional
    public String createSubscriptionUser(Long idUser, SubscriptionRequest subscriptionRequest) {
        User user = userRepository.findById(idUser).orElseThrow(() -> new UserNotFoundByID(USER_NOT_FOUND_BY_ID));
        Subscription subscription = subscriptionMapper.mapperSubscriptionRequestToSubscription(subscriptionRequest);
        subscription.setUser(user);
        subscriptionRepository.save(subscription);
        return String.format("Подписка %s успешно сохранена у пользователя с ID: %d, имя: %s, email: %s",
                subscription.getSubscriptionName(),
                user.getId(),
                user.getName(),
                user.getEmail());
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubscriptionResponse> getSubscriptionsUser(Long idUser) {
        User user = userRepository.findWithSubscriptionsById(idUser).orElseThrow(() ->
                new UserNotFoundByID(USER_NOT_FOUND_BY_ID));
        return subscriptionMapper.mapperListSubscriptionToListSubscriptionResponse(user.getSubscriptions());

    }

    @Override
    @Transactional
    public String deleteSubscriptionUser(Long idUser, Long idSubscription) {
        User user = userRepository.findWithSubscriptionsById(idUser).orElseThrow(() -> new UserNotFoundByID(USER_NOT_FOUND_BY_ID));
        Subscription subscription = subscriptionRepository.findByIdAndUserId(idSubscription, idUser)
                .orElseThrow(() -> new SubscriptionNotFoundException(SUBSCRIPTION_NOT_FOUND_EXCEPTION));

        user.getSubscriptions().remove(subscription);
        subscription.setUser(null);

        userRepository.save(user);
        subscriptionRepository.delete(subscription);
        return String.format("Подписка %s успешно удалена у пользователя с ID: %d, имя: %s, email: %s",
                subscription.getSubscriptionName(),
                user.getId(),
                user.getName(),
                user.getEmail());
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubscriptionResponseTOP3> getSubscriptionTOP3() {
        return subscriptionRepository.findTop3PopularSubscriptions().stream()
                .map(result -> SubscriptionResponseTOP3
                        .builder()
                        .subscriptionName((String) result[0])
                        .usersCount(((Number) result[1]).intValue())
                        .build())
                .toList();
    }
}
