package ru.practicum.shareit.request.dto;

import org.jetbrains.annotations.NotNull;
import ru.practicum.shareit.request.model.ItemRequest;

public class ItemRequestMapper {

    public static @NotNull ItemRequestDto mapperItemRequestToDto(@NotNull ItemRequest itemRequest) {
        return ItemRequestDto.builder().description(itemRequest.getText()).build();
    }

    public static @NotNull ItemRequest mapperItemRequestDtoToItemRequest(@NotNull ItemRequestDto itemRequestDto) {
        return ItemRequest.builder().text(itemRequestDto.getDescription()).build();
    }
}