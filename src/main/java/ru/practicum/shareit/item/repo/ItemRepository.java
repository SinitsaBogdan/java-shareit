package ru.practicum.shareit.item.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findByUser_id(long userId);

    @Query("select i from Item i where i.available <> false and (lower(i.name) LIKE lower(concat('%',:text,'%')) or lower(i.description) LIKE lower(concat('%',:text,'%')))")
    List<Item> findSearch(@Param("text") String text);
}
