package ru.practicum.shareit.item.repo;

import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemRepository {

    List<Item> findAll();

    Item findById(Long itemId);

    Item save(Item item);

    Item update(Item item);

    void deleteAll();

    void deleteById(Long itemId);
}
