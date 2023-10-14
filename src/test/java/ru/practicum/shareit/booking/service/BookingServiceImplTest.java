package ru.practicum.shareit.booking.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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
    private BookingServiceImpl service;

    @Test
    @DisplayName("Тестирование метода - service.getAll")
    public void getAll__Valid_Param() {
        when(userRepository.findById(anyLong())).thenReturn(null);
        when(bookingRepository.findByUserOrderByStartDesc(any(), any())).thenReturn(null);
    }

    @Test
    @DisplayName("Тестирование метода - service.getAll : not valid param userId")
    public void getAll__Fail_Valid_Param_UserId() {
        when(userRepository.findById(anyLong())).thenReturn(null);
        when(bookingRepository.findByUserOrderByStartDesc(any(), any())).thenReturn(null);
    }

    @Test
    @DisplayName("Тестирование метода - service.getAll : not valid param state")
    public void getAll__Fail_Valid_Param_State() {
        when(userRepository.findById(anyLong())).thenReturn(null);
        when(bookingRepository.findByUserOrderByStartDesc(any(), any())).thenReturn(null);
    }

    @Test
    @DisplayName("Тестирование метода - service.getAllInItemOwner")
    public void getAllInItemOwner__Valid_Param() {
        when(userRepository.findById(anyLong())).thenReturn(null);
        when(bookingRepository.findByBookingUser(anyLong(), any())).thenReturn(null);
    }

    @Test
    @DisplayName("Тестирование метода - service.getAllInItemOwner : not valid param userId")
    public void getAllInItemOwner__Fail_Valid_Param_UserId() {
        when(userRepository.findById(anyLong())).thenReturn(null);
        when(bookingRepository.findByBookingUser(anyLong(), any())).thenReturn(null);
    }

    @Test
    @DisplayName("Тестирование метода - service.getAllInItemOwner : not valid param state")
    public void getAllInItemOwner__Fail_Valid_Param_State() {
        when(userRepository.findById(anyLong())).thenReturn(null);
        when(bookingRepository.findByBookingUser(anyLong(), any())).thenReturn(null);
    }

    @Test
    @DisplayName("Тестирование метода - service.getById")
    public void getById__Valid_Param() {
        when(bookingRepository.findById(anyLong())).thenReturn(null);
    }

    @Test
    @DisplayName("Тестирование метода - service.getById : not valid param userId")
    public void getById__Fail_Valid_Param_UserId() {
        when(bookingRepository.findById(anyLong())).thenReturn(null);
    }

    @Test
    @DisplayName("Тестирование метода - service.getById : not valid param bookingId")
    public void getById__Fail_Valid_Param_BookingId() {
        when(bookingRepository.findById(anyLong())).thenReturn(null);
    }

    @Test
    @DisplayName("Тестирование метода - service.getById : fail user not owner booking")
    public void getById__Fail_User_Not_Owner_Booking() {
        when(bookingRepository.findById(anyLong())).thenReturn(null);
    }

    @Test
    @DisplayName("Тестирование метода - service.save")
    public void save__Valid_Param() {
        when(userRepository.findById(anyLong())).thenReturn(null);
        when(itemRepository.findById(anyLong())).thenReturn(null);
        when(bookingRepository.save(any())).thenReturn(null);
    }

    @Test
    @DisplayName("Тестирование метода - service.save : not valid param userId")
    public void save__Fail_Valid_Param_UserIdId() {
        when(userRepository.findById(anyLong())).thenReturn(null);
        when(itemRepository.findById(anyLong())).thenReturn(null);
        when(bookingRepository.save(any())).thenReturn(null);
    }

    @Test
    @DisplayName("Тестирование метода - service.save : not valid param itemId")
    public void save__Fail_Valid_Param_ItemId() {
        when(userRepository.findById(anyLong())).thenReturn(null);
        when(itemRepository.findById(anyLong())).thenReturn(null);
        when(bookingRepository.save(any())).thenReturn(null);
    }

    @Test
    @DisplayName("Тестирование метода - service.save : fail booking available")
    public void save__Fail_Booking_Available() {
        when(userRepository.findById(anyLong())).thenReturn(null);
        when(itemRepository.findById(anyLong())).thenReturn(null);
        when(bookingRepository.save(any())).thenReturn(null);
    }

    @Test
    @DisplayName("Тестирование метода - service.save : not valid datetime")
    public void save__Fail_Valid_Datetime() {
        when(userRepository.findById(anyLong())).thenReturn(null);
        when(itemRepository.findById(anyLong())).thenReturn(null);
        when(bookingRepository.save(any())).thenReturn(null);
    }

    @Test
    @DisplayName("Тестирование метода - service.save : not valid datetime start time")
    public void save__Fail_Valid_Datetime_Start_Time() {
        when(userRepository.findById(anyLong())).thenReturn(null);
        when(itemRepository.findById(anyLong())).thenReturn(null);
        when(bookingRepository.save(any())).thenReturn(null);
    }

    @Test
    @DisplayName("Тестирование метода - service.updateApproved")
    public void updateApproved__Valid_Param() {
        when(userRepository.findById(anyLong())).thenReturn(null);
        when(bookingRepository.findById(anyLong())).thenReturn(null);
        when(bookingRepository.save(any())).thenReturn(null);
    }

    @Test
    @DisplayName("Тестирование метода - service.updateApproved : not valid param userId")
    public void updateApproved__Fail_Valid_Param_UserId() {
        when(userRepository.findById(anyLong())).thenReturn(null);
        when(bookingRepository.findById(anyLong())).thenReturn(null);
        when(bookingRepository.save(any())).thenReturn(null);
    }

    @Test
    @DisplayName("Тестирование метода - service.updateApproved : not valid param itemId")
    public void updateApproved__Fail_Valid_Param_ItemId() {
        when(userRepository.findById(anyLong())).thenReturn(null);
        when(bookingRepository.findById(anyLong())).thenReturn(null);
        when(bookingRepository.save(any())).thenReturn(null);
    }

    @Test
    @DisplayName("Тестирование метода - service.updateApproved : fail block save booking user")
    public void updateApproved__Fail_fail_Block_Save_Booking_User() {
        when(userRepository.findById(anyLong())).thenReturn(null);
        when(bookingRepository.findById(anyLong())).thenReturn(null);
        when(bookingRepository.save(any())).thenReturn(null);
    }

    @Test
    @DisplayName("Тестирование метода - service.updateApproved : fail block save booking user not in booking")
    public void updateApproved__Fail_fail_Block_Save_Booking_User_Not_In_Booking() {
        when(userRepository.findById(anyLong())).thenReturn(null);
        when(bookingRepository.findById(anyLong())).thenReturn(null);
        when(bookingRepository.save(any())).thenReturn(null);
    }

    @Test
    @DisplayName("Тестирование метода - service.updateApproved : fail block save booking approve")
    public void updateApproved__Fail_fail_Block_Save_Booking_Approve() {
        when(userRepository.findById(anyLong())).thenReturn(null);
        when(bookingRepository.findById(anyLong())).thenReturn(null);
        when(bookingRepository.save(any())).thenReturn(null);
    }
}