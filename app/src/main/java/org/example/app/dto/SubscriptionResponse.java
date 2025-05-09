package org.example.app.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@Schema(description = "Информация о подписке пользователя")
public class SubscriptionResponse {

    @Schema(description = "Идентификатор подписки", example = "1")
    private Long id;

    @Schema(description = "Название подписки", example = "Netflix Premium")
    private String subscriptionName;

    @Schema(description = "Дата и время начала подписки",
            example = "2023-12-31T23:59:59",
            format = "date-time")
    private LocalDateTime startDateTime;

}
