package ru.practicum.shareit.request;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.service.ItemRequestService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;


@Slf4j
@Validated
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
        return itemRequestService.findAll(userId);
    }

    /**
     * Запрос на получение всех запросов вещей
     **/
    @GetMapping("/all")
    public List<ItemRequestDto> getAll(
            @RequestHeader(value = "X-Sharer-User-Id") long userId,
            @RequestParam(defaultValue = "0") @PositiveOrZero int from,
            @RequestParam(defaultValue = "10") @Min(1) int size
    ) {
        log.info("   GET [http://localhost:8080/requests/all] : Запрос на получение всех запросов вещей");
        return itemRequestService.findAll(userId, PageRequest.of(from > 0 ? from / size : 0, size));
    }

    /**
     * Запрос на получение всех запросов вещей
     **/
    @GetMapping("/{requestId}")
    public ItemRequestDto get(@RequestHeader(value = "X-Sharer-User-Id") long userId, @PathVariable long requestId) {
        log.info("   GET [http://localhost:8080/requests/{}] : Запрос на получение всех запросов вещей", requestId);
        return itemRequestService.findOne(userId, requestId);
    }

    /**
     * Добавление нового запроса вещи
     **/
    @PostMapping
    public ItemRequestDto add(@RequestHeader(value = "X-Sharer-User-Id") long userId, @RequestBody @Valid ItemRequestDto requestDto) {
        log.info("  POST [http://localhost:8080/requests] : Запрос на добавление запроса вещи - {}", requestDto);
        return itemRequestService.save(userId, requestDto);
    }
}
