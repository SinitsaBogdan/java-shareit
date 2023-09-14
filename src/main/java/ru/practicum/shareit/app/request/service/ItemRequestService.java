package ru.practicum.shareit.app.request.service;

import ru.practicum.shareit.app.request.dto.ItemRequestDto;

import java.util.List;

public interface ItemRequestService {

    List<ItemRequestDto> getAll();

    ItemRequestDto add(Long userId, ItemRequestDto itemRequest);

    ItemRequestDto update(Long userId, ItemRequestDto itemRequest);

    void deleteToUser(Long userId, Long itemRequestId);
}
