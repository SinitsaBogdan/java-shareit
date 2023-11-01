package ru.practicum.shareit.item.repo;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    private final User user = User.builder()
            .name("user-1")
            .email("mail@mail.ru")
            .build();

    private final Item item = Item.builder()
            .name("item")
            .description("description")
            .available(true)
            .user(user)
            .build();

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

        @BeforeEach
        public void beforeEach() {
            userRepository.save(user);
            Assertions.assertNull(item.getId());
            itemRepository.save(item);
        }

        @Test
        @DisplayName("Успешное сохранение объекта в таблицу ITEMS")
        public void save() {
            Assertions.assertNotNull(item.getId());
        }

        @Test
        @DisplayName("Проверка метода - findByUser")
        public void findByUser() {
            List<Item> result = itemRepository.findByUser(user, PageRequest.of(0, 4)).toList();
            Assertions.assertEquals(result.size(), 1);
        }

        @Test
        @DisplayName("Проверка метода - findSearch")
        public void findSearch() {
            Page<Item> result = itemRepository.findSearch("item", PageRequest.of(0, 3));
            Assertions.assertEquals(result.stream().toArray().length, 1);
        }
    }
}