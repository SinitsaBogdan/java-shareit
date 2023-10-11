package ru.practicum.shareit.request.dto;

import org.jetbrains.annotations.NotNull;
import ru.practicum.shareit.request.model.ItemRequest;

public class ItemRequestMapper {

    public static @NotNull ItemRequestDto mapperItemRequestToDto(ItemRequest itemRequest) {
        return new ItemRequestDto();
    }

    public static @NotNull ItemRequest mapperItemRequestDtoToItemRequest(ItemRequestDto itemRequestDto) {
        return new ItemRequest();
    }
}