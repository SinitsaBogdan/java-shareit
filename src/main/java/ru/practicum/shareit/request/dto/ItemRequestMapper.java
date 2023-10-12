package ru.practicum.shareit.request.dto;

import org.jetbrains.annotations.NotNull;
import ru.practicum.shareit.item.dto.ItemMapper;
import ru.practicum.shareit.request.model.ItemRequest;

import java.util.stream.Collectors;

public class ItemRequestMapper {

    public static ItemRequestDto mapperItemRequestToDto(@NotNull ItemRequest itemRequest) {
        return ItemRequestDto.builder()
                .id(itemRequest.getId())
                .description(itemRequest.getDescription())
                .created(itemRequest.getCreated())
                .items(itemRequest.getItems().stream().map(ItemMapper::mapperItemToDto).collect(Collectors.toList()))
                .build();
    }

    public static ItemRequest mapperItemRequestDtoToItemRequest(@NotNull ItemRequestDto itemRequestDto) {
        return ItemRequest.builder().description(itemRequestDto.getDescription()).build();
    }
}