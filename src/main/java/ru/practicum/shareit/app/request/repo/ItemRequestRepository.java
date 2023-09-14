package ru.practicum.shareit.app.request.repo;


import ru.practicum.shareit.app.request.model.ItemRequest;

import java.util.List;

public interface ItemRequestRepository {

    Boolean checkId(Long id);

    List<ItemRequest> findAll();

    ItemRequest findById(Long itemRequestId);

    ItemRequest save(ItemRequest user);

    ItemRequest update(ItemRequest user);

    void deleteAll();

    void deleteById(Long itemRequestId);
}
