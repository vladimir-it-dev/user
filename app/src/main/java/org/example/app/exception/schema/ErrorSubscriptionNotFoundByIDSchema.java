package org.example.app.exception.schema;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "Ответ с ошибкой")
public class ErrorSubscriptionNotFoundByIDSchema {

    @Schema(description = "Сообщение об ошибке",
            example = "Подписка не найдена у пользователя")
    private String messageError;
}
