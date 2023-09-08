package ru.practicum.shareit.request.service;

import ru.practicum.shareit.request.dto.ItemRequestDto;

import java.util.List;

public interface ItemRequestService {

    List<ItemRequestDto> getAll();

    ItemRequestDto add(long userId, ItemRequestDto itemRequest);

    ItemRequestDto update(long userId, ItemRequestDto itemRequest);

    void deleteToUser(long userId, long itemRequestId);
}
