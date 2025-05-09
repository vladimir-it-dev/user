package org.example.app.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "API для управления пользователями и подписками",
                version = "1.0.0",
                description = "API для создания, получения, обновления и удаления пользователей"
                        + " и их подписок на различные сервисы"
        )
)
public class ConfigOpenApi {

    public ConfigOpenApi() {
        log.info("Инициализация конфигурации OpenAPI для API управления пользователями и подписками");
    }
}
