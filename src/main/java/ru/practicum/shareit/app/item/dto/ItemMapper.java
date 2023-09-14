package ru.practicum.shareit.app.item.dto;

import org.jetbrains.annotations.NotNull;
import ru.practicum.shareit.app.item.model.Item;

public class ItemMapper {

    public static ItemDto mapperItemToDto(@NotNull Item item) {
        return ItemDto.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
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
