package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.dto.UserDto;

import java.util.List;

public interface UserService {

    List<UserDto> getAll();

    UserDto getById(long userId, long id);

    UserDto add(long userId, UserDto user);

    UserDto updateAll(long userId, UserDto user);

    List<UserDto> delete(long userId);
}
