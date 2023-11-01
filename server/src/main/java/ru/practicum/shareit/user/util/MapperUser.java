package ru.practicum.shareit.user.util;

import org.jetbrains.annotations.NotNull;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.util.exeptions.CustomException;

public final class MapperUser {

    private MapperUser() {
        throw new CustomException("This is a utility class and cannot be instantiated", 500);
    }

    public static UserDto mapperEntityToDto(@NotNull User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

    public static User mapperDtoToEntity(@NotNull UserDto userDto) {
        return User.builder()
                .id(userDto.getId())
                .name(userDto.getName())
                .email(userDto.getEmail())
                .build();
    }
}
