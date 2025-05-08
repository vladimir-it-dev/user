package org.example.app.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SubscriptionResponseTOP3 {

    private String subscriptionName;

    private Integer usersCount;
}
