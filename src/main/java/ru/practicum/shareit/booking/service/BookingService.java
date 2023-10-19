package ru.practicum.shareit.booking.service;

import org.springframework.data.domain.Pageable;
import ru.practicum.shareit.booking.dto.BookingRequestDto;
import ru.practicum.shareit.booking.dto.BookingResponseDto;

import java.util.List;

public interface BookingService {

    BookingResponseDto getById(long userId, long bookingId);

    List<BookingResponseDto> getAll(long userId, String state, Pageable pageable);

    List<BookingResponseDto> getAllInItemOwner(long userId, String state, Pageable pageable);

    BookingResponseDto save(long userId, BookingRequestDto bookingRequestDto);

    BookingResponseDto updateApproved(long userId, long bookingId, boolean approved);
}
