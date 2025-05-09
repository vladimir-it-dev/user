package org.example.app.exception.schema;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@Schema(description = "Ошибка валидации")
public class ErrorUserOrSubscriptionValidationSchema {

    @Schema(description = "Сообщение об ошибке", example = "Ошибка валидации")
    private String message;

    @Schema(description = "Время получения ошибки валидации", example = "2024-05-15T14:30:00")
    private LocalDateTime timestamp;

    @Schema(description = "Список ошибок")
    private List<FieldMessageError> errors;

    @Getter
    @AllArgsConstructor
    public static class FieldMessageError {

        @Schema(description = "Имя поля, в котором произошла ошибка", example = "name")
        private String fieldName;

        @Schema(description = "Сообщение об ошибке",
                example = "Имя пользователя обязательно для заполнения(Название подписки не может быть пустым)")
        private String message;

    }
}
