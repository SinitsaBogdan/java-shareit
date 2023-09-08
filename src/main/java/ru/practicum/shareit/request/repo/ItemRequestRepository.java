package ru.practicum.shareit.request.repo;


import ru.practicum.shareit.request.model.ItemRequest;

import java.util.List;

public interface ItemRequestRepository {

    List<ItemRequest> findAll();

    ItemRequest findById(Long itemRequestId);

    ItemRequest save(ItemRequest user);

    ItemRequest update(ItemRequest user);

    void deleteAll();

    void deleteById(Long itemRequestId);
}
