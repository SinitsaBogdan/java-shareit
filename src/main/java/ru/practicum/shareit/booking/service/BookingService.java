package ru.practicum.shareit.booking.service;

import ru.practicum.shareit.booking.dto.BookingDto;

import java.util.List;

public interface BookingService {

    List<BookingDto> getAll();

    List<BookingDto> getAllByUser(long userId);

    BookingDto getById(long bookingId);

    BookingDto addToUser(long userId, BookingDto booking);

    BookingDto updateToUser(long userId, BookingDto booking);

    void deleteToUser(long userId, long bookingId);
}
