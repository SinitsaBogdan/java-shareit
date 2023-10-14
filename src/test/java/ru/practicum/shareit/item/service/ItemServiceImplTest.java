package ru.practicum.shareit.item.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.booking.repo.BookingRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repo.CommentRepository;
import ru.practicum.shareit.item.repo.ItemRepository;
import ru.practicum.shareit.request.repo.ItemRequestRepository;
import ru.practicum.shareit.user.repo.UserRepository;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ItemServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private ItemRequestRepository requestRepository;

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private ItemService service;

    private Item model;

    @Test
    @DisplayName("Тестирование метода - service.getAllByUserId")
    public void getAllByUserId() {
        when(userRepository.findById(anyLong())).thenReturn(null);
        when(itemRepository.findByUser(any())).thenReturn(null);
        when(bookingRepository.findByItem_User(any())).thenReturn(null);
    }

    @Test
    @DisplayName("Тестирование метода - service.getById")
    public void getById() {
        when(itemRepository.findByUser(any())).thenReturn(null);
        when(bookingRepository.findListToLastBooking(any(), any())).thenReturn(null);
        when(bookingRepository.findListToNextBooking(any(), any())).thenReturn(null);
    }

    @Test
    @DisplayName("Тестирование метода - service.getBySearchText")
    public void getBySearchText() {
        when(itemRepository.findSearch(anyString())).thenReturn(null);
    }

    @Test
    @DisplayName("Тестирование метода - service.saveItem")
    public void saveItem() {
        when(userRepository.findById(anyLong())).thenReturn(null);
        when(requestRepository.findById(anyLong())).thenReturn(null);
        when(itemRepository.save(any())).thenReturn(null);
    }

    @Test
    @DisplayName("Тестирование метода - service.saveComment")
    public void saveComment() {
        when(itemRepository.findById(anyLong())).thenReturn(null);
        when(userRepository.findById(anyLong())).thenReturn(null);
        when(bookingRepository.findFirstBookingByUserAndItemOrderByStartAsc(any(), any())).thenReturn(null);
        when(commentRepository.save(any())).thenReturn(null);
    }

    @Test
    @DisplayName("Тестирование метода - service.updateItem")
    public void updateItem() {
        when(userRepository.findById(anyLong())).thenReturn(null);
        when(itemRepository.findById(anyLong())).thenReturn(null);
        when(itemRepository.save(any())).thenReturn(null);
    }
}