package org.example.app.exception.schema;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "Ответ с ошибкой")
public class ErrorUserExistsByEmailSchema {

    @Schema(description = "Сообщение об ошибке",
            example = "Пользователь с таким Email уже есть в базе данных. " +
                    "Пожалуйста, используйте другой Email.")
    private String messageError;
}
