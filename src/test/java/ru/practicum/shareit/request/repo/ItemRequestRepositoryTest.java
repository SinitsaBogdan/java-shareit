package ru.practicum.shareit.request.repo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
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
            User user = User.builder()
                    .name("user-1")
                    .email("mail-1@mail.ru")
                    .build();

            ItemRequest request = ItemRequest.builder()
                    .created(LocalDateTime.now())
                    .user(user)
                    .build();

            Assertions.assertThrows(DataIntegrityViolationException.class, () -> requestRepository.save(request));
        }

        @Test
        @DisplayName("Проверка валидации поля description - nullable")
        public void save__Fail_Name_nullable() {
            User user = User.builder()
                    .name("user-1")
                    .email("mail-1@mail.ru")
                    .build();

            ItemRequest request = ItemRequest.builder()
                    .description("description")
                    .user(user)
                    .build();

            Assertions.assertThrows(DataIntegrityViolationException.class, () -> requestRepository.save(request));
        }
    }

    @Nested
    @DisplayName("REPOSITORY")
    public class Repository {

        @Test
        @DisplayName("Успешное сохранение объекта в таблицу REQUESTS")
        public void save() {
            User user = User.builder()
                    .name("user-1")
                    .email("mail-1@mail.ru")
                    .build();

            ItemRequest request = ItemRequest.builder()
                    .description("description")
                    .created(LocalDateTime.now())
                    .user(user)
                    .build();

            Assertions.assertNull(request.getId());
            requestRepository.save(request);
            Assertions.assertNotNull(request.getId());
        }

        @Test
        @DisplayName("Проверка метода - findByUser(User user)")
        public void findByUser_id() {

            User user_1 = User.builder()
                    .name("user-1")
                    .email("mail-1@mail.ru")
                    .build();

            User user_2 = User.builder()
                    .name("user-2")
                    .email("mail-2@mail.ru")
                    .build();

            ItemRequest request = ItemRequest.builder()
                    .description("description")
                    .created(LocalDateTime.now())
                    .user(user_2)
                    .build();

            userRepository.save(user_1);
            userRepository.save(user_2);
            requestRepository.save(request);

            List<ItemRequest> result = requestRepository.findByUser(user_2);
            Assertions.assertEquals(result.size(), 1);
        }

        @Test
        @DisplayName("Проверка метода - findByUser(User user)")
        public void findByUser() {

            User user_1 = User.builder()
                    .name("user-1")
                    .email("mail-1@mail.ru")
                    .build();

            User user_2 = User.builder()
                    .name("user-2")
                    .email("mail-2@mail.ru")
                    .build();

            ItemRequest request_1 = ItemRequest.builder()
                    .description("description-1")
                    .created(LocalDateTime.now())
                    .user(user_1)
                    .build();

            ItemRequest request_2 = ItemRequest.builder()
                    .description("description-2")
                    .created(LocalDateTime.now())
                    .user(user_2)
                    .build();

            userRepository.save(user_1);
            userRepository.save(user_2);
            requestRepository.save(request_1);
            requestRepository.save(request_2);

            List<ItemRequest> result = requestRepository.findByUser(user_2);
            Assertions.assertEquals(result.size(), 1);
            Assertions.assertEquals(result.get(0).getDescription(), "description-1");
        }
    }
}