package ru.practicum.shareit.booking.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.repo.BookingRepository;
import ru.practicum.shareit.item.repo.ItemRepository;
import ru.practicum.shareit.user.repo.UserRepository;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookingServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private BookingRepository bookingRepository;

    @InjectMocks
    private BookingService service;

    private Booking model;

    @Test
    @DisplayName("Тестирование метода - service.getAll")
    public void getAll() {
        when(userRepository.findById(anyLong())).thenReturn(null);
        when(bookingRepository.findByUserOrderByStartDesc(any(), any())).thenReturn(null);
        when(bookingRepository.findAllBookingStatePast(anyLong(), any(), any())).thenReturn(null);
        when(bookingRepository.findByUserAndStartAfterOrderByStartDesc(any(), any(), any())).thenReturn(null);
        when(bookingRepository.findAllBookingStateCurrent(anyLong(), any(), any())).thenReturn(null);
        when(bookingRepository.findAllBookingState(anyLong(), any(), any())).thenReturn(null);
        when(bookingRepository.findAllBookingState(anyLong(), any(), any())).thenReturn(null);
    }

    @Test
    @DisplayName("Тестирование метода - service.getAllInItemOwner")
    public void getAllInItemOwner() {
        when(userRepository.findById(anyLong())).thenReturn(null);
        when(bookingRepository.findByBookingUser(anyLong(), any())).thenReturn(null);
        when(bookingRepository.findAllBookingUserStatePast(anyLong(), any(), any())).thenReturn(null);
        when(bookingRepository.findByBookingUserAndStartAfter(anyLong(), any(), any())).thenReturn(null);
        when(bookingRepository.findAllBookingUserStateCurrent(anyLong(), any(), any())).thenReturn(null);
        when(bookingRepository.findAllUserBookingState(anyLong(), any(), any())).thenReturn(null);
        when(bookingRepository.findAllUserBookingState(anyLong(), any(), any())).thenReturn(null);    }

    @Test
    @DisplayName("Тестирование метода - service.getById")
    public void getById() {
        when(bookingRepository.findById(anyLong())).thenReturn(null);
    }

    @Test
    @DisplayName("Тестирование метода - service.save")
    public void save() {
        when(userRepository.findById(anyLong())).thenReturn(null);
        when(itemRepository.findById(anyLong())).thenReturn(null);
        when(bookingRepository.save(any())).thenReturn(null);
    }

    @Test
    @DisplayName("Тестирование метода - service.updateApproved")
    public void updateApproved() {
        when(userRepository.findById(anyLong())).thenReturn(null);
        when(bookingRepository.findById(anyLong())).thenReturn(null);
        when(bookingRepository.save(any())).thenReturn(null);    }
}