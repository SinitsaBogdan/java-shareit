package ru.practicum.shareit.booking.dto;

import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

public class BookingMapper {

    public static BookingDto mapperBookingToDto(Booking booking) {
        return new BookingDto();
    }

    public static Booking mapperBookingDtoToBooking(BookingDto bookingDto) {
        return new Booking();
    }
}
