package ru.practicum.shareit.user.dto;

import lombok.Builder;
import lombok.Data;

/**
 * TODO Sprint add-item-requests.
 */

@Data
@Builder
public class UserDto {

    private String name, email;
}
