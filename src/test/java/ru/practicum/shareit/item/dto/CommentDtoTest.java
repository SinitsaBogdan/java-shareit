package ru.practicum.shareit.item.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDateTime;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@JsonTest
class CommentDtoTest {

    @Autowired
    private JacksonTester<CommentDto> json;

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    @Test
    @DisplayName("Сериализация CommentDto объекта в json ( id, authorName, created, text )")
    public void testCommentDto() throws Exception {
        CommentDto dto = CommentDto.builder()
                .id(1L)
                .authorName("user")
                .created(LocalDateTime.of(2023, 10, 1, 10, 0))
                .text("text item")
                .build();

        JsonContent<CommentDto> result = json.write(dto);

        Set<ConstraintViolation<CommentDto>> violations = validator.validate(dto);
        assertEquals(0, violations.size());

        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(1);
        assertThat(result).extractingJsonPathStringValue("$.authorName").isEqualTo("user");
        assertThat(result).extractingJsonPathStringValue("$.created").isEqualTo("2023-10-01T10:00:00");
        assertThat(result).extractingJsonPathStringValue("$.text").isEqualTo("text item");
    }

    @Test
    @DisplayName("Проверка валидации поля Text - @NotBlank")
    public void testCommentDto__Text_NotBlank__Empty() {
        CommentDto dto = CommentDto.builder().text("").build();

        Set<ConstraintViolation<CommentDto>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
    }

    @Test
    @DisplayName("Проверка валидации поля Text - @NotBlank")
    public void testCommentDto__Text_NotBlank__Null() {
        CommentDto dto = CommentDto.builder().build();

        Set<ConstraintViolation<CommentDto>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
    }
}