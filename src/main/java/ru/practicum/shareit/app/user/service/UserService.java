package ru.practicum.shareit.app.user.service;

import ru.practicum.shareit.app.user.dto.UserDto;

import java.util.List;

public interface UserService {

    List<UserDto> getAll();

    UserDto getById(Long userId);

    UserDto add(UserDto user);

    UserDto update(UserDto user);

    void deleteById(Long userId);
}
