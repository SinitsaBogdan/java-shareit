package ru.practicum.shareit.util.exeptions;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;

@Slf4j
@Data
@EqualsAndHashCode(callSuper = true)
public class BusinessException extends RuntimeException {

    HttpStatus httpStatus;

    public BusinessException(@NotNull ErrorMessage message) {
        super(message.getDescription());
        this.httpStatus = HttpStatus.valueOf(message.getHttpStatusCode());
        log.debug("{} | {}", message.getHttpStatusCode(), message);
    }
}
