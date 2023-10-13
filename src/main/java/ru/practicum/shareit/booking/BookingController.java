package ru.practicum.shareit.booking;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingRequestDto;
import ru.practicum.shareit.booking.dto.BookingResponseDto;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.util.exeptions.BusinessException;

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

    private final BookingService bookingService;

    /**
     * Запрос всех записей бронирования пользователя
     **/
    @GetMapping
    public List<BookingResponseDto> getAll(
            @RequestHeader(value = "X-Sharer-User-Id") long userId,
            @RequestParam(defaultValue = "ALL") String state,
            @RequestParam(defaultValue = "0") int from,
            @RequestParam(defaultValue = "10") int size
    ) {
        log.info("   GET [http://localhost:8080/bookings?state={}] : Запрос на получение всех бронирований от пользователя {}", state, userId);
        if (from < 0 || size < 1) throw new BusinessException("Некорректные параметры поиска", 400);
        return bookingService.getAll(userId, state, PageRequest.of(from > 0 ? from / size : 0, size));
    }

    /**
     * Запрос записи бронирования по ID
     **/
    @GetMapping("/{bookingId}")
    public BookingResponseDto getById(
            @RequestHeader(value = "X-Sharer-User-Id") long userId,
            @PathVariable long bookingId
    ) {
        log.info("   GET [http://localhost:8080/bookings/{}] : Запрос на получение бронирования по id : {}", bookingId, bookingId);
        return bookingService.getById(userId, bookingId);
    }

    /**
     * Получение списка бронирований для всех вещей текущего пользователя
     **/
    @GetMapping("/owner")
    public List<BookingResponseDto> getById(
            @RequestHeader(value = "X-Sharer-User-Id") long userId,
            @RequestParam(defaultValue = "ALL") String state,
            @RequestParam(defaultValue = "0") int from,
            @RequestParam(defaultValue = "10") int size
    ) {
        log.info("   GET [http://localhost:8080/bookings/owner?state={}] : Запрос на получение всех бронирований пользователя", state);
        if (from < 0 || size < 1) throw new BusinessException("Некорректные параметры поиска", 400);
        return bookingService.getAllInItemOwner(userId, state, PageRequest.of(from > 0 ? from / size : 0, size));
    }

    /**
     * Добавление новой записи бронирования пользователя
     **/
    @PostMapping
    public BookingResponseDto save(@RequestHeader(value = "X-Sharer-User-Id") long userId, @RequestBody @Valid BookingRequestDto bookingRequestDto) {
        System.out.println(bookingRequestDto);
        log.info("  POST [http://localhost:8080/bookings] : Запрос на добавление бронирования - {}", bookingRequestDto);
        return bookingService.save(userId, bookingRequestDto);
    }

    /**
     * Подтверждение бронирования
     **/
    @PatchMapping("/{bookingId}")
    public BookingResponseDto approved(@RequestHeader(value = "X-Sharer-User-Id") long userId, @PathVariable long bookingId, @RequestParam(defaultValue = "") boolean approved) {
        log.info("  POST [http://localhost:8080/bookings/{}?approved={}] : Запрос на добавление бронирования - {}", bookingId, approved, bookingId);
        return bookingService.updateApproved(userId, bookingId, approved);
    }
}