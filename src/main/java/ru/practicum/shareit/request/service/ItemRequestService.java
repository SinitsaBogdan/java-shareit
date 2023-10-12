package ru.practicum.shareit.request.service;

import ru.practicum.shareit.request.dto.ItemRequestDto;

import java.util.List;

public interface ItemRequestService {

    List<ItemRequestDto> findAll(Long userId);

    List<ItemRequestDto> findAll(long userId, int from, int size);

    ItemRequestDto findOne(long userId, long requestId);

    ItemRequestDto add(long userId, ItemRequestDto requestDto);
}
