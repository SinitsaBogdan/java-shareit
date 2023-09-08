package ru.practicum.shareit.request.repo;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.request.model.ItemRequest;

import java.util.List;

@Repository
public class ItemRequestRepositoryImpl implements ItemRequestRepository {

    @Override
    public Boolean checkId(Long id) {
        return null;
    }

    @Override
    public List<ItemRequest> findAll() {
        return null;
    }

    @Override
    public ItemRequest findById(Long itemRequestId) {
        return null;
    }

    @Override
    public ItemRequest save(ItemRequest user) {
        return null;
    }

    @Override
    public ItemRequest update(ItemRequest user) {
        return null;
    }

    @Override
    public void deleteAll() {

    }

    @Override
    public void deleteById(Long itemRequestId) {

    }
}
