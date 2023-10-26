package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;

import javax.validation.Valid;

@Slf4j
@Validated
@Controller
@RequestMapping(path = "/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemClient client;

    /** Запрос всех записей вещей пользователя **/
    @GetMapping
    public ResponseEntity<Object> getAll(@RequestHeader("X-Sharer-User-Id") long userId) {
        log.info("   GET [http://localhost:8080/items] : Запрос на получение всех вещей от пользователя id : {}", userId);
        return client.getItems(userId);
    }

    /** Запрос записи вещи пользователя по ID **/
    @GetMapping("/{itemId}")
    public ResponseEntity<Object> getById(@RequestHeader(value = "X-Sharer-User-Id") long userId, @PathVariable long itemId) {
        log.info("   GET [http://localhost:8080/items/{}] : Запрос на получение вещи по id : {} от пользователя id : {}", itemId, itemId, userId);
        return client.getItemById(userId, itemId);
    }

    /** Запрос записей вещей по поиску в text (название или описание) **/
    @GetMapping("/search")
    public ResponseEntity<Object> getAllToSearchText(@RequestHeader(value = "X-Sharer-User-Id") long userId, @RequestParam(defaultValue = "") String text) {
        log.info("   GET [http://localhost:8080/items/search?text={}] : Запрос на поиск вещей по фильтру text : {} от пользователя id : {} ", text, text, userId);
        return client.getAllToSearchText(userId, text.toLowerCase());
    }

    /** Добавление новой записи вещи пользователя **/
    @PostMapping
    public ResponseEntity<Object> saveItem(@RequestHeader(value = "X-Sharer-User-Id") long userId, @RequestBody @Valid ItemDto item) {
        log.info("  POST [http://localhost:8080/items] : Запрос на добавление вещи от пользователя id : {} - {}", userId, item);
        return client.add(userId, item);
    }

    /** Добавление комментария к вещи **/
    @PostMapping("/{itemId}/comment")
    public ResponseEntity<Object> saveComment(@RequestHeader(value = "X-Sharer-User-Id") long userId, @PathVariable long itemId, @RequestBody @Valid CommentDto commentDto) {
        log.info("  POST [http://localhost:8080/items/{}/comment] : Запрос на добавление отзыва к вещи от пользователя id : {} - {}", itemId, userId, commentDto);
        return client.add(userId, itemId, commentDto);
    }

    /** Обновление существующей записи вещи пользователя **/
    @PatchMapping("/{itemId}")
    public ResponseEntity<Object> update(@RequestHeader(value = "X-Sharer-User-Id") long userId, @PathVariable long itemId, @RequestBody ItemDto item) {
        log.info(" PATCH [http://localhost:8080/items{}] : Запрос на обновление вещи от пользователя id : {} - {}", itemId, userId, item);
        item.setId(itemId);
        return client.update(userId, itemId, item);
    }
}
