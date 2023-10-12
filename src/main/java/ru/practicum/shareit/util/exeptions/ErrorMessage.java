package ru.practicum.shareit.util.exeptions;

import lombok.Getter;

@Getter
public enum ErrorMessage {

    GLOBAL_ERROR__NOT_HEADER_IN_REQUEST("В запросе отсутствует заголовок X-Sharer-User-Id", 500),

    REPOSITORY_ERROR__ITEM__ID_NOT_IN_REPO__ID("Указанный id вещи отсутствует в системе", 404),
    REPOSITORY_ERROR__USER__ID_NOT_IN_REPO__ID("Указанный id пользователя отсутствует в системе", 404),
    REPOSITORY_ERROR__BOOKING__ID_NOT_IN_REPO__ID("Указанный id бронирования отсутствует в системе", 404),
    REPOSITORY_ERROR__REQUEST__ID_NOT_IN_REPO__ID("Указанный id запроса отсутствует в системе", 404),

    ITEM_ERROR__VALID__NAME("Поле name отсутствует или не в верном формате", 400),
    ITEM_ERROR__VALID__DESCRIPTION("Поле description отсутствует или не в верном формате", 400),
    ITEM_ERROR__VALID__AVAILABLE("Поле available отсутствует или не в верном формате", 400),
    ITEM_ERROR__VALID__ITEM__USER_NOT_OWNER("Пользователь не является владельцем записи", 403),

    USER_ERROR__VALID_EMPTY__NAME("Name отсутствует в теле запроса или пустое", 400),
    USER_ERROR__VALID__EMAIL("Не корректный формат поля email", 400),
    USER_ERROR__VALID_DUPLICATE__EMAIL("Email уже используется другим пользователем", 409),
    USER_ERROR__VALID_EMPTY__EMAIL("Email отсутствует в теле запроса или пустое", 400),

    BOOKING_ERROR__USER_NOT_OWNER_ITEM("Пользователь не является владельцем вещи", 404),
    BOOKING_ERROR__AVAILABLE_FALSE("Вещь не доступна для бронирования", 400),
    BOOKING_ERROR__VALID_DATETIME("Время окончания бронирования не может быть меньше или равной началу бронирования", 400),
    BOOKING_ERROR__VALID_DATETIME__START_TIME("Дата начала бронирования не должна быть в прошлом", 400),


    COMMENT_ERROR__VALID_TEXT("Текст отзыва не может быть null или пустым", 400)
    ;

    private final String description;

    private final int httpStatusCode;

    ErrorMessage(String description, int httpStatusCode) {
        this.description = description;
        this.httpStatusCode = httpStatusCode;
    }
}