package ru.practicum.shareit.user.service;

import lombok.AllArgsConstructor;
import org.hibernate.PropertyValueException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repo.UserRepository;
import ru.practicum.shareit.util.exeptions.RepositoryException;
import ru.practicum.shareit.user.util.MapperUser;
import ru.practicum.shareit.util.exeptions.ServiceException;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.practicum.shareit.util.exeptions.ErrorMessage.*;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<UserDto> getAll() {
        return userRepository.findAll().stream()
                .map(MapperUser::mapperEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto getById(long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) throw new RepositoryException(REPOSITORY_ERROR__USER__ID_NOT_IN_REPO__ID);
        return MapperUser.mapperEntityToDto(optionalUser.get());
    }

    @Override
    @Transactional
    public UserDto save(UserDto user) {
        return MapperUser.mapperEntityToDto(userRepository.save(MapperUser.mapperDtoToEntity(user)));
    }

    @Override
    @Transactional
    public UserDto update(UserDto userDto) {
        User update = MapperUser.mapperDtoToEntity(userDto);
        Optional<User> optionalUser = userRepository.findById(update.getId());

        if (optionalUser.isEmpty()) throw new RepositoryException(REPOSITORY_ERROR__USER__ID_NOT_IN_REPO__ID);
        User user = optionalUser.get();

        if (update.getName() != null && !update.getName().equals(user.getName())) user.setName(update.getName());
        if (update.getEmail() != null && !update.getEmail().equals(user.getEmail())) user.setEmail(update.getEmail());

        try {
            return MapperUser.mapperEntityToDto(userRepository.save(user));
        } catch (IllegalArgumentException exception) {
            throw new RepositoryException(USER_ERROR__VALID_DUPLICATE__EMAIL);
        }
    }

    @Override
    @Transactional
    public void deleteById(long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) throw new RepositoryException(REPOSITORY_ERROR__USER__ID_NOT_IN_REPO__ID);
        userRepository.deleteById(userId);
    }
}
