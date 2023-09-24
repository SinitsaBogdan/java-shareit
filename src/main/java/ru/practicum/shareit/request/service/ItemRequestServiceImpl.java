package ru.practicum.shareit.request.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.repo.ItemRequestRepository;

import java.util.List;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class ItemRequestServiceImpl implements ItemRequestService {

    private ItemRequestRepository itemRequestRepository;

    @Override
    public List<ItemRequestDto> getAll() {
        return null;
    }

    @Override
    public ItemRequestDto add(Long userId, ItemRequestDto itemRequest) {
        return null;
    }

    @Override
    public ItemRequestDto update(Long userId, ItemRequestDto itemRequest) {
        return null;
    }

    @Override
    public void deleteToUser(Long userId, Long itemRequestId) {

    }
}
