package ru.practicum.shareit.booking.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.item.model.Item;

import java.time.Instant;

/**
 * TODO Sprint add-bookings.
 */

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookingDto {

    private Item item;
    private Instant start;
    private Instant end;
}
