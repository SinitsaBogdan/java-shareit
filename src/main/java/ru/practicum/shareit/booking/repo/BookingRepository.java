package ru.practicum.shareit.booking.repo;

import ru.practicum.shareit.booking.model.Booking;

import java.util.List;

public interface BookingRepository {

    Boolean checkId(Long id);

    List<Booking> findAll();

    Booking findById(Long bookingId);

    Booking save(Booking booking);

    Booking update(Booking booking);

    void deleteAll();

    void deleteById(Long bookingId);
}
