package ru.practicum.shareit.user.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.repo.UserRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Override
    public List<UserDto> getAll() {
        return null;
    }

    @Override
    public UserDto getById(Long userId, Long id) {
        return null;
    }

    @Override
    public UserDto add(Long userId, UserDto user) {
        return null;
    }

    @Override
    public UserDto update(Long userId, UserDto user) {
        return null;
    }

    @Override
    public List<UserDto> delete(Long userId) {
        return null;
    }
}
