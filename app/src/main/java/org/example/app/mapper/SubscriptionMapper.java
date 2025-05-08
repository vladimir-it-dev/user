package org.example.app.mapper;

import org.example.app.dto.SubscriptionRequest;
import org.example.app.dto.SubscriptionResponse;
import org.example.app.entity.Subscription;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface SubscriptionMapper {

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "id", ignore = true)
    Subscription mapperSubscriptionRequestToSubscription(SubscriptionRequest subscriptionRequest);

    SubscriptionResponse mapperSubscriptionResponseToSubscription(Subscription subscription);

    default List<SubscriptionResponse> mapperListSubscriptionToListSubscriptionResponse(List<Subscription> subscriptionList) {
        if (subscriptionList.isEmpty()) {
            return Collections.emptyList();
        }
        ArrayList<SubscriptionResponse> responseArrayList = new ArrayList<>(subscriptionList.size());
        for (Subscription subscription : subscriptionList) {
            responseArrayList.add(this.mapperSubscriptionResponseToSubscription(subscription));
        }
        return responseArrayList;
    }
}
