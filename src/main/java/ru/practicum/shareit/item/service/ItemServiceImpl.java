package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.ItemDto;

import java.util.List;

public class ItemServiceImpl implements ItemService {

    @Override
    public List<ItemDto> getAll() {
        return null;
    }

    @Override
    public List<ItemDto> getAllByUser(long userId) {
        return null;
    }

    @Override
    public List<ItemDto> getBySearchText(String text) {
        return null;
    }

    @Override
    public ItemDto getById(long itemId) {
        return null;
    }

    @Override
    public ItemDto addToUser(long userId, ItemDto item) {
        return null;
    }

    @Override
    public ItemDto updateToUser(long userId, ItemDto item) {
        return null;
    }

    @Override
    public void deleteToUser(long userId, long itemId) {

    }
}
