package ru.practicum.shareit.util.exeptions;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@Slf4j
@Data
@EqualsAndHashCode(callSuper = true)
public class CustomException extends RuntimeException {

    HttpStatus httpStatus;

    public CustomException(String message, int statusCode) {
        super(message);
        this.httpStatus = HttpStatus.valueOf(statusCode);
        log.debug("{} | {}", statusCode, message);
    }
}
