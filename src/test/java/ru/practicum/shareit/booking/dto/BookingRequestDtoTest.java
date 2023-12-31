package ru.practicum.shareit.booking.dto;

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
class BookingRequestDtoTest {

    @Autowired
    private JacksonTester<BookingRequestDto> json;

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    @Test
    @DisplayName("Сериализация BookingRequestDto объекта в json ( itemId, start, end )")
    public void testUserDto__ItemIdStartEnd() throws Exception {
        BookingRequestDto dto = BookingRequestDto.builder()
                .itemId(1L)
                .start(LocalDateTime.of(2023, 10, 1, 10, 0))
                .end(LocalDateTime.of(2023, 10, 1, 12, 0))
                .build();

        JsonContent<BookingRequestDto> result = json.write(dto);

        Set<ConstraintViolation<BookingRequestDto>> violations = validator.validate(dto);
        assertEquals(0, violations.size());

        assertThat(result).extractingJsonPathNumberValue("$.itemId").isEqualTo(1);
        assertThat(result).extractingJsonPathStringValue("$.start").isEqualTo("2023-10-01T10:00:00");
        assertThat(result).extractingJsonPathStringValue("$.end").isEqualTo("2023-10-01T12:00:00");
    }

    @Test
    @DisplayName("Проверка валидации поля ItemId - @Positive")
    public void testUserDto__ItemId_Positive() {
        BookingRequestDto dto = BookingRequestDto.builder()
                .itemId(-1L)
                .start(LocalDateTime.of(2023, 10, 1, 10, 0))
                .end(LocalDateTime.of(2023, 10, 1, 12, 0))
                .build();

        Set<ConstraintViolation<BookingRequestDto>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
    }

    @Test
    @DisplayName("Проверка валидации поля Start - @NotNull")
    public void testUserDto__Start_NotNull() {
        BookingRequestDto dto = BookingRequestDto.builder()
                .itemId(1L)
                .end(LocalDateTime.of(2023, 10, 1, 10, 0))
                .build();

        Set<ConstraintViolation<BookingRequestDto>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
    }

    @Test
    @DisplayName("Проверка валидации поля End - @NotNull")
    public void testUserDto__End_NotNull() {
        BookingRequestDto dto = BookingRequestDto.builder()
                .itemId(1L)
                .start(LocalDateTime.of(2023, 10, 1, 10, 0))
                .build();

        Set<ConstraintViolation<BookingRequestDto>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
    }
}