package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingRequestDto;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Slf4j
@Validated
@Controller
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingClient client;
    private final String headerShareitUserId = "X-Sharer-User-Id";

    /**
     * Запрос всех записей бронирования пользователя
     **/
    @GetMapping
    public ResponseEntity<Object> getAll(
            @RequestHeader(headerShareitUserId) @Positive long userId,
            @RequestParam(defaultValue = "ALL") String state,
            @RequestParam(defaultValue = "0") @PositiveOrZero int from,
            @RequestParam(defaultValue = "10") @Min(1) int size
    ) {
        log.info("   GET [http://localhost:8080/bookings?state={}] : Запрос на получение всех бронирований от пользователя {}", state, userId);
        return client.get(userId, state, from, size);
    }

    /**
     * Запрос записи бронирования по ID
     **/
    @GetMapping("/{bookingId}")
    public ResponseEntity<Object> getById(
            @RequestHeader(headerShareitUserId) @Positive long userId,
            @PathVariable @Positive long bookingId
    ) {
        log.info("   GET [http://localhost:8080/bookings/{}] : Запрос на получение бронирования по id : {}", bookingId, bookingId);
        return client.get(userId, bookingId);
    }

    /**
     * Получение списка бронирований для всех вещей текущего пользователя
     **/
    @GetMapping("/owner")
    public ResponseEntity<Object> getByIdOwner(
            @RequestHeader(headerShareitUserId) @Positive long userId,
            @RequestParam(defaultValue = "ALL") String state,
            @RequestParam(defaultValue = "0") @PositiveOrZero int from,
            @RequestParam(defaultValue = "10") @Min(1) int size
    ) {
        log.info("   GET [http://localhost:8080/bookings/owner?state={}] : Запрос на получение всех бронирований пользователя", state);
        return client.getOwner(userId, state, from, size);
    }

    /**
     * Добавление новой записи бронирования пользователя
     **/
    @PostMapping
    public ResponseEntity<Object> save(
            @RequestHeader(headerShareitUserId) @Positive long userId,
            @RequestBody @Valid BookingRequestDto bookingRequestDto
    ) {
        log.info("  POST [http://localhost:8080/bookings] : Запрос на добавление бронирования - {}", bookingRequestDto);
        return client.add(userId, bookingRequestDto);
    }

    /**
     * Подтверждение бронирования
     **/
    @PatchMapping("/{bookingId}")
    public ResponseEntity<Object> approved(
            @RequestHeader(headerShareitUserId) @Positive long userId,
            @PathVariable @Positive long bookingId,
            @RequestParam(defaultValue = "") boolean approved
    ) {
        log.info("  POST [http://localhost:8080/bookings/{}?approved={}] : Запрос на добавление бронирования - {}", bookingId, approved, bookingId);
        return client.approvedUp(userId, bookingId, approved);
    }
}
