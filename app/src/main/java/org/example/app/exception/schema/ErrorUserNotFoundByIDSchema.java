package org.example.app.exception.schema;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "Ответ с ошибкой")
public class ErrorUserNotFoundByIDSchema {

    @Schema(description = "Сообщение об ошибке",
            example = "Пользователь с таким ID не найден")
    private String messageError;
}
