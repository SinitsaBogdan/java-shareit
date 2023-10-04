package ru.practicum.shareit.booking;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingRequestDto;
import ru.practicum.shareit.booking.dto.BookingResponseDto;
import ru.practicum.shareit.booking.service.BookingService;

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
    public List<BookingResponseDto> getAll(@RequestHeader(value = "X-Sharer-User-Id") Long userId, @RequestParam(defaultValue = "ALL") String state) {
        log.info("   GET [http://localhost:8080/bookings?state={}] : Запрос на получение всех бронирований от пользователя {}", state, userId);
        return bookingService.getAll(userId, state);
    }

    /**
     * Запрос записи бронирования по ID
     **/
    @GetMapping("/{bookingId}")
    public BookingResponseDto getById(@RequestHeader(value = "X-Sharer-User-Id") Long userId, @PathVariable Long bookingId) {
        log.info("   GET [http://localhost:8080/bookings/{}] : Запрос на получение бронирования по id : {}", bookingId, bookingId);
        return bookingService.getById(userId, bookingId);
    }

    /**
     * Получение списка бронирований для всех вещей текущего пользователя
     **/
    @GetMapping("/owner")
    public List<BookingResponseDto> getById(@RequestHeader(value = "X-Sharer-User-Id") Long userId, @RequestParam(defaultValue = "ALL") String state) {
        log.info("   GET [http://localhost:8080/bookings/owner?state={}] : Запрос на получение всех бронирований пользователя", state);
        return bookingService.getAllInItemOwner(userId, state);
    }

    /**
     * Добавление новой записи бронирования пользователя
     **/
    @PostMapping
    public BookingResponseDto add(@RequestHeader(value = "X-Sharer-User-Id") Long userId, @RequestBody @Valid BookingRequestDto bookingRequestDto) {
        System.out.println(bookingRequestDto);
        log.info("  POST [http://localhost:8080/bookings] : Запрос на добавление бронирования - {}", bookingRequestDto);
        return bookingService.add(userId, bookingRequestDto);
    }

    /**
     * Подтверждение бронирования
     **/
    @PatchMapping("/{bookingId}")
    public BookingResponseDto add(@RequestHeader(value = "X-Sharer-User-Id") Long userId, @PathVariable Long bookingId, @RequestParam(defaultValue = "") Boolean approved) {
        log.info("  POST [http://localhost:8080/bookings/{}?approved={}] : Запрос на добавление бронирования - {}", bookingId, approved, bookingId);
        return bookingService.updateApproved(userId, bookingId, approved);
    }
}