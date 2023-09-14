package ru.practicum.shareit.item.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

/**
 * TODO Sprint add-controllers.
 */

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ItemDto {

    private Long id;
    private String name;
    private String description;
    private Boolean available;
}
