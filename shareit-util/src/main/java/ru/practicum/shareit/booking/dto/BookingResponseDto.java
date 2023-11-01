package ru.practicum.shareit.booking.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class BookingResponseDto {

    private Long id;

    private LocalDateTime start;

    private LocalDateTime end;

    private String status;

    private LocalBooker booker;

    private LocalItem item;

    @Data
    public static class LocalBooker {
        private final long id;
    }

    @Data
    public static class LocalItem {
        private final long id;
        private final String name;
    }
}