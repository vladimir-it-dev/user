package org.example.app.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Запрос для создания/обновления пользователя")
public class UserRequest {

    @Schema(description = "Имя пользователя", example = "Иван")
    @Size(min = 2, max = 30, message = "Имя пользователя должно содержать от 2 до 30 символов")
    @NotBlank(message = "Имя пользователя обязательно для заполнения")
    private String name;

    @Schema(description = "Email пользователя", example = "user@example.com")
    @NotBlank(message = "Email пользователя обязателен для заполнения")
    @Email(message = "Некорректный формат email")
    private String email;

}
