package ru.practicum.shareit.item.repo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.user.model.User;

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
        @DisplayName("Успешное сохранение объекта в таблицу COMMENTS")
        public void save() {
            Comment comment = Comment.builder()
                    .text("text")
                    .created(LocalDateTime.now())
                    .build();

            Assertions.assertNull(comment.getId());
            commentRepository.save(comment);
            Assertions.assertNotNull(comment.getId());
        }
    }

    @Nested
    @DisplayName("REPOSITORY")
    public class Repository {
    }
}