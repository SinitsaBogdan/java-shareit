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

    @Test
    @DisplayName("Сериализация CommentDto объекта в json")
    public void testCommentDto() throws Exception {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        CommentDto commentDto = CommentDto.builder()
                .id(1L)
                .authorName("user")
                .created(LocalDateTime.of(2023, 10, 1, 10, 0))
                .text("text item")
                .build();

        JsonContent<CommentDto> result = json.write(commentDto);

        Set<ConstraintViolation<CommentDto>> violations = validator.validate(commentDto);
        assertEquals(0, violations.size());

        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(1);
        assertThat(result).extractingJsonPathStringValue("$.authorName").isEqualTo("user");
        assertThat(result).extractingJsonPathStringValue("$.created").isEqualTo("2023-10-01T10:00:00");
        assertThat(result).extractingJsonPathStringValue("$.text").isEqualTo("text item");
    }

    @Test
    @DisplayName("Проверка валидации поля Text - @NotBlank")
    public void testCommentDto__Text_NotBlank__Empty() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        CommentDto commentDto = CommentDto.builder()
                .text("")
                .build();

        Set<ConstraintViolation<CommentDto>> violations = validator.validate(commentDto);
        assertEquals(1, violations.size());
    }

    @Test
    @DisplayName("Проверка валидации поля Text - @NotBlank")
    public void testCommentDto__Text_NotBlank__Null() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        CommentDto commentDto = CommentDto.builder()
                .build();

        Set<ConstraintViolation<CommentDto>> violations = validator.validate(commentDto);
        assertEquals(1, violations.size());
    }
}