package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.ItemDto;

import java.util.List;

public interface ItemService {

    List<ItemDto> getAll();

    List<ItemDto> getAllByUserId(Long userId);

    List<ItemDto> getBySearchText(String text);

    ItemDto getById(Long itemId);

    ItemDto addToUser(Long userId, ItemDto item);

    ItemDto updateToUser(Long userId, ItemDto item);

    void deleteToUser(Long userId, Long itemId);
}
