package ru.practicum.shareit.request.repo;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repo.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@DataJpaTest
@DisplayName("Интеграционное тестирование - ItemRequestRepository")
class ItemRequestRepositoryTest {

    @Autowired
    private ItemRequestRepository requestRepository;

    @Autowired
    private UserRepository userRepository;

    private final User user_1 = User.builder()
            .name("user-1")
            .email("mail-1@mail.ru")
            .build();

    private final User user_2 = User.builder()
            .name("user-2")
            .email("mail-2@mail.ru")
            .build();

    private final ItemRequest request_1 = ItemRequest.builder()
            .description("description-1")
            .created(LocalDateTime.now())
            .user(user_1)
            .build();

    private final ItemRequest request_2 = ItemRequest.builder()
            .description("description-2")
            .created(LocalDateTime.now())
            .user(user_2)
            .build();

    @Test
    @DisplayName("CONFIG")
    public void contextLoads() {
        Assertions.assertNotNull(requestRepository);
    }

    @Nested
    @DisplayName("ENTITY")
    public class Entity {

        @Test
        @DisplayName("Проверка валидации поля description - nullable")
        public void save__Fail_Description_nullable() {
            ItemRequest request = ItemRequest.builder()
                    .created(LocalDateTime.now())
                    .user(user_1)
                    .build();

            Assertions.assertThrows(DataIntegrityViolationException.class, () -> requestRepository.save(request));
        }

        @Test
        @DisplayName("Проверка валидации поля description - nullable")
        public void save__Fail_Name_nullable() {
            ItemRequest request = ItemRequest.builder()
                    .description("description")
                    .user(user_1)
                    .build();

            Assertions.assertThrows(DataIntegrityViolationException.class, () -> requestRepository.save(request));
        }
    }

    @Nested
    @DisplayName("REPOSITORY")
    public class Repository {

        @BeforeEach
        public void beforeEach() {
            userRepository.save(user_1);
            userRepository.save(user_2);
            Assertions.assertNull(request_1.getId());
            Assertions.assertNull(request_2.getId());
            requestRepository.save(request_1);
            requestRepository.save(request_2);
        }

        @Test
        @DisplayName("Успешное сохранение объекта в таблицу REQUESTS")
        public void save() {
            Assertions.assertNotNull(request_1.getId());
        }

        @Test
        @DisplayName("Проверка метода - findByUser")
        public void findByUser_id() {
            List<ItemRequest> result = requestRepository.findByUser(user_2);
            Assertions.assertEquals(result.size(), 1);
        }

        @Test
        @DisplayName("Проверка метода - findByUser")
        public void findByUser() {
            List<ItemRequest> result = requestRepository.findByUser(user_2);
            Assertions.assertEquals(result.size(), 1);
            Assertions.assertEquals(result.get(0).getDescription(), "description-2");
        }
    }
}