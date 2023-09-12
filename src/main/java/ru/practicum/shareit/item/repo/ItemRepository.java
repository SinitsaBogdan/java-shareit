package ru.practicum.shareit.item.repo;

import ru.practicum.shareit.item.model.Item;

import java.util.Collection;
import java.util.List;

public interface ItemRepository {

    Boolean checkId(Long id);

    Collection<Item> findAll();

    List<Item> findAllIsUser(Long userId);

    Item findById(Long itemId);

    Item save(Item item);

    Item update(Item item);

    void deleteAll();

    void deleteById(Long itemId);
}
