package ru.practicum.shareit.request;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.service.ItemRequestService;

import java.util.List;

/**
 * TODO Sprint add-item-requests.
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/requests")
public class ItemRequestController {

    private ItemRequestService itemRequestService;

    /**
     * Запрос всех запросов вещей
     **/
    @GetMapping
    public List<ItemRequestDto> getAll() {
        log.info(
                "   GET [http://localhost:8080/requests] : " +
                        "Запрос на получение всех запросов вещей"
        );
        return null;
    }

    /**
     * Запрос запроса вещи по ID
     **/
    @GetMapping("/{itemId}")
    public ItemRequestDto getById(@PathVariable Long itemId) {
        log.info(
                "   GET [http://localhost:8080/requests/{}] : " +
                        "Запрос на получение запроса вещи id : {}",
                itemId, itemId
        );
        return null;
    }

    /**
     * Добавление новой записи запроса вещи
     **/
    @PostMapping
    public ItemRequestDto add(@RequestBody ItemDto item) {
        log.info(
                "  POST [http://localhost:8080/requests] : " +
                        "Запрос на добавление запроса вещи - {}",
                item
        );
        return null;
    }

    /**
     * Обновление существующего запроса вещи
     **/
    @PatchMapping
    public ItemRequestDto update(@RequestBody ItemRequestDto itemRequest) {
        log.info(
                " PATCH [http://localhost:8080/requests] : " +
                        "Запрос на обновление запроса вещи - {}",
                itemRequest
        );
        return null;
    }

    /**
     * Удаление существующего запроса вещи
     **/
    @DeleteMapping("/{itemRequestId}")
    public void delete(@PathVariable Long itemRequestId) {
        log.info(
                " DELETE [http://localhost:8080/requests/{}] : " +
                        "Запрос на удаление запроса вещи id : {}",
                itemRequestId, itemRequestId
        );
    }
}
