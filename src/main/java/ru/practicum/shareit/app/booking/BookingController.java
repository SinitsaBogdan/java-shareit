package ru.practicum.shareit.app.booking;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.app.booking.dto.BookingDto;
import ru.practicum.shareit.app.booking.service.BookingService;

import java.util.List;

/**
 * TODO Sprint add-bookings.
 */

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/bookings")
public class BookingController {

    private BookingService bookingService;

    /**
     * Запрос всех записей
     * бронирования пользователя
     **/
    @GetMapping
    public List<BookingDto> getAll() {
        log.info("   GET [http://localhost:8080/bookings] : Запрос на получение всех бронирований");
        return null;
    }

    /**
     * Запрос записи бронирования по ID
     **/
    @GetMapping("/{bookingId}")
    public BookingDto getById(@PathVariable Long bookingId) {
        log.info("   GET [http://localhost:8080/bookings/{}] : Запрос на получение бронирования по id : {}", bookingId, bookingId);
        return null;
    }

    /**
     * Добавление новой записи
     * бронирования пользователя
     **/
    @PostMapping
    public BookingDto add(@RequestBody BookingDto booking) {
        log.info("  POST [http://localhost:8080/bookings] : Запрос на добавление бронирования - {}", booking);
        return null;
    }

    /**
     * Обновление существующей записи
     * бронирования пользователя
     **/
    @PatchMapping
    public BookingDto update(@RequestBody BookingDto booking) {
        log.info(" PATCH [http://localhost:8080/bookings] : Запрос на обновление бронирования - {}", booking);
        return null;
    }

    /**
     * Удаление существующей записи
     * бронирования пользователя
     **/
    @DeleteMapping("/{bookingId}")
    public void delete(@PathVariable Long bookingId) {
        log.info(" DELETE [http://localhost:8080/bookings/{}] : Запрос на удаление бронирования id : {}", bookingId, bookingId);
    }
}
