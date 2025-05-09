package org.example.app.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Топ-3 популярных подписок")
public class SubscriptionResponseTOP3 {

    @Schema(description = "Название подписки", example = "Netflix Premium")
    private String subscriptionName;

    @Schema(description = "Количество пользователей, подписанных на эту подписку", example = "100")
    private Integer usersCount;
}
