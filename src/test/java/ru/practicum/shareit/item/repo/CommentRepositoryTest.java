package ru.practicum.shareit.item.repo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import ru.practicum.shareit.item.model.Comment;

import javax.validation.ValidationException;
import java.time.LocalDateTime;

@DataJpaTest
@DisplayName("Интеграционное тестирование - CommentRepository")
class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @Test
    @DisplayName("CONFIG")
    public void contextLoads() {
        Assertions.assertNotNull(commentRepository);
    }

    @Nested
    @DisplayName("ENTITY")
    public class Entity {



        @Test
        @DisplayName("Проверка валидации поля text - @CustomValidNotBlank")
        public void save__Fail_Text_CustomValidNotBlank() {
            Comment comment = Comment.builder().text("").created(LocalDateTime.now()).build();
            Assertions.assertThrows(ValidationException.class, () -> commentRepository.save(comment));
        }

        @Test
        @DisplayName("Проверка валидации поля text - nullable")
        public void save__Fail_Text_nullable() {
            Comment comment = Comment.builder().created(LocalDateTime.now()).build();
            Assertions.assertThrows(ValidationException.class, () -> commentRepository.save(comment));
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

        @Test
        @DisplayName("Успешное сохранение объекта в таблицу COMMENTS")
        public void save() {
            Comment comment = Comment.builder().text("text").created(LocalDateTime.now()).build();
            Assertions.assertNull(comment.getId());
            commentRepository.save(comment);
            Assertions.assertNotNull(comment.getId());
        }
    }
}