package ru.practicum.shareit.booking;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingResponseBookerDto;
import ru.practicum.shareit.booking.dto.BookingResponseOwnerDto;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.util.EnumBookingState;
import ru.practicum.shareit.util.exeptions.RepositoryException;

import javax.validation.Valid;
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
     * Запрос всех записей бронирования пользователя
     **/
    @GetMapping
    public List<BookingResponseOwnerDto> getAll(@RequestHeader(value = "X-Sharer-User-Id") Long userId, @RequestParam(defaultValue = "ALL") String state) {
        log.info("   GET [http://localhost:8080/bookings?state={}] : Запрос на получение всех бронирований от пользователя {}", state, userId);
        return bookingService.getAll(userId, state);
    }

    /**
     * Запрос записи бронирования по ID
     **/
    @GetMapping("/{bookingId}")
    public BookingResponseOwnerDto getById(@RequestHeader(value = "X-Sharer-User-Id") Long userId, @PathVariable Long bookingId) {
        log.info("   GET [http://localhost:8080/bookings/{}] : Запрос на получение бронирования по id : {}", bookingId, bookingId);
        return bookingService.getById(userId, bookingId);
    }

    /**
     * Получение списка бронирований для всех вещей текущего пользователя
     **/
    @GetMapping("/owner")
    public List<BookingResponseOwnerDto> getById(@RequestHeader(value = "X-Sharer-User-Id") Long userId, @RequestParam(defaultValue = "ALL") String state) {
        log.info("   GET [http://localhost:8080/bookings/owner?state={}] : Запрос на получение всех бронирований пользователя", state);
        return bookingService.getAllInItemOwner(userId, state);
    }

    /**
     * Добавление новой записи бронирования пользователя
     **/
    @PostMapping
    public BookingResponseOwnerDto add(@RequestHeader(value = "X-Sharer-User-Id") Long userId, @RequestBody @Valid BookingDto bookingDto) {
        System.out.println(bookingDto);
        log.info("  POST [http://localhost:8080/bookings] : Запрос на добавление бронирования - {}", bookingDto);
        return bookingService.add(userId, bookingDto);
    }

    /**
     * Подтверждение бронирования
     **/
    @PatchMapping("/{bookingId}")
    public BookingResponseBookerDto add(@RequestHeader(value = "X-Sharer-User-Id") Long userId, @PathVariable Long bookingId, @RequestParam(defaultValue = "") Boolean approved) {
        log.info("  POST [http://localhost:8080/bookings/{}?approved={}] : Запрос на добавление бронирования - {}", bookingId, approved, bookingId);
        return bookingService.updateApproved(userId, bookingId, approved);
    }
}