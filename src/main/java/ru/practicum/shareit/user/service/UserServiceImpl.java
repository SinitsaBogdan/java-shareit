package ru.practicum.shareit.user.service;

import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repo.UserRepository;
import ru.practicum.shareit.util.exeptions.ServiceException;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static ru.practicum.shareit.util.exeptions.ErrorMessage.REPOSITORY_ERROR__USER__ID_NOT_IN_REPO__ID;
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
        try {
            User user = userRepository.getById(userId);
            return UserMapper.mapperUserToDto(user);
        } catch (EntityNotFoundException exception) {
            throw new ServiceException(REPOSITORY_ERROR__USER__ID_NOT_IN_REPO__ID);
        }
    }

    @Override
    public UserDto add(UserDto user) {
        User result = UserMapper.mapperUserDtoToUser(user);
        try {
            result = userRepository.save(result);
            return UserMapper.mapperUserToDto(result);
        } catch (DataIntegrityViolationException exception) {
            throw new ServiceException(USER_ERROR__VALID_DUPLICATE__EMAIL);
        }
    }

    @Override
    public UserDto update(UserDto userDto) {
        User update = UserMapper.mapperUserDtoToUser(userDto);

        try {
            User user = userRepository.getById(update.getId());

            if (update.getName() != null && !update.getName().equals(user.getName())) user.setName(update.getName());
            if (update.getEmail() != null && !update.getEmail().equals(user.getEmail())) user.setEmail(update.getEmail());

            user = userRepository.save(user);
            return UserMapper.mapperUserToDto(user);
        } catch (DataIntegrityViolationException exception) {
            throw new ServiceException(USER_ERROR__VALID_DUPLICATE__EMAIL);
        } catch (EntityNotFoundException exception) {
            throw new ServiceException(REPOSITORY_ERROR__USER__ID_NOT_IN_REPO__ID);
        }
    }

    @Override
    public void deleteById(Long userId) {
        try {
            userRepository.deleteById(userId);
        } catch (NoSuchElementException exception) {
            throw new ServiceException(REPOSITORY_ERROR__USER__ID_NOT_IN_REPO__ID);
        }
    }
}
