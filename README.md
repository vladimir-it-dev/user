# Микросервис управления пользователями и их подписками

Микросервис на Java с использованием Spring Boot для управления пользователями и их подписками.

### Технологический стек

* **Java 17**
* **Spring Boot 3.1.5**
* **Spring Data JPA** - для ORM и доступа к данным
* **PostgreSQL** - в качестве хранилища данных
* **Lombok** - для уменьшения бойлерплейт-кода
* **Swagger/OpenAPI** - для документации API
* **Docker & Docker Compose** - для контейнеризации и оркестрации
* **Flyway** - для управления миграциями базы данных

## Инструкция по запуску проекта
1. Клонирование репозитория:   git clone https://github.com/vladimir-it-dev/users-and-subscriptions.git
2. Запуск микросервиса через Docker:   docker compose up -d
3. Все эндпоинты доступны через Swagger UI:   http://localhost:8080/swagger-ui.html
4. Логи приложения выводятся в консоль, а также пишутся в файл /logs/application.log.
5. Посмотреть логи через Docker:   docker logs users-subscriptions

## Доступ к сервисам
- Сервис	     Порт	Описание
- Приложение -	 8080	Основное API
- PostgreSQL -	 5433	База данных

## Работа с API
Доступные эндпоинты:
- `POST /users` - Создать нового пользователя
- `GET /users/{id}` - Получить информацию о пользователе
- `PUT /users/{id}` - Обновить информацию о пользователе
- `DELETE /users/{id}` - Удалить пользователя

- `POST /users/{id}/subscriptions` - Создать новую подписку
- `GET /users/{id}/subscriptions` - Получить список подписок пользователя
- `DELETE /users/{id}/subscriptions/{sub_Id}` - Удалить подписку
- `GET /users/subscriptions/top` - Получить ТОП-3 популярных подписок



