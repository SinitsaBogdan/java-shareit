package ru.practicum.shareit.booking.dto;

import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.dto.UserDto;

public class BookingMapper {

    public static BookingShortDto mapperBookingToShortDto(Booking booking) {
        return BookingShortDto.builder()
                .id(booking.getId())
                .bookerId(booking.getOwner().getId())
                .build();
    }

    public static Booking mapperBookingDtoToBooking(BookingDto booking) {
        return Booking.builder()
                .start(booking.getStart())
                .end(booking.getEnd())
                .build();
    }

    public static BookingResponseOwnerDto mapperBookingResponseOwnerToDto(Booking booking) {

        ItemDto item = ItemDto.builder()
                .id(booking.getItem().getId())
                .name(booking.getItem().getName()).build();

        UserDto user = UserDto.builder().id(booking.getOwner().getId()).build();

        return BookingResponseOwnerDto.builder()
                .id(booking.getId())
                .item(item)
                .booker(user)
                .start(booking.getStart())
                .end(booking.getEnd())
                .status(booking.getApproved().name())
                .build();
    }

    public static BookingResponseBookerDto mapperBookingResponseBookerToDto(Booking booking) {

        ItemDto item = ItemDto.builder()
                .id(booking.getItem().getId())
                .name(booking.getItem().getName()).build();

        UserDto user = UserDto.builder().id(booking.getOwner().getId()).build();

        return BookingResponseBookerDto.builder()
                .id(booking.getId())
                .item(item)
                .booker(user)
                .start(booking.getStart())
                .end(booking.getEnd())
                .status(booking.getApproved().name())
                .build();
    }
}
