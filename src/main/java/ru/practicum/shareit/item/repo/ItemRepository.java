package ru.practicum.shareit.item.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.item.model.Item;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {

    Optional<Item> findById(Long itemId);

    Collection<Item> findByOwner(Long userId);

    /* TODO переопределить запрос через Query*/
    Collection<Item> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseAndAvailable(String nameSearch, String descriptionSearch, Boolean available);
}
