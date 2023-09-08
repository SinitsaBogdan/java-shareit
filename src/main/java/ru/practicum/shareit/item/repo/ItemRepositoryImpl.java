package ru.practicum.shareit.item.repo;

import ru.practicum.shareit.item.model.Item;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemRepositoryImpl implements ItemRepository {

    private static final Map<Long, Item> data = new HashMap<>();

    @Override
    public List<Item> findAll() {
        return (List<Item>) data.values();
    }

    @Override
    public Item findById(Long itemId) {
        return data.get(itemId);
    }

    @Override
    public Item save(Item item) {
        return data.put(item.getId(), item);
    }

    @Override
    public Item update(Item item) {
        return data.put(item.getId(), item);
    }

    @Override
    public void deleteAll() {
        data.clear();
    }

    @Override
    public void deleteById(Long itemId) {
        data.remove(itemId);
    }
}
