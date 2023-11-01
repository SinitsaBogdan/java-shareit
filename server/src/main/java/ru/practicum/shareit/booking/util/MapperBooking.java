package ru.practicum.shareit.booking.util;

import org.jetbrains.annotations.NotNull;
import ru.practicum.shareit.booking.dto.BookingRequestDto;
import ru.practicum.shareit.booking.dto.BookingResponseDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.util.exeptions.CustomException;

public final class MapperBooking {

    private MapperBooking() {
        throw new CustomException("This is a utility class and cannot be instantiated", 500);
    }

    public static BookingResponseDto mapperEntityToDto(@NotNull Booking booking) {

        return BookingResponseDto.builder()
                .id(booking.getId())
                .item(new BookingResponseDto.LocalItem(booking.getItem().getId(), booking.getItem().getName()))
                .booker(new BookingResponseDto.LocalBooker(booking.getUser().getId()))
                .start(booking.getStart())
                .end(booking.getEnd())
                .status(booking.getApproved().name())
                .build();
    }

    public static Booking mapperDtoToEntity(@NotNull BookingRequestDto bookingRequestDto) {
        return Booking.builder()
                .start(bookingRequestDto.getStart())
                .end(bookingRequestDto.getEnd())
                .build();
    }
}
