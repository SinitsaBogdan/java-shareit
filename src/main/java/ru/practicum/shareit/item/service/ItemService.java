package ru.practicum.shareit.item.service;

import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;

import java.util.List;

@Transactional(readOnly = true)
public interface ItemService {

    List<ItemDto> getAllByUserId(long userId);

    List<ItemDto> getBySearchText(String text);

    ItemDto getById(long userId, long itemId);

    @Transactional
    ItemDto add(long userId, ItemDto item);

    @Transactional
    CommentDto addComment(long userId, long itemId, CommentDto commentDto);

    @Transactional
    ItemDto update(long userId, ItemDto item);
}
