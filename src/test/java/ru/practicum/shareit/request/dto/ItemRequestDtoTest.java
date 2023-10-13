package ru.practicum.shareit.request.dto;

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
import java.util.ArrayList;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@JsonTest
class ItemRequestDtoTest {

    @Autowired
    private JacksonTester<ItemRequestDto> json;

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    @Test
    @DisplayName("Сериализация BookingRequestDto объекта в json ( id, description, created, items )")
    public void testUserDto__ItemIdStartEnd() throws Exception {
        ItemRequestDto dto = ItemRequestDto.builder()
                .id(1L)
                .description("description")
                .created(LocalDateTime.of(2023, 10, 20, 10, 0))
                .items(new ArrayList<>())
                .build();

        JsonContent<ItemRequestDto> result = json.write(dto);

        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(1);
        assertThat(result).extractingJsonPathStringValue("$.description").isEqualTo("description");
        assertThat(result).extractingJsonPathStringValue("$.created").isEqualTo("2023-10-20T10:00:00");
        assertThat(result).extractingJsonPathArrayValue("$.items").size().isEqualTo(0);
    }

    @Test
    @DisplayName("Проверка валидации поля created - @NotBlank")
    public void testUserDto__End_NotNull() {
        ItemRequestDto dto = ItemRequestDto.builder()
                .id(1L)
                .description("")
                .created(LocalDateTime.of(2023, 10, 20, 10, 0))
                .items(new ArrayList<>())
                .build();

        Set<ConstraintViolation<ItemRequestDto>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
    }
}