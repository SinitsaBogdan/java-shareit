package ru.practicum.shareit.user.repo;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import ru.practicum.shareit.user.model.User;

import javax.validation.ValidationException;

@DataJpaTest
@DisplayName("Интеграционное тестирование - UserRepository")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private final User user = User.builder().name("user-1").email("mail@mail.ru").build();

    @Test
    @DisplayName("CONFIG")
    public void contextLoads() {
        Assertions.assertNotNull(userRepository);
    }

    @Nested
    @DisplayName("ENTITY")
    public class Entity {

        @Test
        @DisplayName("Проверка валидации поля name - MaxLength20")
        public void save__Fail_Name_MaxLength20() {
            User user = User.builder().name("user-1-BigName-BigName-BigName-33").email("mail@mail.ru").build();
            Assertions.assertThrows(DataIntegrityViolationException.class, () -> userRepository.save(user));
        }

        @Test
        @DisplayName("Проверка валидации поля name - @CustomValidNotBlank")
        public void save__Fail_Name_CustomValidNotBlank() {
            User user = User.builder().name("").email("mail@mail.ru").build();
            Assertions.assertThrows(ValidationException.class, () -> userRepository.save(user));
        }

        @Test
        @DisplayName("Проверка валидации поля email - MaxLength36")
        public void save__Fail_Name_MaxLength36() {
            User user = User.builder().name("user").email("mail_BigName_BigName_BigName_BigName@mail.ru").build();
            Assertions.assertThrows(DataIntegrityViolationException.class, () -> userRepository.save(user));
        }

        @Test
        @DisplayName("Проверка валидации поля email - @CustomValidEmail [ v1 ]")
        public void save__Fail_Name_CustomValidEmail_v1() {
            User user = User.builder().name("user").email("mail.mail.ru").build();
            Assertions.assertThrows(ValidationException.class, () -> userRepository.save(user));
        }

        @Test
        @DisplayName("Проверка валидации поля email - @CustomValidEmail [ v2 ]")
        public void save__Fail_Name_CustomValidEmail_v2() {
            User user = User.builder().name("user").email("mail@mail_ru").build();
            Assertions.assertThrows(ValidationException.class, () -> userRepository.save(user));
        }

        @Test
        @DisplayName("Проверка валидации поля email - unique")
        public void save__Fail_Email_Unique() {
            userRepository.save(user);
            User unique = User.builder().name("user").email("mail@mail.ru").build();
            Assertions.assertThrows(DataIntegrityViolationException.class, () -> userRepository.save(unique));
        }
    }

    @Nested
    @DisplayName("REPOSITORY")
    public class Repository {

        @BeforeEach
        public void beforeEach() {
            Assertions.assertNull(user.getId());
            userRepository.save(user);
        }

        @Test
        @DisplayName("Успешное сохранение объекта в таблицу USERS")
        public void save() {
            Assertions.assertNotNull(user.getId());
        }
    }
}