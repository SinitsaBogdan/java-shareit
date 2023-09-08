package ru.practicum.shareit.user.dto;

import ru.practicum.shareit.user.model.User;

public class UserMapper {

    public static UserDto mapperUserToDto(User user) {
        return new UserDto();
    }

    public static User mapperUserDtoToUser(UserDto userDto) {
        return new User();
    }
}
