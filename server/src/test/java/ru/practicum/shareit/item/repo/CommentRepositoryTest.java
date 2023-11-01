package ru.practicum.shareit.item.repo;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import ru.practicum.shareit.item.model.Comment;

import java.time.LocalDateTime;

@DataJpaTest
@DisplayName("Интеграционное тестирование - CommentRepository")
class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    private final Comment comment = Comment.builder().text("text").created(LocalDateTime.now()).build();

    @Test
    @DisplayName("CONFIG")
    public void contextLoads() {
        Assertions.assertNotNull(commentRepository);
    }

    @Nested
    @DisplayName("ENTITY")
    public class Entity {

        @Test
        @DisplayName("Проверка валидации поля text - nullable")
        public void save__Fail_Text_nullable() {
            Comment comment = Comment.builder().created(LocalDateTime.now()).build();
            Assertions.assertThrows(DataIntegrityViolationException.class, () -> commentRepository.save(comment));
        }

        @Test
        @DisplayName("Проверка валидации поля created - nullable")
        public void save__Fail_Created_nullable() {
            Comment comment = Comment.builder().text("text").build();
            Assertions.assertThrows(DataIntegrityViolationException.class, () -> commentRepository.save(comment));
        }
    }

    @Nested
    @DisplayName("REPOSITORY")
    public class Repository {

        @BeforeEach
        public void beforeEach() {
            Assertions.assertNull(comment.getId());
            commentRepository.save(comment);
        }

        @Test
        @DisplayName("Успешное сохранение объекта в таблицу COMMENTS")
        public void save() {
            Assertions.assertNotNull(comment.getId());
        }
    }
}