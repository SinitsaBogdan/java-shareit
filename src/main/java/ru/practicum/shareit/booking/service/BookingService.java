package ru.practicum.shareit.booking.service;

import ru.practicum.shareit.booking.dto.BookingRequestDto;
import ru.practicum.shareit.booking.dto.BookingResponseDto;

import java.util.List;

public interface BookingService {

    BookingResponseDto getById(long userId, long bookingId);

    List<BookingResponseDto> getAll(long userId, String state);

    List<BookingResponseDto> getAllInItemOwner(long userId, String state);

    BookingResponseDto add(long userId, BookingRequestDto bookingRequestDto);

    BookingResponseDto updateApproved(long userId, long bookingId, boolean approved);
}
