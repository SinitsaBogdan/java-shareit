package ru.practicum.shareit.item.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    Page<Item> findByUser(User user, Pageable pageable);

    @Query("select i from Item i where i.available <> false and (lower(i.name) LIKE lower(concat('%',:text,'%')) or lower(i.description) LIKE lower(concat('%',:text,'%')))")
    Page<Item> findSearch(@Param("text") String text, Pageable pageable);
}
