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
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@JsonTest
class ItemDtoTest {

    @Autowired
    private JacksonTester<ItemDto> json;

    @Test
    @DisplayName("Сериализация ItemDto объекта в json")
    public void testItemDto() throws Exception {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        ItemDto itemDto = ItemDto.builder()
                .id(1L)
                .name("item-1")
                .description("description")
                .available(true)
                .build();

        JsonContent<ItemDto> result = json.write(itemDto);

        Set<ConstraintViolation<ItemDto>> violations = validator.validate(itemDto);
        assertEquals(0, violations.size());

        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(1);
        assertThat(result).extractingJsonPathStringValue("$.name").isEqualTo("item-1");
        assertThat(result).extractingJsonPathStringValue("$.description").isEqualTo("description");
        assertThat(result).extractingJsonPathBooleanValue("$.available").isEqualTo(true);
        assertThat(result).extractingJsonPathStringValue("$.lastBooking").isEqualTo(null);
        assertThat(result).extractingJsonPathStringValue("$.nextBooking").isEqualTo(null);
        assertThat(result).extractingJsonPathStringValue("$.requestId").isEqualTo(null);
    }

    @Test
    @DisplayName("Сериализация ItemDto объекта в json")
    public void testItemDto__Name_NotBlank__Empty() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        ItemDto itemDto = ItemDto.builder()
                .id(1L)
                .name("")
                .description("description")
                .available(true)
                .build();

        Set<ConstraintViolation<ItemDto>> violations = validator.validate(itemDto);
        assertEquals(1, violations.size());
    }

    @Test
    @DisplayName("Сериализация ItemDto объекта в json")
    public void testItemDto__Name_NotBlank__Null() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        ItemDto itemDto = ItemDto.builder()
                .id(1L)
                .description("description")
                .available(true)
                .build();

        Set<ConstraintViolation<ItemDto>> violations = validator.validate(itemDto);
        assertEquals(1, violations.size());
    }

    @Test
    @DisplayName("Сериализация ItemDto объекта в json")
    public void testItemDto__Description_NotBlank__Empty() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        ItemDto itemDto = ItemDto.builder()
                .id(1L)
                .name("item-1")
                .description("")
                .available(true)
                .build();

        Set<ConstraintViolation<ItemDto>> violations = validator.validate(itemDto);
        assertEquals(1, violations.size());
    }

    @Test
    @DisplayName("Сериализация ItemDto объекта в json")
    public void testItemDto__Description_NotBlank__Null() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        ItemDto itemDto = ItemDto.builder()
                .id(1L)
                .name("item-1")
                .available(true)
                .build();

        Set<ConstraintViolation<ItemDto>> violations = validator.validate(itemDto);
        assertEquals(1, violations.size());
    }

    @Test
    @DisplayName("Сериализация ItemDto объекта в json")
    public void testItemDto__Available_NotNul() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        ItemDto itemDto = ItemDto.builder()
                .id(1L)
                .name("item-1")
                .description("description")
                .build();

        Set<ConstraintViolation<ItemDto>> violations = validator.validate(itemDto);
        assertEquals(1, violations.size());
    }
}