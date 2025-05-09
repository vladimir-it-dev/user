package org.example.app.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@Schema(description = "Запрос на создание подписки")
public class SubscriptionRequest {

    @Schema(description = "Название подписки", example = "Netflix Premium")
    @Size(max = 255, message = "Название подписки не может превышать 255 символов")
    @NotBlank(message = "Название подписки не может быть пустым")
    private String subscriptionName;

    @Schema(description = "Дата и время начала подписки",
            example = "2023-12-31T23:59:59",
            format = "date-time")
    @NotNull(message = "Дата и время начала подписки не могут быть пустыми")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startDateTime;

}
