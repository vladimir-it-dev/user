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
import org.example.app.dto.SubscriptionRequest;
import org.example.app.dto.SubscriptionResponse;
import org.example.app.dto.SubscriptionResponseTOP3;
import org.example.app.exception.schema.ErrorSubscriptionExistsByUserSchema;
import org.example.app.exception.schema.ErrorSubscriptionNotFoundByIDSchema;
import org.example.app.exception.schema.ErrorUserNotFoundByIDSchema;
import org.example.app.exception.schema.ErrorUserOrSubscriptionValidationSchema;
import org.example.app.service.SubscriptionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "Подписки", description = "API для работы с подписками пользователей")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @Operation(summary = "Создание подписки для пользователя")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Подписка успешно создана",
                    content = @Content(
                            mediaType = "text/plain",
                            schema = @Schema(implementation = String.class),
                            examples = @ExampleObject(
                                    value = "Подписка Netflix Premium успешно сохранена у пользователя с " +
                                            "ID: 1, имя: Иван, email: user@example.com"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Невалидные данные для создания подписки",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorUserOrSubscriptionValidationSchema.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Пользователь не найден",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorUserNotFoundByIDSchema.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Подписка уже существует",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorSubscriptionExistsByUserSchema.class)
                    )
            )
    })
    @PostMapping("/{id}/subscriptions")
    public String createSubscriptionUser(
            @Parameter(
                    description = "Идентификатор пользователя",
                    example = "1",
                    required = true
            )
            @PathVariable("id") Long idUser,
            @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Данные для создания подписки",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SubscriptionRequest.class)
                    )
            ) @Valid SubscriptionRequest subscriptionRequest) {

        log.info("Попытка создать подписку для пользователя с id {}, подписка {}",
                idUser, subscriptionRequest.getSubscriptionName());
        return subscriptionService.createSubscriptionUser(idUser, subscriptionRequest);
    }

    @Operation(summary = "Получение подписок пользователя")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Подписки успешно получены",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SubscriptionResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Пользователь не найден",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorUserNotFoundByIDSchema.class)
                    )
            )
    })
    @GetMapping("/{id}/subscriptions")
    public List<SubscriptionResponse> getSubscriptionUser(
            @Parameter(
                    description = "Идентификатор пользователя",
                    example = "1",
                    required = true
            )
            @PathVariable("id") Long idUser) {

        log.info("Попытка получить подписки для пользователя с id {}", idUser);
        return subscriptionService.getSubscriptionsUser(idUser);
    }

    @Operation(summary = "Удаление подписки пользователя")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Подписка успешно удалена",
                    content = @Content(
                            mediaType = "text/plain",
                            schema = @Schema(implementation = String.class),
                            examples = @ExampleObject(
                                    value = "Подписка Netflix Premium успешно удалена у пользователя с " +
                                            "ID: 1, имя: Иван, email: user@example.com")
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Пользователь не найден",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorUserNotFoundByIDSchema.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Подписка не найдена",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorSubscriptionNotFoundByIDSchema.class)
                    )
            )
    })
    @DeleteMapping("/{id}/subscriptions/{sub_id}")
    public String deleteSubscriptionUser(
            @Parameter(
                    description = "Идентификатор пользователя",
                    example = "1",
                    required = true
            )
            @PathVariable("id") Long idUser,
            @Parameter(
                    description = "Идентификатор подписки",
                    example = "1",
                    required = true
            )
            @PathVariable("sub_id") Long idSubscription) {
        log.info("Попытка удалить подписку с id {} для пользователя с id {}", idSubscription, idUser);
        return subscriptionService.deleteSubscriptionUser(idUser, idSubscription);
    }

    @Operation(summary = "Получение топ 3 подписок")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Топ 3 подписок успешно получены",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SubscriptionResponseTOP3.class)
                    )
            )
    })
    @GetMapping("/subscriptions/top")
    public List<SubscriptionResponseTOP3> getSubscriptionTOP3() {
        log.info("Попытка получить топ 3 подписок");
        return subscriptionService.getSubscriptionTOP3();
    }
}
