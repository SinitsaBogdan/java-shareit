package ru.practicum.shareit.request.util;

import org.jetbrains.annotations.NotNull;
import ru.practicum.shareit.item.util.MapperItem;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.util.exeptions.CustomException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class MapperRequest {

    private MapperRequest() {
        throw new CustomException("This is a utility class and cannot be instantiated", 500);
    }

    public static ItemRequestDto mapperEntityToDto(@NotNull ItemRequest itemRequest) {
        List<ItemDto> list = itemRequest.getItems().stream().map(MapperItem::mapperEntityToDto).collect(Collectors.toList());
        return ItemRequestDto.builder()
                .id(itemRequest.getId())
                .description(itemRequest.getDescription())
                .created(itemRequest.getCreated())
                .items(list.isEmpty() ? new ArrayList<>() : list)
                .build();
    }

    public static ItemRequest mapperDtoToEntity(@NotNull ItemRequestDto itemRequestDto) {
        return ItemRequest.builder().description(itemRequestDto.getDescription()).build();
    }
}
