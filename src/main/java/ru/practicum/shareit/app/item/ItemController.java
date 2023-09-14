package ru.practicum.shareit.app.item;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.app.item.dto.ItemDto;
import ru.practicum.shareit.app.item.service.ItemService;

import java.util.List;

/**
 * TODO Sprint add-controllers.
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/items")
public class ItemController {

    private ItemService itemService;

    /**
     * Запрос всех записей
     * вещей пользователя
     **/
    @GetMapping
    public List<ItemDto> getAll(@RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("   GET [http://localhost:8080/items] : Запрос на получение всех вещей от пользователя id : {}", userId);
        return itemService.getAllByUserId(userId);
    }

    /**
     * Запрос записи вещи пользователя по ID
     **/
    @GetMapping("/{itemId}")
    public ItemDto getById(@RequestHeader(value = "X-Sharer-User-Id", defaultValue = "") Long userId, @PathVariable Long itemId) {
        log.info("   GET [http://localhost:8080/items/{}] : Запрос на получение вещи по id : {} от пользователя id : {}", itemId, itemId, userId);
        return itemService.getById(itemId);
    }

    /**
     * Запрос записей
     * вещей по поиску в text (название или описание)
     **/
    @GetMapping("/search")
    public List<ItemDto> getAllToSearchText(@RequestHeader(value = "X-Sharer-User-Id", defaultValue = "") Long userId, @RequestParam(defaultValue = "") String text) {
        log.info("   GET [http://localhost:8080/items/search?text={}] : Запрос на поиск вещей по фильтру text : {} от пользователя id : {} ", text, text, userId);
        return itemService.getBySearchText(text.toLowerCase());
    }

    /**
     * Добавление новой записи
     * вещи пользователя
     **/
    @PostMapping
    public ItemDto add(@RequestHeader(value = "X-Sharer-User-Id", defaultValue = "") Long userId, @RequestBody ItemDto item) {
        log.info("  POST [http://localhost:8080/items] : Запрос на добавление вещи от пользователя id : {} - {}", userId, item);
        return itemService.addToUser(userId, item);
    }

    /**
     * Обновление существующей записи
     * вещи пользователя
     **/
    @PatchMapping("/{itemId}")
    public ItemDto update(@RequestHeader(value = "X-Sharer-User-Id", defaultValue = "") Long userId, @PathVariable Long itemId, @RequestBody ItemDto item) {
        log.info(" PATCH [http://localhost:8080/items] : Запрос на обновление вещи от пользователя id : {} - {}", userId, item);
        item.setId(itemId);
        return itemService.updateToUser(userId, item);
    }

    /**
     * Удаление существующей записи
     * вещи пользователя
     **/
    @DeleteMapping("/{itemDtoId}")
    public void delete(@RequestHeader(value = "X-Sharer-User-Id", defaultValue = "") Long userId, @PathVariable Long itemId) {
        log.info(" DELETE [http://localhost:8080/items/{}] : Запрос на удаление вещи id : {} от пользователя id : {}", itemId, itemId, userId);
    }
}
