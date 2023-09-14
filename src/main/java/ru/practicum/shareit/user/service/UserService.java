package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.dto.UserDto;

import java.util.List;

public interface UserService {

    List<UserDto> getAll();

    UserDto getById(Long userId);

    UserDto add(UserDto user);

    UserDto update(UserDto user);

    void deleteById(Long userId);
}
