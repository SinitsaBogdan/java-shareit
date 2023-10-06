package ru.practicum.shareit.booking.service;

import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.dto.BookingRequestDto;
import ru.practicum.shareit.booking.dto.BookingResponseDto;

import java.util.List;

@Transactional(readOnly = true)
public interface BookingService {

    BookingResponseDto getById(long userId, long bookingId);

    List<BookingResponseDto> getAll(long userId, String state);

    List<BookingResponseDto> getAllInItemOwner(long userId, String state);

    @Transactional
    BookingResponseDto add(long userId, BookingRequestDto bookingRequestDto);

    @Transactional
    BookingResponseDto updateApproved(long userId, long bookingId, boolean approved);
}
