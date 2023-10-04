package ru.practicum.shareit.booking.service;

import ru.practicum.shareit.booking.dto.BookingRequestDto;
import ru.practicum.shareit.booking.dto.BookingResponseDto;

import java.util.List;

public interface BookingService {

    BookingResponseDto getById(Long userId, Long bookingId);

    List<BookingResponseDto> getAll(Long userId, String state);

    List<BookingResponseDto> getAllInItemOwner(Long userId, String state);

    BookingResponseDto add(Long userId, BookingRequestDto bookingRequestDto);

    BookingResponseDto updateApproved(Long userId, Long bookingId, Boolean approved);
}
