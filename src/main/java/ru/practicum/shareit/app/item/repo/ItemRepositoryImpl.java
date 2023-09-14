package ru.practicum.shareit.app.item.repo;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.app.item.model.Item;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class ItemRepositoryImpl implements ItemRepository {

    private static Long id = 1L;

    private static final Map<Long, Item> data = new HashMap<>();
    private static final Map<Long, List<Long>> dataInIndexItemToUser = new HashMap<>();

    @Override
    public Boolean checkId(Long id) {
        return data.containsKey(id);
    }

    @Override
    public Collection<Item> findAll() {
        return data.values();
    }

    @Override
    public Collection<Item> findAllIsText(String text) {
        return data.values().stream()
                .filter(Item::getAvailable)
                .filter(item -> item.getName().toLowerCase().contains(text) || item.getDescription().toLowerCase().contains(text))
                .collect(Collectors.toList());
    }


    @Override
    public List<Item> findAllIsUser(Long userId) {
        return dataInIndexItemToUser.get(userId).stream()
                .map(data::get)
                .collect(Collectors.toList());
    }

    @Override
    public Item findById(Long itemId) {
        return data.get(itemId);
    }

    @Override
    public Item save(Item item) {
        item.setId(id++);
        data.put(item.getId(), item);
        if (dataInIndexItemToUser.containsKey(item.getOwner())) dataInIndexItemToUser.get(item.getOwner()).add(item.getId());
        else dataInIndexItemToUser.put(item.getOwner(), List.of(item.getId()));
        return item;
    }

    @Override
    public Item update(Item item) {
        data.put(item.getId(), item);
        return item;
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
