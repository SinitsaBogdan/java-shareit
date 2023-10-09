package ru.practicum.shareit.booking.dto;

import org.jetbrains.annotations.NotNull;
import ru.practicum.shareit.booking.model.Booking;

public class BookingMapper {

    public static Booking mapperBookingDtoToBooking(@NotNull BookingRequestDto booking) {
        return Booking.builder()
                .start(booking.getStart())
                .end(booking.getEnd())
                .build();
    }

    public static BookingResponseDto mapperBookingResponseBookerToDto(@NotNull Booking booking) {

        return BookingResponseDto.builder()
                .id(booking.getId())
                .item(new BookingResponseDto.LocalItem(booking.getItem().getId(), booking.getItem().getName()))
                .booker(new BookingResponseDto.LocalBooker(booking.getUser().getId()))
                .start(booking.getStart())
                .end(booking.getEnd())
                .status(booking.getApproved().name())
                .build();
    }
}
