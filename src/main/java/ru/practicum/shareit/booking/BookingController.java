package ru.practicum.shareit.booking;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingRequestDto;
import ru.practicum.shareit.booking.dto.BookingResponseDto;
import ru.practicum.shareit.booking.service.BookingService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Slf4j
@Validated
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
            @RequestParam(defaultValue = "0") @PositiveOrZero int from,
            @RequestParam(defaultValue = "10") @Min(1) int size
    ) {
        log.info("   GET [http://localhost:8080/bookings?state={}] : Запрос на получение всех бронирований от пользователя {}", state, userId);
        return bookingService.getAll(userId, state, PageRequest.of(from > 0 ? from / size : 0, size, Sort.by("id")));
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
            @RequestParam(defaultValue = "0") @PositiveOrZero int from,
            @RequestParam(defaultValue = "10") @Min(1) int size
    ) {
        log.info("   GET [http://localhost:8080/bookings/owner?state={}] : Запрос на получение всех бронирований пользователя", state);
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