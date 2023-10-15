package ru.practicum.shareit.util.exeptions;

import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class HandlerController {

    @ExceptionHandler({BusinessException.class, ServiceException.class, RepositoryException.class, CustomException.class})
    public ResponseEntity<Response> handleException(@NotNull BusinessException exception) {
        Response response = new Response(exception.getMessage(), null);
        return new ResponseEntity<>(response, exception.getHttpStatus());
    }
}