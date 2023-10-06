package ru.practicum.shareit.user.service;

import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.user.dto.UserDto;

import java.util.List;

@Transactional(readOnly = true)
public interface UserService {

    List<UserDto> getAll();

    UserDto getById(long userId);

    @Transactional
    UserDto add(UserDto user);

    @Transactional
    UserDto update(UserDto user);

    @Transactional
    void deleteById(long userId);
}
