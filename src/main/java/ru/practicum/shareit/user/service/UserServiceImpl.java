package ru.practicum.shareit.user.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repo.UserRepository;
import ru.practicum.shareit.util.Validator;

import java.util.ArrayList;
import java.util.List;


@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Override
    public List<UserDto> getAll() {
        List<UserDto> result = new ArrayList<>();
        for (User user : userRepository.findAll()) result.add(UserMapper.mapperUserToDto(user));
        return result;
    }

    @Override
    public UserDto getById(Long userId) {
        Validator.checkIdInUserRepo(userId, userRepository);
        User result = userRepository.findById(userId);
        return UserMapper.mapperUserToDto(result);
    }

    @Override
    public UserDto add(UserDto user) {
        Validator.checkValidUser(user, userRepository);
        User result = UserMapper.mapperUserDtoToUser(user);
        result = userRepository.save(result);
        return UserMapper.mapperUserToDto(result);
    }

    @Override
    public UserDto update(UserDto user) {
        Validator.checkIdInUserRepo(user.getId(), userRepository);
        User result = UserMapper.mapperUserDtoToUser(user);
        result = userRepository.update(result);
        return UserMapper.mapperUserToDto(result);
    }

    @Override
    public void delete(Long userId) {
        Validator.checkIdInUserRepo(userId, userRepository);
        User user = userRepository.findById(userId);
        userRepository.deleteById(user);
    }
}
