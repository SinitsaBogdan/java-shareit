package ru.practicum.shareit.request.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.repo.ItemRequestRepository;
import ru.practicum.shareit.user.repo.UserRepository;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ItemRequestServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ItemRequestRepository requestRepository;

    @InjectMocks
    private ItemRequestServiceImpl service;

    @Test
    @DisplayName("Тестирование метода - service.findAll_v1")
    public void findAll_v1() {
        when(userRepository.findById(anyLong())).thenReturn(null);
        when(requestRepository.findByUser(any())).thenReturn(null);    }

    @Test
    @DisplayName("Тестирование метода - service.findAll_v2")
    public void findAll_v2() {
        when(userRepository.findById(anyLong())).thenReturn(null);
        when(requestRepository.findItemRequest(anyLong(), any())).thenReturn(null);
    }

    @Test
    @DisplayName("Тестирование метода - service.findOne")
    public void findOne() {
        when(userRepository.findById(anyLong())).thenReturn(null);
        when(requestRepository.findById(anyLong())).thenReturn(null);
    }

    @Test
    @DisplayName("Тестирование метода - service.save")
    public void save() {
        when(requestRepository.findById(anyLong())).thenReturn(null);
        when(requestRepository.save(any())).thenReturn(null);
    }
}