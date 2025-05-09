package org.example.app.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.app.dto.UserRequest;
import org.example.app.dto.UserResponse;
import org.example.app.exception.schema.ErrorUserExistsByEmailSchema;
import org.example.app.exception.schema.ErrorUserNotFoundByIDSchema;
import org.example.app.exception.schema.ErrorUserOrSubscriptionValidationSchema;
import org.example.app.service.UserService;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "Управление пользователями",
        description = "API для создания, получения, обновления и удаления пользователей")
public class UserController {

    private final UserService userService;

    @Operation(summary = "Создание пользователя", description = "Создает нового пользователя в системе")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Пользователь успешно создан",
                    content = @Content(
                            mediaType = "text/plain",
                            schema = @Schema(implementation = String.class),
                            examples = @ExampleObject(value =
                                    "Пользователь с ID: 1, имя: Иван," +
                                            " email: user@example.com успешно сохранен в базе данных")
                    )),
            @ApiResponse(responseCode = "400", description = "Невалидные данные запроса",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorUserOrSubscriptionValidationSchema.class)

                    )),
            @ApiResponse(
                    responseCode = "409",
                    description = "Пользователь с таким email уже существует",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorUserExistsByEmailSchema.class)
                    )
            )
    })
    @PostMapping
    public String createUser(
            @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Данные для создания пользователя",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserRequest.class)
                    )
            )
            @Valid UserRequest userRequest) {

        log.info("Попытка создать пользователя с именем: {}", userRequest.getName());
        return userService.createUser(userRequest);
    }

    @Operation(summary = "Получение информации о пользователе",
            description = "Возвращает информацию о пользователе по его ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Информация о пользователе успешно получена",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class)
                    )),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден по ID",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorUserNotFoundByIDSchema.class)
                    ))
    })
    @GetMapping("/{id}")
    public UserResponse getInfoUser(
            @Parameter(
                    description = "ID пользователя",
                    example = "1",
                    required = true
            )
            @PathVariable("id") Long idUser) {

        log.info("Запрос информации о пользователе с id: {}", idUser);
        return userService.getInfoUser(idUser);
    }

    @Operation(summary = "Обновление информации о пользователе",
            description = "Обновляет информацию о пользователе по его ID")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Информация о пользователе успешно обновлена",
                    content = @Content(
                            mediaType = "text/plain",
                            schema = @Schema(implementation = String.class),
                            examples = @ExampleObject(value = "Пользователь с ID: 1, имя: Иван," +
                                    " email: user@example.com успешно обновлен в базе данных")
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Невалидные данные запроса",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorUserOrSubscriptionValidationSchema.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Пользователь не найден по ID",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorUserNotFoundByIDSchema.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Пользователь с таким email уже существует",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorUserExistsByEmailSchema.class)
                    )
            )
    })
    @PutMapping("/{id}")
    public String updateUser(
            @Parameter(
                    description = "ID пользователя",
                    example = "1",
                    required = true
            )
            @PathVariable("id") Long idUser,
            @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Данные для обновления пользователя",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserRequest.class)
                    )
            )
            @Valid UserRequest userRequest) {

        log.info("Попытка обновить пользователя с id: {}", idUser);
        return userService.updateUser(idUser, userRequest);
    }

    @Operation(summary = "Удаление пользователя",
            description = "Удаляет пользователя по его ID")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Пользователь успешно удален",
                    content = @Content(
                            mediaType = "text/plain",
                            schema = @Schema(implementation = String.class),
                            examples = @ExampleObject(value = "Пользователь с ID: 1, имя: Иван," +
                                    " email: user@example.com успешно удален из базы данных")
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Пользователь не найден по ID",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorUserNotFoundByIDSchema.class)
                    )
            )
    })
    @DeleteMapping("/{id}")
    public String deleteUser(
            @Parameter(
                    description = "ID пользователя",
                    example = "1",
                    required = true
            )
            @PathVariable("id") Long idUser) {

        log.info("Попытка удалить пользователя с id: {}", idUser);
        return userService.deleteUser(idUser);
    }
}
