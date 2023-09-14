package ru.practicum.shareit.booking.dto;

import ru.practicum.shareit.booking.model.Booking;

public class BookingMapper {

    public static BookingDto mapperBookingToDto(Booking booking) {
        return new BookingDto();
    }

    public static Booking mapperBookingDtoToBooking(BookingDto bookingDto) {
        return new Booking();
    }
}
