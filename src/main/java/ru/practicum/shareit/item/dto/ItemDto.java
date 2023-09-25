package ru.practicum.shareit.item.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.booking.dto.BookingShortDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * TODO Sprint add-controllers.
 */

@Data
@Builder
public class ItemDto {

    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String description;

    @NotNull
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean available;


    private BookingShortDto lastBooking;

    private BookingShortDto nextBooking;
}
