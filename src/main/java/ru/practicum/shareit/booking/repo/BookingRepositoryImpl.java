package ru.practicum.shareit.booking.repo;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.booking.model.Booking;

import java.util.List;

@Repository
public class BookingRepositoryImpl implements BookingRepository {

    @Override
    public List<Booking> findAll() {
        return null;
    }

    @Override
    public Booking findById(Long bookingId) {
        return null;
    }

    @Override
    public Booking save(Booking booking) {
        return null;
    }

    @Override
    public Booking update(Booking booking) {
        return null;
    }

    @Override
    public void deleteAll() {

    }

    @Override
    public void deleteById(Long bookingId) {

    }
}
