package ru.practicum.shareit.item.dto;

import org.jetbrains.annotations.NotNull;
import ru.practicum.shareit.item.model.Item;

public class ItemMapper {

    public static ItemDto mapperItemToDto(@NotNull Item item) {
        return ItemDto.builder()
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .build();
    }

    public static Item mapperItemDtoToItem(ItemDto itemDto) {
        return Item.builder()
                .name(itemDto.getName())
                .description(itemDto.getDescription())
                .available(itemDto.getAvailable())
                .build();
    }
}
