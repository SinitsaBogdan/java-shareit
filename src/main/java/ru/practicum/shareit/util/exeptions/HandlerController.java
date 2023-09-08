package ru.practicum.shareit.util.exeptions;

import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class HandlerController {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(@NotNull ShareitException exception) {
        return new ResponseEntity<>(
                ErrorResponse.builder().name(exception.getName()).description(exception.getDescription()).build(),
                HttpStatus.valueOf(exception.getHttpStatusCode())
        );
    }
}