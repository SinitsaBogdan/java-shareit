package ru.practicum.shareit.user.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repo.UserRepository;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService service;

    private User model;

    @Test
    @DisplayName("Тестирование метода - service.getAll")
    public void getAll() {
        when(userRepository.findAll()).thenReturn(null);
    }

    @Test
    @DisplayName("Тестирование метода - service.getById")
    public void getById() {
        when(userRepository.findById(anyLong())).thenReturn(null);
    }

    @Test
    @DisplayName("Тестирование метода - service.save")
    public void save() {
        when(userRepository.save(any())).thenReturn(null);
    }

    @Test
    @DisplayName("Тестирование метода - service.update")
    public void update() {
        when(userRepository.findById(anyLong())).thenReturn(null);
        when(userRepository.save(any())).thenReturn(null);
    }

    @Test
    @DisplayName("Тестирование метода - service.deleteById")
    public void deleteById() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(User.builder().id(1L).name("user").email("mail").build()));
    }
}