package ru.practicum.shareit.app.request.dto;

import ru.practicum.shareit.app.request.model.ItemRequest;

public class ItemRequestMapper {

    public static ItemRequestDto mapperItemRequestToDto(ItemRequest itemRequest) {
        return new ItemRequestDto();
    }

    public static ItemRequest mapperItemRequestDtoToItemRequest(ItemRequestDto itemRequestDto) {
        return new ItemRequest();
    }
}
