package ru.practicum.shareit.item.dto;

import org.jetbrains.annotations.NotNull;
import ru.practicum.shareit.item.model.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ItemMapper {

    public static ItemDto mapperItemToDto(@NotNull Item item) {
        List<CommentDto> list = new ArrayList<>();

        if (item.getComments() != null) {
            list = item.getComments().stream()
                    .map(CommentMapper::mapperCommentToDto)
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

    public static Item mapperItemDtoToItem(ItemDto itemDto) {
        return Item.builder()
                .id(itemDto.getId())
                .name(itemDto.getName())
                .description(itemDto.getDescription())
                .available(itemDto.getAvailable())
                .build();
    }
}
