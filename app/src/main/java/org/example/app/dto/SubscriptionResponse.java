package org.example.app.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class SubscriptionResponse {

    private Long id;

    private String subscriptionName;

    private LocalDateTime startDateTime;

}
