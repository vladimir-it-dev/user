package org.example.app.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserResponse {

    private Long id;

    private String name;

    private String email;

    private List<SubscriptionResponse> subscriptions;
}
