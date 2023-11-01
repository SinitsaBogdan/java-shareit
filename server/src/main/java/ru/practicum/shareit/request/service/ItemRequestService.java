package ru.practicum.shareit.request.service;

import org.springframework.data.domain.Pageable;
import ru.practicum.shareit.request.dto.ItemRequestDto;

import java.util.List;

public interface ItemRequestService {

    List<ItemRequestDto> findAll(Long userId);

    List<ItemRequestDto> findAll(long userId, Pageable pageable);

    ItemRequestDto findOne(long userId, long requestId);

    ItemRequestDto save(long userId, ItemRequestDto requestDto);
}
