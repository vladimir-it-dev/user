package org.example.app.controller;

import lombok.RequiredArgsConstructor;
import org.example.app.dto.SubscriptionRequest;
import org.example.app.dto.SubscriptionResponse;
import org.example.app.dto.SubscriptionResponseTOP3;
import org.example.app.service.SubscriptionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @PostMapping("/{id}/subscriptions")
    public String createSubscriptionUser(@PathVariable("id") Long idUser, @RequestBody SubscriptionRequest subscriptionRequest) {
        return subscriptionService.createSubscriptionUser(idUser, subscriptionRequest);
    }

    @GetMapping("/{id}/subscriptions")
    public List<SubscriptionResponse> getSubscriptionUser(@PathVariable("id") Long idUser) {
        return subscriptionService.getSubscriptionsUser(idUser);
    }

    @DeleteMapping("/{id}/subscriptions/{sub_id}")
    public String deleteSubscriptionUser(@PathVariable("id") Long idUser, @PathVariable("sub_id") Long idSubscription) {

        return subscriptionService.deleteSubscriptionUser(idUser, idSubscription);
    }

    @GetMapping("/subscriptions/top")
    public List<SubscriptionResponseTOP3> getSubscriptionTOP3() {
        return subscriptionService.getSubscriptionTOP3();
    }
}
