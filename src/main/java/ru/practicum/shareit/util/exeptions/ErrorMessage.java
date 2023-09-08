package ru.practicum.shareit.util.exeptions;

import lombok.Getter;

@Getter
public enum ErrorMessage {

    GLOBAL_ERROR__NOT_HEADER_IN_REQUEST("Ошибка формата запроса!", "В запросе отсутствует заголовок X-Sharer-User-Id", 500),

    REPOSITORY_ERROR__ITEM__ID_NOT_IN_REPO__ID("Ошибка формата запроса!", "Указанный в заголовке X-Sharer-User-Id отсутствует в системе", 404),

    ITEM_ERROR__VALID__NAME("Ошибка формирования тела запроса!", "Поле name отсутствует или не в верном формате", 400),
    ITEM_ERROR__VALID__DESCRIPTION("Ошибка формирования тела запроса!", "Поле description отсутствует или не в верном формате", 400),
    ITEM_ERROR__VALID__AVAILABLE("Ошибка формирования тела запроса!", "Поле available отсутствует или не в верном формате", 400)
    ;

    private final String name;

    private final String description;

    private final int httpStatusCode;

    ErrorMessage(String name, String description, int httpStatusCode) {
        this.name = name;
        this.description = description;
        this.httpStatusCode = httpStatusCode;
    }
}