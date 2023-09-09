package ru.practicum.shareit.user.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.dto.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repo.UserRepository;
import ru.practicum.shareit.util.Validator;

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
    public UserDto add(UserDto user) {

        Validator.check(user, userRepository);

        User result = UserMapper.mapperUserDtoToUser(user);

        result = userRepository.save(result);
        return UserMapper.mapperUserToDto(result);
    }

    @Override
    public UserDto update(UserDto user) {
        return null;
    }

    @Override
    public List<UserDto> delete(Long userId) {
        return null;
    }
}
