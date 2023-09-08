package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.ItemDto;

import java.util.List;

public interface ItemService {

    List<ItemDto> getAll();

    List<ItemDto> getAllByUser(long userId);

    List<ItemDto> getBySearchText(String text);

    ItemDto getById(long itemId);

    ItemDto addToUser(long userId, ItemDto item);

    ItemDto updateToUser(long userId, ItemDto item);

    void deleteToUser(long userId, long itemId);
}
