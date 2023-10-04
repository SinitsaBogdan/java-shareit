package ru.practicum.shareit.user.dto;

import org.jetbrains.annotations.NotNull;
import ru.practicum.shareit.user.model.User;

public class UserMapper {

    public static UserDto mapperUserToDto(@NotNull User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

    public static User mapperUserDtoToUser(@NotNull UserDto userDto) {
        return User.builder()
                .id(userDto.getId())
                .name(userDto.getName())
                .email(userDto.getEmail())
                .build();
    }
}
