package ru.practicum.shareit.app.booking.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.app.booking.dto.BookingDto;
import ru.practicum.shareit.app.booking.repo.BookingRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class BookingServiceImpl implements BookingService {

    private BookingRepository bookingRepository;

    @Override
    public List<BookingDto> getAll() {
        return null;
    }

    @Override
    public List<BookingDto> getAllByUser(Long userId) {
        return null;
    }

    @Override
    public BookingDto getById(Long bookingId) {
        return null;
    }

    @Override
    public BookingDto addToUser(Long userId, BookingDto booking) {
        return null;
    }

    @Override
    public BookingDto updateToUser(Long userId, BookingDto booking) {
        return null;
    }

    @Override
    public void deleteToUser(Long userId, Long bookingId) {

    }
}
