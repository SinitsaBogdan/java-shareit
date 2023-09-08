package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.service.ItemRequestService;

import java.util.List;

/**
 * TODO Sprint add-item-requests.
 *  1. Добавить логирование
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/requests")
public class ItemRequestController {

    private final ItemRequestService itemRequestService;

    /**
     * Запрос всех запросов вещей
     **/
    @GetMapping
    public List<ItemRequestDto> getAll(@RequestHeader("X-Later-User-Id") long userId) {
        log.info(
                "   GET [http://localhost:8080/requests] : " +
                        "Запрос на получение всех запросов вещей от пользователя id : {}",
                userId
        );
        return null;
    }

    /**
     * Запрос запроса вещи по ID
     **/
    @GetMapping
    public ItemRequestDto getById(
            @RequestHeader("X-Later-User-Id") long userId,
            @RequestParam long itemId
    ) {
        log.info(
                "   GET [http://localhost:8080/requests/{}] : " +
                        "Запрос на получение запроса вещи id : {} от пользователя id : {}",
                itemId, itemId, userId
        );
        return null;
    }

    /**
     * Добавление новой записи запроса вещи
     **/
    @PostMapping
    public ItemRequestDto add(
            @RequestHeader("X-Later-User-Id") long userId,
            @RequestBody ItemDto item
    ) {
        log.info(
                "  POST [http://localhost:8080/requests] : " +
                        "Запрос на добавление запрова вещи от пользователя id : {} - {}",
                userId, item
        );
        return null;
    }

    /**
     * Обновление существующего запроса вещи
     **/
    @PatchMapping
    public ItemRequestDto update(
            @RequestHeader("X-Later-User-Id") long userId,
            @RequestBody ItemRequestDto itemRequest
    ) {
        log.info(
                " PATCH [http://localhost:8080/requests] : " +
                        "Запрос на обновление запроса вещи от пользователя id : {} - {}",
                userId, itemRequest
        );
        return null;
    }

    /**
     * Удаление существующего запроса вещи
     **/
    @DeleteMapping("/{itemRequestId}")
    public void delete(
            @RequestHeader("X-Later-User-Id") long userId,
            @PathVariable long itemRequestId
    ) {
        log.info(
                " DELETE [http://localhost:8080/requests/{}] : " +
                        "Запрос на удаление запроса вещи id : {} от пользователя id : {}",
                itemRequestId, itemRequestId, userId
        );
    }
}
