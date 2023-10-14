package ru.practicum.shareit.booking.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import ru.practicum.shareit.booking.dto.BookingRequestDto;
import ru.practicum.shareit.booking.dto.BookingResponseDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.repo.BookingRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repo.ItemRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repo.UserRepository;
import ru.practicum.shareit.util.EnumBookingState;
import ru.practicum.shareit.util.exeptions.CustomException;
import ru.practicum.shareit.util.exeptions.ServiceException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static ru.practicum.shareit.util.exeptions.ErrorMessage.*;

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

    private final User user_1 = User.builder().id(1L).name("user-1").email("mail1").build();
    private final User user_2 = User.builder().id(2L).name("user-2").email("mail2").build();
    private final User user_3 = User.builder().id(3L).name("user-3").email("mail3").build();
    private final Item item_1 = Item.builder().id(1L).name("item").description("description").available(true).user(user_1).build();
    private final Item item_2 = Item.builder().id(1L).name("item").description("description").available(false).user(user_1).build();
    private final Booking booking_1 = Booking.builder()
            .id(1L)
            .user(user_2)
            .item(item_1)
            .approved(EnumBookingState.WAITING)
            .start(LocalDateTime.of(2023, 10, 20, 10, 0))
            .end(LocalDateTime.of(2023, 10, 20, 16, 0))
            .build();

    private final Booking booking_2 = Booking.builder()
            .id(2L)
            .user(user_2)
            .item(item_1)
            .approved(EnumBookingState.WAITING)
            .start(LocalDateTime.of(2023, 10, 20, 10, 0))
            .end(LocalDateTime.of(2023, 10, 20, 16, 0))
            .build();

    private final Booking booking_3 = Booking.builder()
            .id(3L)
            .user(user_2)
            .item(item_1)
            .approved(EnumBookingState.APPROVED)
            .start(LocalDateTime.of(2023, 10, 20, 10, 0))
            .end(LocalDateTime.of(2023, 10, 20, 16, 0))
            .build();

    private final Booking booking_4 = Booking.builder()
            .id(4L)
            .item(item_2)
            .user(user_1)
            .approved(EnumBookingState.APPROVED)
            .start(LocalDateTime.of(2023, 10, 20, 10, 0))
            .end(LocalDateTime.of(2023, 10, 20, 16, 0))
            .build();

    @Test
    @DisplayName("Тестирование метода - service.getAll")
    public void getAll__Valid_Param() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user_1));
        when(bookingRepository.findByUserOrderByStartDesc(any(), any())).thenReturn(Page.empty());

        List<BookingResponseDto> result = service.getAll(1L, "ALL", PageRequest.of(2, 2));
        Assertions.assertNotNull(result);
    }

    @Test
    @DisplayName("Тестирование метода - service.getAll : not valid param userId")
    public void getAll__Fail_Valid_Param_UserId() {
        when(userRepository.findById(anyLong()))
                .thenThrow(new ServiceException(REPOSITORY_ERROR__USER__ID_NOT_IN_REPO__ID));

        final ServiceException exception = Assertions.assertThrows(
                ServiceException.class,
                () -> service.getAll(1L, "ALL", PageRequest.of(2, 2)));

        Assertions.assertEquals(REPOSITORY_ERROR__USER__ID_NOT_IN_REPO__ID.getDescription(), exception.getMessage());
    }

    @Test
    @DisplayName("Тестирование метода - service.getAll : not valid param state")
    public void getAll__Fail_Valid_Param_State() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user_1));

        final CustomException exception = Assertions.assertThrows(
                CustomException.class,
                () -> service.getAll(1L, "FAIL", PageRequest.of(2, 2)));

        Assertions.assertEquals("Unknown state: FAIL", exception.getMessage());
    }

    @Test
    @DisplayName("Тестирование метода - service.getAllInItemOwner")
    public void getAllInItemOwner__Valid_Param() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user_1));
        when(bookingRepository.findByBookingUser(anyLong(), any())).thenReturn(Page.empty());

        List<BookingResponseDto> result = service.getAllInItemOwner(1L, "ALL", PageRequest.of(2, 2));
        Assertions.assertNotNull(result);
    }

    @Test
    @DisplayName("Тестирование метода - service.getAllInItemOwner : not valid param userId")
    public void getAllInItemOwner__Fail_Valid_Param_UserId() {
        when(userRepository.findById(anyLong()))
                .thenThrow(new ServiceException(REPOSITORY_ERROR__USER__ID_NOT_IN_REPO__ID));

        final ServiceException exception = Assertions.assertThrows(
                ServiceException.class,
                () -> service.getAllInItemOwner(1L, "ALL", PageRequest.of(2, 2)));

        Assertions.assertEquals(REPOSITORY_ERROR__USER__ID_NOT_IN_REPO__ID.getDescription(), exception.getMessage());
    }

    @Test
    @DisplayName("Тестирование метода - service.getAllInItemOwner : not valid param state")
    public void getAllInItemOwner__Fail_Valid_Param_State() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user_1));

        final CustomException exception = Assertions.assertThrows(
                CustomException.class,
                () -> service.getAllInItemOwner(1L, "FAIL", PageRequest.of(2, 2)));

        Assertions.assertEquals("Unknown state: FAIL", exception.getMessage());
    }

    @Test
    @DisplayName("Тестирование метода - service.getById")
    public void getById__Valid_Param() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user_3));
        when(bookingRepository.findById(anyLong())).thenReturn(Optional.of(booking_1));
        BookingResponseDto result = service.getById(2L, 1L);
        Assertions.assertNotNull(result);
    }

    @Test
    @DisplayName("Тестирование метода - service.getById : not valid param userId")
    public void getById__Fail_Valid_Param_UserId() {
        when(userRepository.findById(anyLong()))
                .thenThrow(new ServiceException(REPOSITORY_ERROR__USER__ID_NOT_IN_REPO__ID));

        final ServiceException exception = Assertions.assertThrows(
                ServiceException.class,
                () -> service.getById(1L, 1L));

        Assertions.assertEquals(REPOSITORY_ERROR__USER__ID_NOT_IN_REPO__ID.getDescription(), exception.getMessage());
    }

    @Test
    @DisplayName("Тестирование метода - service.getById : not valid param bookingId")
    public void getById__Fail_Valid_Param_BookingId() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user_1));
        when(bookingRepository.findById(anyLong()))
                .thenThrow(new ServiceException(REPOSITORY_ERROR__BOOKING__ID_NOT_IN_REPO__ID));

        final ServiceException exception = Assertions.assertThrows(
                ServiceException.class,
                () -> service.getById(1L, 1L));

        Assertions.assertEquals(REPOSITORY_ERROR__BOOKING__ID_NOT_IN_REPO__ID.getDescription(), exception.getMessage());
    }

    @Test
    @DisplayName("Тестирование метода - service.getById : fail user not owner booking")
    public void getById__Fail_User_Not_Owner_Booking() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user_3));
        when(bookingRepository.findById(anyLong())).thenReturn(Optional.of(booking_1));

        final ServiceException exception = Assertions.assertThrows(
                ServiceException.class,
                () -> service.getById(3L, 2L));

        Assertions.assertEquals(BOOKING_ERROR__USER_NOT_OWNER_ITEM.getDescription(), exception.getMessage());
    }

    @Test
    @DisplayName("Тестирование метода - service.save")
    public void save__Valid_Param() {

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user_2));
        when(itemRepository.findById(anyLong())).thenReturn(Optional.of(item_1));
        when(bookingRepository.save(any())).thenReturn(booking_4);

        BookingResponseDto result = service.save(2L, BookingRequestDto.builder()
                .itemId(1L)
                .start(LocalDateTime.of(2023, 10, 20, 10, 0))
                .end(LocalDateTime.of(2023, 10, 20, 16, 0))
                .build());

        Assertions.assertNotNull(result);
    }

    @Test
    @DisplayName("Тестирование метода - service.save : not valid param userId")
    public void save__Fail_Valid_Param_UserIdId() {
        when(userRepository.findById(anyLong()))
                .thenThrow(new ServiceException(REPOSITORY_ERROR__USER__ID_NOT_IN_REPO__ID));

        final ServiceException exception = Assertions.assertThrows(
                ServiceException.class,
                () -> service.save(1L, BookingRequestDto.builder().build()));

        Assertions.assertEquals(REPOSITORY_ERROR__USER__ID_NOT_IN_REPO__ID.getDescription(), exception.getMessage());
    }

    @Test
    @DisplayName("Тестирование метода - service.save : not valid param itemId")
    public void save__Fail_Valid_Param_ItemId() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user_1));
        when(itemRepository.findById(anyLong()))
                .thenThrow(new ServiceException(REPOSITORY_ERROR__ITEM__ID_NOT_IN_REPO__ID));

        final ServiceException exception = Assertions.assertThrows(
                ServiceException.class,
                () -> service.save(1L, BookingRequestDto.builder().itemId(1L).build()));

        Assertions.assertEquals(REPOSITORY_ERROR__ITEM__ID_NOT_IN_REPO__ID.getDescription(), exception.getMessage());
    }

    @Test
    @DisplayName("Тестирование метода - service.save : not valid booking owner id")
    public void save__Fail_Valid_Param_Booking_Not_Owner_Id() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user_1));
        when(itemRepository.findById(anyLong())).thenReturn(Optional.of(item_1));

        final ServiceException exception = Assertions.assertThrows(
                ServiceException.class,
                () -> service.save(1L, BookingRequestDto.builder().itemId(1L).build()));

        Assertions.assertEquals(BOOKING_ERROR__FAIL_PARAM_BOOKING__OWNER_ID.getDescription(), exception.getMessage());
    }

    @Test
    @DisplayName("Тестирование метода - service.save : fail booking available")
    public void save__Fail_Booking_Available() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user_2));
        when(itemRepository.findById(anyLong())).thenReturn(Optional.of(item_2));

        final ServiceException exception = Assertions.assertThrows(
                ServiceException.class,
                () -> service.save(2L, BookingRequestDto.builder().itemId(1L).build()));

        Assertions.assertEquals(BOOKING_ERROR__AVAILABLE_FALSE.getDescription(), exception.getMessage());
    }

    @Test
    @DisplayName("Тестирование метода - service.save : not valid datetime")
    public void save__Fail_Valid_Datetime() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user_2));
        when(itemRepository.findById(anyLong())).thenReturn(Optional.of(item_1));

        final ServiceException exception = Assertions.assertThrows(
                ServiceException.class,
                () -> service.save(2L, BookingRequestDto.builder()
                        .itemId(1L)
                        .start(LocalDateTime.of(2023, 10, 20, 10, 0))
                        .end(LocalDateTime.of(2023, 10, 20, 10, 0))
                        .build()));

        Assertions.assertEquals(BOOKING_ERROR__VALID_DATETIME.getDescription(), exception.getMessage());
    }

    @Test
    @DisplayName("Тестирование метода - service.save : not valid datetime start time")
    public void save__Fail_Valid_Datetime_Start_Time() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user_2));
        when(itemRepository.findById(anyLong())).thenReturn(Optional.of(item_1));

        final ServiceException exception = Assertions.assertThrows(
                ServiceException.class,
                () -> service.save(2L, BookingRequestDto.builder()
                        .itemId(1L)
                        .start(LocalDateTime.of(2020, 10, 20, 10, 0))
                        .end(LocalDateTime.of(2023, 10, 20, 10, 0))
                        .build()));

        Assertions.assertEquals(BOOKING_ERROR__VALID_DATETIME__START_TIME.getDescription(), exception.getMessage());
    }

    @Test
    @DisplayName("Тестирование метода - service.updateApproved")
    public void updateApproved__Valid_Param() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user_1));
        when(bookingRepository.findById(anyLong())).thenReturn(Optional.of(booking_2));
        when(bookingRepository.save(any())).thenReturn(booking_2);

        BookingResponseDto result = service.updateApproved(1L, 2L, true);
        Assertions.assertNotNull(result);
    }

    @Test
    @DisplayName("Тестирование метода - service.updateApproved : not valid param userId")
    public void updateApproved__Fail_Valid_Param_UserId() {
        when(userRepository.findById(anyLong()))
                .thenThrow(new ServiceException(REPOSITORY_ERROR__USER__ID_NOT_IN_REPO__ID));

        final ServiceException exception = Assertions.assertThrows(
                ServiceException.class,
                () -> service.updateApproved(1L, 1L, true));

        Assertions.assertEquals(REPOSITORY_ERROR__USER__ID_NOT_IN_REPO__ID.getDescription(), exception.getMessage());
    }

    @Test
    @DisplayName("Тестирование метода - service.updateApproved : not valid param itemId")
    public void updateApproved__Fail_Valid_Param_ItemId() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(User.builder().build()));
        when(bookingRepository.findById(anyLong())).thenThrow(new ServiceException(REPOSITORY_ERROR__BOOKING__ID_NOT_IN_REPO__ID));

        final ServiceException exception = Assertions.assertThrows(
                ServiceException.class,
                () -> service.updateApproved(1L, 1L, true));

        Assertions.assertEquals(REPOSITORY_ERROR__BOOKING__ID_NOT_IN_REPO__ID.getDescription(), exception.getMessage());
    }

    @Test
    @DisplayName("Тестирование метода - service.updateApproved : fail block save booking user")
    public void updateApproved__Fail_fail_Block_Save_Booking_User() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user_2));
        when(bookingRepository.findById(anyLong())).thenReturn(Optional.of(booking_1));

        final ServiceException exception = Assertions.assertThrows(
                ServiceException.class,
                () -> service.updateApproved(2L, 1L, true));

        Assertions.assertEquals(BOOKING_ERROR__BLOCK_SAVE_BOOKING__USER.getDescription(), exception.getMessage());
    }

    @Test
    @DisplayName("Тестирование метода - service.updateApproved : fail block save booking user not in booking")
    public void updateApproved__Fail_fail_Block_Save_Booking_User_Not_In_Booking() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user_3));
        when(bookingRepository.findById(anyLong())).thenReturn(Optional.of(booking_2));

        final ServiceException exception = Assertions.assertThrows(
                ServiceException.class,
                () -> service.updateApproved(3L, 2L, true));

        Assertions.assertEquals(BOOKING_ERROR__BLOCK_SAVE_BOOKING__USER_NOT_IN_BOOKING.getDescription(), exception.getMessage());
    }

    @Test
    @DisplayName("Тестирование метода - service.updateApproved : fail block save booking approve")
    public void updateApproved__Fail_fail_Block_Save_Booking_Approve() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user_3));
        when(bookingRepository.findById(anyLong())).thenReturn(Optional.of(booking_3));

        final ServiceException exception = Assertions.assertThrows(
                ServiceException.class,
                () -> service.updateApproved(1L, 3L, true));

        Assertions.assertEquals(BOOKING_ERROR__BLOCK_SAVE_BOOKING__BOOKING__APPROVE.getDescription(), exception.getMessage());
    }
}