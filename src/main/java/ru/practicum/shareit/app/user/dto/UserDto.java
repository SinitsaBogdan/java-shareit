package ru.practicum.shareit.app.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.util.validation.annotation.NotEmpty;

/**
 * TODO Sprint add-item-requests.
 */

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {

    private Long id;

    private String name;

    @NotEmpty
    private String email;
}
