package ru.practicum.shareit.app.item.repo;

import ru.practicum.shareit.app.item.model.Item;

import java.util.Collection;
import java.util.List;

public interface ItemRepository {

    Boolean checkId(Long id);

    Collection<Item> findAll();

    Collection<Item> findAllIsText(String text);

    List<Item> findAllIsUser(Long userId);

    Item findById(Long itemId);

    Item save(Item item);

    Item update(Item item);

    void deleteAll();

    void deleteById(Long itemId);
}
