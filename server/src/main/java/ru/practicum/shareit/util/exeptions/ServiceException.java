package ru.practicum.shareit.util.exeptions;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;

@Slf4j
@Data
@EqualsAndHashCode(callSuper = true)
public class ServiceException extends RuntimeException {

    HttpStatus httpStatus;

    public ServiceException(@NotNull ErrorMessage message) {
        super(message.getDescription());
        this.httpStatus = HttpStatus.valueOf(message.getHttpStatusCode());
        log.debug("{} | {}", message.getHttpStatusCode(), message);
    }

    public ServiceException(@NotNull String message, int statusCode) {
        super(message);
        this.httpStatus = HttpStatus.valueOf(statusCode);
        log.debug("{} | {}", statusCode, message);
    }
}
