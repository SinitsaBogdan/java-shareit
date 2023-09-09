package ru.practicum.shareit.user.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repo.UserRepository;
import ru.practicum.shareit.util.Validator;
import ru.practicum.shareit.util.exeptions.ShareitException;

import java.util.ArrayList;
import java.util.List;

import static ru.practicum.shareit.util.exeptions.ErrorMessage.REPOSITORY_ERROR__USER__ID_NOT_IN_REPO__ID;

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
        User user = userRepository.findById(userId);
        if (user.getId() == null || !userRepository.checkId(user.getId())) throw new ShareitException(REPOSITORY_ERROR__USER__ID_NOT_IN_REPO__ID);
        User result = userRepository.findById(userId);
        return UserMapper.mapperUserToDto(result);
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
        if (user.getId() == null || !userRepository.checkId(user.getId())) throw new ShareitException(REPOSITORY_ERROR__USER__ID_NOT_IN_REPO__ID);
        User result = UserMapper.mapperUserDtoToUser(user);
        result = userRepository.update(result);
        return UserMapper.mapperUserToDto(result);
    }

    @Override
    public void delete(Long userId) {
        User user = userRepository.findById(userId);
        if (user.getId() == null || !userRepository.checkId(user.getId())) throw new ShareitException(REPOSITORY_ERROR__USER__ID_NOT_IN_REPO__ID);
        userRepository.deleteById(user);
    }
}
