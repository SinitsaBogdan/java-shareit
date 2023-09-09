package ru.practicum.shareit.item.repo;

import ru.practicum.shareit.item.model.Item;

import java.util.Collection;

public interface ItemRepository {

    Boolean checkId(Long id);

    Collection<Item> findAll();

    Collection<Item> findAllByUser(Long userId);

    Item findById(Long itemId);

    Item save(Item item);

    Item update(Item item);

    void deleteAll();

    void deleteById(Long itemId);
}
