package ru.practicum.shareit.user.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repo.UserRepository;
import ru.practicum.shareit.util.exeptions.RepositoryException;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static ru.practicum.shareit.util.exeptions.ErrorMessage.REPOSITORY_ERROR__USER__ID_NOT_IN_REPO__ID;
import static ru.practicum.shareit.util.exeptions.ErrorMessage.USER_ERROR__VALID_DUPLICATE__EMAIL;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl service;

    private final User user1 = User.builder().id(1L).name("user-1").email("mail1").build();
    private final User user2 = User.builder().id(2L).name("user-2").email("mail2").build();

    @Test
    @DisplayName("Тестирование метода - service.getAll")
    public void getAll() {
        when(userRepository.findAll()).thenReturn(List.of(user1, user2));
        List<UserDto> result = service.getAll();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.size(), 2);
    }

    @Test
    @DisplayName("Тестирование метода - service.getById : 1L")
    public void getById_OK() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user1));
        UserDto result = service.getById(1L);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(result, UserMapper.mapperUserToDto(user1));
    }

    @Test
    @DisplayName("Тестирование метода - service.getById : not id")
    public void getById_Fail_Id() {
        when(userRepository.findById(anyLong()))
                .thenThrow(new RepositoryException(REPOSITORY_ERROR__USER__ID_NOT_IN_REPO__ID));

        final RepositoryException exception = Assertions.assertThrows(
                RepositoryException.class,
                () -> service.getById(1L));

        Assertions.assertEquals(REPOSITORY_ERROR__USER__ID_NOT_IN_REPO__ID.getDescription(), exception.getMessage());
    }

    @Test
    @DisplayName("Тестирование метода - service.save")
    public void save() {
        when(userRepository.save(any())).thenReturn(user1);
        UserDto result = service.save(UserDto.builder().name("user-1").email("mail-1").build());
    }

    @Test
    @DisplayName("Тестирование метода - service.update : valid model")
    public void update_OK() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user1));
        when(userRepository.save(any())).thenReturn(user2);
        UserDto result = service.update(UserDto.builder().id(1L).email("mail2").build());
        Assertions.assertNotNull(result);
        Assertions.assertEquals(user2.getName(), result.getName());
        Assertions.assertEquals(user2.getEmail(), result.getEmail());
    }

    @Test
    @DisplayName("Тестирование метода - service.update : fail model id")
    public void update_fail_model_id() {
        when(userRepository.findById(anyLong())).thenThrow(new RepositoryException(REPOSITORY_ERROR__USER__ID_NOT_IN_REPO__ID));

        final RepositoryException exception = Assertions.assertThrows(
                RepositoryException.class,
                () -> service.update(UserDto.builder().id(1L).email("mail1").build()));

        Assertions.assertEquals(REPOSITORY_ERROR__USER__ID_NOT_IN_REPO__ID.getDescription(), exception.getMessage());
    }

    @Test
    @DisplayName("Тестирование метода - service.update : fail duplicate email")
    public void update_fail_model_duplicate_email() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user1));
        when(userRepository.save(user1))
                .thenThrow(new RepositoryException(USER_ERROR__VALID_DUPLICATE__EMAIL));

        final Exception exception = Assertions.assertThrows(
                RepositoryException.class,
                () -> service.update(UserDto.builder().id(1L).name("user1").email("mail1").build()));

        Assertions.assertEquals(USER_ERROR__VALID_DUPLICATE__EMAIL.getDescription(), exception.getMessage());
    }

    @Test
    @DisplayName("Тестирование метода - service.deleteById : not id")
    public void deleteById() {
        when(userRepository.findById(anyLong()))
                .thenThrow(new RepositoryException(REPOSITORY_ERROR__USER__ID_NOT_IN_REPO__ID));

        final RepositoryException exception = Assertions.assertThrows(
                RepositoryException.class,
                () -> service.deleteById(1L));

        Assertions.assertEquals(REPOSITORY_ERROR__USER__ID_NOT_IN_REPO__ID.getDescription(), exception.getMessage());
    }
}