package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;

import java.util.List;

public interface ItemService {

    List<ItemDto> getAllByUserId(long userId);

    List<ItemDto> getBySearchText(String text);

    ItemDto getById(long userId, long itemId);

    ItemDto saveItem(long userId, ItemDto item);

    CommentDto saveComment(long userId, long itemId, CommentDto commentDto);

    ItemDto updateItem(long userId, ItemDto item);
}
