package ru.practicum.shareit.booking.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookingRequestDto {

    @NotNull
    private final LocalDateTime start;

    @NotNull
    private final LocalDateTime end;

    private final Long itemId;
}
