package ru.practicum.shareit.request;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.service.ItemRequestService;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/requests")
public class ItemRequestController {

    private final ItemRequestService itemRequestService;

    /**
     * Запрос на получение всех запросов вещей пользователя
     **/
    @GetMapping
    public List<ItemRequestDto> getAll(@RequestHeader(value = "X-Sharer-User-Id") long userId) {
        log.info("   GET [http://localhost:8080/requests] : Запрос на получение всех запросов вещей");
        return null;
    }

    /**
     * Запрос на получение всех запросов вещей
     **/
    @GetMapping("/all")
    public List<ItemRequestDto> getAll(@RequestHeader(value = "X-Sharer-User-Id") long userId, @RequestParam(defaultValue = "0") int from, @RequestParam(defaultValue = "0") int size) {
        log.info("   GET [http://localhost:8080/requests/all] : Запрос на получение всех запросов вещей");
        return null;
    }

    /**
     * Запрос на получение всех запросов вещей
     **/
    @GetMapping("/{requestId}")
    public List<ItemRequestDto> getAll(@RequestHeader(value = "X-Sharer-User-Id") long userId, @PathVariable long requestId) {
        log.info("   GET [http://localhost:8080/requests/{}] : Запрос на получение всех запросов вещей", requestId);
        return null;
    }

    /**
     * Добавление нового запроса вещи
     **/
    @PostMapping
    public ItemRequestDto add(@RequestHeader(value = "X-Sharer-User-Id") long userId, @RequestBody ItemRequestDto requestDto) {
        log.info("  POST [http://localhost:8080/requests] : Запрос на добавление запроса вещи - {}", requestDto);
        return null;
    }
}