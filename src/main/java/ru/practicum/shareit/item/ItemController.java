package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.service.ItemService;

import java.util.List;

/**
 * TODO Sprint add-controllers.
 *  1. Добавить логирование
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    /**
     * Запрос всех записей
     * вещей пользователя
     **/
    @GetMapping
    public List<ItemDto> getAll(@RequestHeader("X-Later-User-Id") long userId) {
        log.info(
                "   GET [http://localhost:8080/items] : " +
                        "Запрос на получение всех вещей от пользователя id : {}",
                userId
        );
        return null;
    }

    /**
     * Запрос записи вещи пользователя по ID
     **/
    @GetMapping("/{itemId}")
    public ItemDto getById(
            @RequestHeader("X-Later-User-Id") long userId,
            @RequestParam long itemId
    ) {
        log.info(
                "   GET [http://localhost:8080/items/{}] : " +
                        "Запрос на получение вещи по id : {} от пользователя id : {}",
                itemId, itemId, userId
        );
        return null;
    }

    /**
     * Запрос записей
     * вещей по поиску в text ( название или описание )
     **/
    @GetMapping("/search")
    public List<ItemDto> getAllToSearchText(
            @RequestHeader("X-Later-User-Id") long userId,
            @RequestParam(required = false) String text
    ) {
        log.info(
                "   GET [http://localhost:8080/items/search?text={}] : " +
                        "Запрос на поиск вещей по фильтру text : {} от пользователя id : {} ",
                text, text, userId
        );
        return null;
    }

    /**
     * Добавление новой записи
     * вещи пользователя
     **/
    @PostMapping
    public ItemDto add(
            @RequestHeader("X-Later-User-Id") long userId,
            @RequestBody ItemDto item
    ) {
        log.info(
                "  POST [http://localhost:8080/items] : " +
                        "Запрос на добавление вещи от пользователя id : {} - {}",
                userId, item
        );
        return null;
    }

    /**
     * Обновление существующей записи
     * вещи пользователя
     **/
    @PatchMapping
    public ItemDto update(
            @RequestHeader("X-Later-User-Id") long userId,
            @RequestBody ItemDto item
    ) {
        log.info(
                " PATCH [http://localhost:8080/items] : " +
                        "Запрос на обновление вещи от пользователя id : {} - {}",
                userId, item
        );
        return null;
    }

    /**
     * Удаление существующей записи
     * вещи пользователя
     **/
    @DeleteMapping("/{itemDtoId}")
    public void delete(
            @RequestHeader("X-Later-User-Id") long userId,
            @PathVariable long itemId
    ) {
        log.info(
                " DELETE [http://localhost:8080/items/{}] : " +
                        "Запрос на удаление вещи id : {} от пользователя id : {}",
                itemId, itemId, userId
        );
    }
}
