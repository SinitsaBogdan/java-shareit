package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingRequestDto;

@Slf4j
@Validated
@Controller
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingClient client;

    /**
     * Запрос всех записей бронирования пользователя
     **/
    @GetMapping
    public ResponseEntity<Object> getAll(
            @RequestHeader(value = "X-Sharer-User-Id") long userId,
            @RequestParam String state,
            @RequestParam int from,
            @RequestParam int size
    ) {
        log.info("   GET [http://localhost:8080/bookings?state={}] : Запрос на получение всех бронирований от пользователя {}", state, userId);
        return client.get(userId, state, from, size);
    }

    /**
     * Запрос записи бронирования по ID
     **/
    @GetMapping("/{bookingId}")
    public ResponseEntity<Object> getById(
            @RequestHeader(value = "X-Sharer-User-Id") long userId,
            @PathVariable long bookingId
    ) {
        log.info("   GET [http://localhost:8080/bookings/{}] : Запрос на получение бронирования по id : {}", bookingId, bookingId);
        return client.get(userId, bookingId);
    }

    /**
     * Получение списка бронирований для всех вещей текущего пользователя
     **/
    @GetMapping("/owner")
    public ResponseEntity<Object> getById(
            @RequestHeader(value = "X-Sharer-User-Id") long userId,
            @RequestParam String state,
            @RequestParam int from,
            @RequestParam int size
    ) {
        log.info("   GET [http://localhost:8080/bookings/owner?state={}] : Запрос на получение всех бронирований пользователя", state);
        return client.get(userId, state, from, size);
    }

    /**
     * Добавление новой записи бронирования пользователя
     **/
    @PostMapping
    public ResponseEntity<Object> save(@RequestHeader(value = "X-Sharer-User-Id") long userId, @RequestBody BookingRequestDto bookingRequestDto) {
        log.info("  POST [http://localhost:8080/bookings] : Запрос на добавление бронирования - {}", bookingRequestDto);
        return client.add(userId, bookingRequestDto);
    }

    /**
     * Подтверждение бронирования
     **/
    @PatchMapping("/{bookingId}")
    public ResponseEntity<Object> approved(@RequestHeader(value = "X-Sharer-User-Id") long userId, @PathVariable long bookingId, @RequestParam(defaultValue = "") boolean approved) {
        log.info("  POST [http://localhost:8080/bookings/{}?approved={}] : Запрос на добавление бронирования - {}", bookingId, approved, bookingId);
        return client.update(userId, bookingId, approved);
    }
}
