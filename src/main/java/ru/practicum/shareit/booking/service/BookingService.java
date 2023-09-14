package ru.practicum.shareit.booking.service;

import ru.practicum.shareit.booking.dto.BookingDto;

import java.util.List;

public interface BookingService {

    List<BookingDto> getAll();

    List<BookingDto> getAllByUser(Long userId);

    BookingDto getById(Long bookingId);

    BookingDto addToUser(Long userId, BookingDto booking);

    BookingDto updateToUser(Long userId, BookingDto booking);

    void deleteToUser(Long userId, Long bookingId);
}
