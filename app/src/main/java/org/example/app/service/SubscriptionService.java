package org.example.app.service;

import org.example.app.dto.SubscriptionRequest;
import org.example.app.dto.SubscriptionResponse;
import org.example.app.dto.SubscriptionResponseTOP3;

import java.util.List;

public interface SubscriptionService {

    String createSubscriptionUser(Long idUser, SubscriptionRequest subscriptionRequest);

    List<SubscriptionResponse> getSubscriptionsUser(Long idUser);

    String deleteSubscriptionUser(Long idUser, Long idSubscription);

    List<SubscriptionResponseTOP3> getSubscriptionTOP3();
}
