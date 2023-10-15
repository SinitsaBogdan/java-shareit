package ru.practicum.shareit.request.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.repo.ItemRequestRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repo.UserRepository;
import ru.practicum.shareit.util.exeptions.RepositoryException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static ru.practicum.shareit.util.exeptions.ErrorMessage.REPOSITORY_ERROR__REQUEST__ID_NOT_IN_REPO__ID;
import static ru.practicum.shareit.util.exeptions.ErrorMessage.REPOSITORY_ERROR__USER__ID_NOT_IN_REPO__ID;

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
    public void findAll_v1__Valid_Param() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(User.builder().build()));
        when(requestRepository.findByUser(any())).thenReturn(new ArrayList<>());
        List<ItemRequestDto> result = service.findAll(1L);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.size(), 0);
    }

    @Test
    @DisplayName("Тестирование метода - service.findAll_v1 : not valid param userId")
    public void findAll_v1__Fail_Valid_Param_UserId() {
        when(userRepository.findById(anyLong()))
                .thenThrow(new RepositoryException(REPOSITORY_ERROR__USER__ID_NOT_IN_REPO__ID));

        final RepositoryException exception = Assertions.assertThrows(
                RepositoryException.class,
                () -> service.findAll(1L));

        Assertions.assertEquals(REPOSITORY_ERROR__USER__ID_NOT_IN_REPO__ID.getDescription(), exception.getMessage());
    }

    @Test
    @DisplayName("Тестирование метода - service.findAll_v2")
    public void findAll_v2__Valid_Param() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(User.builder().build()));
        when(requestRepository.findItemRequest(anyLong(), any())).thenReturn(Page.empty());
        List<ItemRequestDto> result = service.findAll(1L, PageRequest.of(2, 2));
        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.size(), 0);
    }

    @Test
    @DisplayName("Тестирование метода - service.findAll_v2 : not valid param userId")
    public void findAll_v2__Fail_Valid_Param_UserId() {
        when(userRepository.findById(anyLong()))
                .thenThrow(new RepositoryException(REPOSITORY_ERROR__USER__ID_NOT_IN_REPO__ID));

        final RepositoryException exception = Assertions.assertThrows(
                RepositoryException.class,
                () -> service.findAll(1L, PageRequest.of(2, 2)));

        Assertions.assertEquals(REPOSITORY_ERROR__USER__ID_NOT_IN_REPO__ID.getDescription(), exception.getMessage());
    }

    @Test
    @DisplayName("Тестирование метода - service.findOne")
    public void findOne__Valid_Param() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(User.builder().build()));
        when(requestRepository.findById(anyLong())).thenReturn(Optional.of(ItemRequest.builder().id(1L).build()));
        ItemRequestDto result = service.findOne(1L, 1L);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1L, result.getId());
    }

    @Test
    @DisplayName("Тестирование метода - service.findOne : not valid param userId")
    public void findOne__Fail_Valid_Param_UserId() {
        when(userRepository.findById(anyLong()))
                .thenThrow(new RepositoryException(REPOSITORY_ERROR__USER__ID_NOT_IN_REPO__ID));

        final RepositoryException exception = Assertions.assertThrows(
                RepositoryException.class,
                () -> service.findOne(1L, 1L));

        Assertions.assertEquals(REPOSITORY_ERROR__USER__ID_NOT_IN_REPO__ID.getDescription(), exception.getMessage());
    }

    @Test
    @DisplayName("Тестирование метода - service.findOne : not valid param requestId")
    public void findOne__Fail_Valid_Param_RequestId() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(User.builder().build()));
        when(requestRepository.findById(anyLong())).thenThrow(new RepositoryException(REPOSITORY_ERROR__REQUEST__ID_NOT_IN_REPO__ID));

        final RepositoryException exception = Assertions.assertThrows(
                RepositoryException.class,
                () -> service.findOne(1L, 1L));

        Assertions.assertEquals(REPOSITORY_ERROR__REQUEST__ID_NOT_IN_REPO__ID.getDescription(), exception.getMessage());
    }

    @Test
    @DisplayName("Тестирование метода - service.save")
    public void save__Valid_Param() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(User.builder().build()));
        when(requestRepository.save(any())).thenReturn(ItemRequest.builder().build());

        ItemRequestDto result = service.save(1L, ItemRequestDto.builder().build());
        Assertions.assertNotNull(result);
    }

    @Test
    @DisplayName("Тестирование метода - service.save : not valid param userId")
    public void save__Fail_Valid_Param_UserId() {
        when(userRepository.findById(anyLong()))
                .thenThrow(new RepositoryException(REPOSITORY_ERROR__USER__ID_NOT_IN_REPO__ID));

        final RepositoryException exception = Assertions.assertThrows(
                RepositoryException.class,
                () -> service.save(1L, ItemRequestDto.builder().build()));

        Assertions.assertEquals(REPOSITORY_ERROR__USER__ID_NOT_IN_REPO__ID.getDescription(), exception.getMessage());
    }
}