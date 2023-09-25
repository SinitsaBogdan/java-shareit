package ru.practicum.shareit.booking.service;

import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingResponseBookerDto;
import ru.practicum.shareit.booking.dto.BookingResponseOwnerDto;
import ru.practicum.shareit.util.EnumBookingState;

import java.util.List;

public interface BookingService {

    BookingResponseOwnerDto getById(Long userId, Long bookingId);

    List<BookingResponseOwnerDto> getAll(Long userId, String state);

    List<BookingResponseOwnerDto> getAllInItemOwner(Long userId, String state);

    BookingResponseOwnerDto add(Long userId, BookingDto bookingDto);

    BookingResponseBookerDto updateApproved(Long userId, Long bookingId, Boolean approved);
}
