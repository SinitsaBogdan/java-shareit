package ru.practicum.shareit.booking.dto;

import org.jetbrains.annotations.NotNull;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.dto.UserDto;

public class BookingMapper {

    public static BookingResponseShortDto mapperBookingToShortDto(@NotNull Booking booking) {
        return BookingResponseShortDto.builder()
                .id(booking.getId())
                .bookerId(booking.getOwner().getId())
                .build();
    }

    public static Booking mapperBookingDtoToBooking(@NotNull BookingRequestDto booking) {
        return Booking.builder()
                .start(booking.getStart())
                .end(booking.getEnd())
                .build();
    }

    public static BookingResponseDto mapperBookingResponseBookerToDto(@NotNull Booking booking) {

        ItemDto item = ItemDto.builder()
                .id(booking.getItem().getId())
                .name(booking.getItem().getName()).build();

        UserDto user = UserDto.builder().id(booking.getOwner().getId()).build();

        return BookingResponseDto.builder()
                .id(booking.getId())
                .item(item)
                .booker(user)
                .start(booking.getStart())
                .end(booking.getEnd())
                .status(booking.getApproved().name())
                .build();
    }
}
