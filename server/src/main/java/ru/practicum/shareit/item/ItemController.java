package ru.practicum.shareit.item;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.service.ItemService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    /** Запрос всех записей вещей пользователя **/
    @GetMapping
    public List<ItemDto> getAll(@RequestHeader("X-Sharer-User-Id") long userId) {
        log.info("   GET [http://localhost:8080/items] : Запрос на получение всех вещей от пользователя id : {}", userId);
        return itemService.getAllByUserId(userId);
    }

    /** Запрос записи вещи пользователя по ID **/
    @GetMapping("/{itemId}")
    public ItemDto getById(@RequestHeader(value = "X-Sharer-User-Id") long userId, @PathVariable long itemId) {
        log.info("   GET [http://localhost:8080/items/{}] : Запрос на получение вещи по id : {} от пользователя id : {}", itemId, itemId, userId);
        return itemService.getById(userId, itemId);
    }

    /** Запрос записей вещей по поиску в text (название или описание) **/
    @GetMapping("/search")
    public List<ItemDto> getAllToSearchText(@RequestHeader(value = "X-Sharer-User-Id") long userId, @RequestParam(defaultValue = "") String text) {
        log.info("   GET [http://localhost:8080/items/search?text={}] : Запрос на поиск вещей по фильтру text : {} от пользователя id : {} ", text, text, userId);
        return itemService.getBySearchText(text);
    }

    /** Добавление новой записи вещи пользователя **/
    @PostMapping
    public ItemDto saveItem(@RequestHeader(value = "X-Sharer-User-Id") long userId, @RequestBody @Valid ItemDto item) {
        log.info("  POST [http://localhost:8080/items] : Запрос на добавление вещи от пользователя id : {} - {}", userId, item);
        return itemService.saveItem(userId, item);
    }

    /** Добавление комментария к вещи **/
    @PostMapping("/{itemId}/comment")
    public CommentDto saveComment(@RequestHeader(value = "X-Sharer-User-Id") long userId, @PathVariable long itemId, @RequestBody CommentDto commentDto) {
        log.info("  POST [http://localhost:8080/items/{}/comment] : Запрос на добавление отзыва к вещи от пользователя id : {} - {}", itemId, userId, commentDto);
        return itemService.saveComment(userId, itemId, commentDto);
    }

    /** Обновление существующей записи вещи пользователя **/
    @PatchMapping("/{itemId}")
    public ItemDto update(@RequestHeader(value = "X-Sharer-User-Id") long userId, @PathVariable long itemId, @RequestBody ItemDto item) {
        log.info(" PATCH [http://localhost:8080/items/{}] : Запрос на обновление вещи от пользователя id : {} - {}", userId, itemId, item);
        item.setId(itemId);
        return itemService.updateItem(userId, item);
    }
}
