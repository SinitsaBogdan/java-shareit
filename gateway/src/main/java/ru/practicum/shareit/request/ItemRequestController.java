package ru.practicum.shareit.request;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.ItemRequestDto;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import static ru.practicum.shareit.Constants.headerShareitUserId;

@Slf4j
@Validated
@RestController
@AllArgsConstructor
@RequestMapping("/requests")
public class ItemRequestController {

    private final ItemRequestClient client;

    /**
     * Запрос на получение всех запросов вещей пользователя
     **/
    @GetMapping
    public ResponseEntity<Object> getAllOwner(
            @RequestHeader(headerShareitUserId) @Positive long userId
    ) {
        log.info("   GET [http://localhost:8080/requests] : Запрос на получение всех запросов вещей");
        return client.get(userId);
    }

    /**
     * Запрос на получение всех запросов вещей
     **/
    @GetMapping("/all")
    public ResponseEntity<Object> getUsersAll(
            @RequestHeader(headerShareitUserId) @Positive long userId,
            @RequestParam(defaultValue = "0") @PositiveOrZero int from,
            @RequestParam(defaultValue = "10") @Min(1) int size
    ) {
        log.info("   GET [http://localhost:8080/requests/all] : Запрос на получение всех запросов вещей");
        return client.get(userId, from, size);
    }

    /**
     * Запрос на получение всех запросов вещей
     **/
    @GetMapping("/{requestId}")
    public ResponseEntity<Object> get(
            @RequestHeader(headerShareitUserId) @Positive long userId,
            @PathVariable long requestId
    ) {
        log.info("   GET [http://localhost:8080/requests/{}] : Запрос на получение всех запросов вещей", requestId);
        return client.get(userId, requestId);
    }

    /**
     * Добавление нового запроса вещи
     **/
    @PostMapping
    public ResponseEntity<Object> add(
            @RequestHeader(headerShareitUserId) @Positive long userId,
            @RequestBody @Valid ItemRequestDto requestDto
    ) {
        log.info("  POST [http://localhost:8080/requests] : Запрос на добавление запроса вещи - {}", requestDto);
        return client.add(userId, requestDto);
    }
}
