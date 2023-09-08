package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.user.service.UserService;

import java.util.List;

/**
 * TODO Sprint add-bookings.
 *  1. Добавить логирование
 */

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/bookings")
public class BookingController {

    private final BookingService bookingService;

    /**
     * Запрос всех записей
     * бронирования пользователя
    **/
    @GetMapping
    public List<BookingDto> getAll(@RequestHeader("X-Later-User-Id") long userId) {
        log.info(
                "   GET [http://localhost:8080/bookings] : " +
                        "Запрос на получение всех бронирований от пользователя id : {}",
                userId
        );
        return null;
    }

    /**
     * Запрос записи бронирования по ID
     **/
    @GetMapping("/{bookingId}")
    public BookingDto getById(
            @RequestHeader("X-Later-User-Id") long userId,
            @RequestParam long bookingId
    ) {
        log.info(
                "   GET [http://localhost:8080/bookings/{}] : " +
                        "Запрос на получение бронирования по id : {} от пользователя id : {}",
                bookingId, bookingId, userId
        );
        return null;
    }

    /**
     * Добавление новой записи
     * бронирования пользователя
     **/
    @PostMapping
    public BookingDto add(
            @RequestHeader("X-Later-User-Id") long userId,
            @RequestBody BookingDto booking
    ) {
        log.info(
                "  POST [http://localhost:8080/bookings] : " +
                        "Запрос на добавление бронирования от пользователя id : {} - {}",
                userId, booking
        );
        return null;
    }

    /**
     * Обновление существующей записи
     * бронирования пользователя
     **/
    @PatchMapping
    public BookingDto update(
            @RequestHeader("X-Later-User-Id") long userId,
            @RequestBody BookingDto booking
    ) {
        log.info(
                " PATCH [http://localhost:8080/bookings] : " +
                        "Запрос на обновление бронирования от пользователя id : {} - {}",
                userId, booking
        );
        return null;
    }

    /**
     * Удаление существующей записи
     * бронирования пользователя
     **/
    @DeleteMapping("/{bookingId}")
    public void delete(
            @RequestHeader("X-Later-User-Id") long userId,
            @RequestParam long bookingId
    ) {
        log.info(
                " DELETE [http://localhost:8080/bookings/{}] : " +
                        "Запрос на удаление бронирования id : {} от пользователя id : {}",
                bookingId, bookingId, userId
        );
    }
}
