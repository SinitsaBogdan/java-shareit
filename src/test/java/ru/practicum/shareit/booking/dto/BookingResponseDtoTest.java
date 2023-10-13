package ru.practicum.shareit.booking.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class BookingResponseDtoTest {

    @Autowired
    private JacksonTester<BookingResponseDto> json;

    @Test
    @DisplayName("Сериализация BookingResponseDto объекта в json ( id, start, end, status, booker, item )")
    public void testUserDto__ItemIdStartEnd() throws Exception {
        BookingResponseDto dto = BookingResponseDto.builder()
                .id(1L)
                .start(LocalDateTime.of(2023, 10, 20, 10, 0))
                .end(LocalDateTime.of(2023, 10, 20, 12, 0))
                .status("STATUS")
                .booker(new BookingResponseDto.LocalBooker(1L))
                .item(new BookingResponseDto.LocalItem(1L, "item"))
                .build();

        JsonContent<BookingResponseDto> result = json.write(dto);

        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(1);
        assertThat(result).extractingJsonPathStringValue("$.start").isEqualTo("2023-10-20T10:00:00");
        assertThat(result).extractingJsonPathStringValue("$.end").isEqualTo("2023-10-20T12:00:00");
        assertThat(result).extractingJsonPathStringValue("$.status").isEqualTo("STATUS");
        assertThat(result).extractingJsonPathNumberValue("$.booker.id").isEqualTo(1);
        assertThat(result).extractingJsonPathNumberValue("$.item.id").isEqualTo(1);
        assertThat(result).extractingJsonPathStringValue("$.item.name").isEqualTo("item");
    }
}