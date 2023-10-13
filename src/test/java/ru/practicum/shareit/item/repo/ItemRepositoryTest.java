package ru.practicum.shareit.item.repo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repo.UserRepository;

import java.util.List;


@DataJpaTest
@DisplayName("Интеграционное тестирование - ItemRepository")
class ItemRepositoryTest {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("CONFIG")
    public void contextLoads() {
        Assertions.assertNotNull(itemRepository);
    }

    @Nested
    @DisplayName("ENTITY")
    public class Entity {

        @Test
        @DisplayName("Проверка валидации поля name - nullable")
        public void save__Fail_Name_nullable() {
            Item item = Item.builder()
                    .description("description")
                    .available(true)
                    .build();

            Assertions.assertThrows(DataIntegrityViolationException.class, () -> itemRepository.save(item));
        }

        @Test
        @DisplayName("Проверка валидации поля description - nullable")
        public void save__Fail_Description_nullable() {
            Item item = Item.builder()
                    .name("item")
                    .available(true)
                    .build();

            Assertions.assertThrows(DataIntegrityViolationException.class, () -> itemRepository.save(item));
        }

        @Test
        @DisplayName("Проверка валидации поля available - nullable")
        public void save__Fail_Available_nullable() {
            Item item = Item.builder()
                    .name("item")
                    .description("description")
                    .build();

            Assertions.assertThrows(DataIntegrityViolationException.class, () -> itemRepository.save(item));
        }
    }

    @Nested
    @DisplayName("REPOSITORY")
    public class Repository {

        @Test
        @DisplayName("Успешное сохранение объекта в таблицу ITEMS")
        public void save() {
            Item item = Item.builder()
                    .name("item")
                    .description("description")
                    .available(true)
                    .build();

            Assertions.assertNull(item.getId());
            itemRepository.save(item);
            Assertions.assertNotNull(item.getId());
        }

        @Test
        @DisplayName("Проверка метода - findByUser_id(long userId)")
        public void findByUser_id() {

            User user = User.builder()
                    .name("user-1")
                    .email("mail@mail.ru")
                    .build();

            Item item = Item.builder()
                    .name("item")
                    .description("description")
                    .user(user)
                    .available(true)
                    .build();

            userRepository.save(user);
            itemRepository.save(item);

            List<Item> result = itemRepository.findByUser_id(user.getId());
            Assertions.assertEquals(result.size(), 1);
        }

        @Test
        @DisplayName("Проверка метода - findByUser_id(long userId)")
        public void findSearch() {

            User user = User.builder()
                    .name("user-1")
                    .email("mail@mail.ru")
                    .build();

            Item item = Item.builder()
                    .name("item")
                    .description("description")
                    .user(user)
                    .available(true)
                    .build();

            userRepository.save(user);
            itemRepository.save(item);

            List<Item> result = itemRepository.findSearch("item");
            Assertions.assertEquals(result.size(), 1);
        }
    }
}