package ru.practicum.shareit.user.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repo.UserRepository;
import ru.practicum.shareit.util.Validator;
import ru.practicum.shareit.util.exeptions.ShareitException;

import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.shareit.util.exeptions.ErrorMessage.USER_ERROR__VALID_DUPLICATE__EMAIL;


@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Override
    public List<UserDto> getAll() {
        return userRepository.findAll().stream()
                .map(UserMapper::mapperUserToDto)
                .collect(Collectors.toList());
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
        User update = UserMapper.mapperUserDtoToUser(user);
        User result = userRepository.findById(user.getId());

        System.out.println(result);
        System.out.println(update);

        if (update.getName() != null && !update.getName().equals(result.getName())) result.setName(update.getName());
        if (update.getEmail() != null && !update.getEmail().equals(result.getEmail())) {
            if (userRepository.checkEmailDuplicate(update.getEmail())) throw new ShareitException(USER_ERROR__VALID_DUPLICATE__EMAIL);
            userRepository.removeDataEmail(result.getEmail());
            userRepository.addDataEmail(update.getEmail());
            result.setEmail(update.getEmail());
        }

        result = userRepository.update(result);
        return UserMapper.mapperUserToDto(result);
    }

    @Override
    public void deleteById(Long userId) {
        Validator.checkIdInUserRepo(userId, userRepository);
        User user = userRepository.findById(userId);
        userRepository.deleteById(user);
    }
}
