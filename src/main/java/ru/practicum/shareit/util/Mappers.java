package ru.practicum.shareit.util;

import org.jetbrains.annotations.NotNull;
import ru.practicum.shareit.booking.dto.BookingRequestDto;
import ru.practicum.shareit.booking.dto.BookingResponseDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Mappers {

    private Mappers() {
    }

    public static CommentDto mapperEntityToDto(@NotNull Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .authorName(comment.getUser().getName())
                .created(comment.getCreated())
                .text(comment.getText())
                .build();
    }

    public static ItemDto mapperEntityToDto(@NotNull Item item) {
        List<CommentDto> list = new ArrayList<>();

        if (item.getComments() != null) {
            list = item.getComments().stream()
                    .map(Mappers::mapperEntityToDto)
                    .collect(Collectors.toList());
        }

        return ItemDto.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .comments(list)
                .requestId(item.getRequest() != null ? item.getRequest().getId() : null)
                .build();
    }

    public static ItemRequestDto mapperEntityToDto(@NotNull ItemRequest itemRequest) {
        List<ItemDto> list = itemRequest.getItems().stream().map(Mappers::mapperEntityToDto).collect(Collectors.toList());
        return ItemRequestDto.builder()
                .id(itemRequest.getId())
                .description(itemRequest.getDescription())
                .created(itemRequest.getCreated())
                .items(list.isEmpty() ? new ArrayList<>() : list)
                .build();
    }

    public static UserDto mapperEntityToDto(@NotNull User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
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

    public static User mapperDtoToEntity(@NotNull UserDto userDto) {
        return User.builder()
                .id(userDto.getId())
                .name(userDto.getName())
                .email(userDto.getEmail())
                .build();
    }

    public static ItemRequest mapperDtoToEntity(@NotNull ItemRequestDto itemRequestDto) {
        return ItemRequest.builder().description(itemRequestDto.getDescription()).build();
    }

    public static Item mapperDtoToEntity(@NotNull ItemDto itemDto) {
        return Item.builder()
                .id(itemDto.getId())
                .name(itemDto.getName())
                .description(itemDto.getDescription())
                .available(itemDto.getAvailable())
                .build();
    }

    public static Comment mapperDtoToEntity(@NotNull CommentDto commentDto) {
        return Comment.builder()
                .text(commentDto.getText())
                .build();
    }


}