package ru.practicum.shareit.item.dto;

import ru.practicum.shareit.item.model.Item;

public class ItemMapper {

    public static ItemDto mapperItemToDto(Item item) {
        return new ItemDto();
    }

    public static Item mapperItemDtoToItem(ItemDto itemDto) {
        return new Item();
    }
}
