package ru.practicum.shareit.item.repo;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.model.Item;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
public class ItemRepositoryImpl implements ItemRepository {

    private static Long id = 1L;

    private static final Map<Long, Item> data = new HashMap<>();

    @Override
    public Boolean checkId(Long id) {
        return data.containsKey(id);
    }

    @Override
    public Collection<Item> findAll() {
        return data.values();
    }

    @Override
    public Collection<Item> findAllByUser(Long userId) {
        return data.values();
    }

    @Override
    public Item findById(Long itemId) {
        return data.get(itemId);
    }

    @Override
    public Item save(Item item) {
        item.setId(id++);
        data.put(item.getId(), item);
        return item;
    }

    @Override
    public Item update(Item item) {

        Item update = data.get(item.getId());
        if (item.getName() != null && !item.getName().equals(update.getName())) update.setName(item.getName());
        if (item.getDescription() != null && !item.getDescription().equals(update.getDescription())) update.setDescription(item.getDescription());
        if (item.getAvailable() != null) update.setAvailable(item.getAvailable());

        data.put(update.getId(), update);
        return update;
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
