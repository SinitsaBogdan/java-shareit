package ru.practicum.shareit.util.exeptions;

import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ValidationException;

@ControllerAdvice
public class HandlerController {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Response> handleException(@NotNull BusinessException exception) {
        Response response = new Response(exception.getMessage(), null);
        return new ResponseEntity<>(response, exception.getHttpStatus());
    }

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<Response> handleException(@NotNull ServiceException exception) {
        Response response = new Response(exception.getMessage(), null);
        return new ResponseEntity<>(response, exception.getHttpStatus());
    }

    @ExceptionHandler(RepositoryException.class)
    public ResponseEntity<Response> handleException(@NotNull RepositoryException exception) {
        Response response = new Response(exception.getMessage(), null);
        return new ResponseEntity<>(response, exception.getHttpStatus());
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Response> handleException(@NotNull CustomException exception) {
        Response response = new Response(exception.getMessage(), null);
        return new ResponseEntity<>(response, exception.getHttpStatus());
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Response> handleAllExceptions(@NotNull ValidationException exception) {
        Response response = new Response(exception.getMessage(), null);
        return new ResponseEntity<>(response, HttpStatus.valueOf(400));
    }
}