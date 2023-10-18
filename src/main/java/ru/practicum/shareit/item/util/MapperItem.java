package ru.practicum.shareit.item.util;

import org.jetbrains.annotations.NotNull;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.util.exeptions.CustomException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class MapperItem {

    private MapperItem() {
        throw new CustomException("This is a utility class and cannot be instantiated", 500);
    }

    public static ItemDto mapperEntityToDto(@NotNull Item item) {
        List<CommentDto> list = new ArrayList<>();

        if (item.getComments() != null) {
            list = item.getComments().stream()
                    .map(MapperComment::mapperEntityToDto)
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

    public static Item mapperDtoToEntity(@NotNull ItemDto itemDto) {
        return Item.builder()
                .id(itemDto.getId())
                .name(itemDto.getName())
                .description(itemDto.getDescription())
                .available(itemDto.getAvailable())
                .build();
    }
}
