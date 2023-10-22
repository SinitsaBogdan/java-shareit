package ru.practicum.shareit.booking.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Builder
@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookingRequestDto {

    private final Long itemId;

    private final LocalDateTime start;

    private final LocalDateTime end;
}
