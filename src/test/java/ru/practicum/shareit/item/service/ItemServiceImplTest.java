package ru.practicum.shareit.item.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.repo.BookingRepository;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repo.CommentRepository;
import ru.practicum.shareit.item.repo.ItemRepository;
import ru.practicum.shareit.request.repo.ItemRequestRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repo.UserRepository;
import ru.practicum.shareit.util.exeptions.ServiceException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static ru.practicum.shareit.util.exeptions.ErrorMessage.*;

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
    private ItemServiceImpl service;

    private final User user_1 = User.builder().id(1L).name("user-1").email("mail1").build();
    private final Item item_1 = Item.builder().id(1L).name("item").description("description").available(true).user(user_1).build();

    @Test
    @DisplayName("Тестирование метода - service.getAllByUserId : valid id")
    public void getAllByUserId__Valid_Param_Id() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user_1));
        when(itemRepository.findByUser(any())).thenReturn(List.of(item_1));
        when(bookingRepository.findByItem_User(any())).thenReturn(new ArrayList<>());

        List<ItemDto> result = service.getAllByUserId(1L);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.size(), 1);
        Assertions.assertEquals(ItemMapper.mapperItemToDto(item_1), result.get(0));
    }

    @Test
    @DisplayName("Тестирование метода - service.getAllByUserId : fail valid id")
    public void getAllByUserId__Fail_Valid_Param_Id() {
        when(userRepository.findById(anyLong()))
                .thenThrow(new ServiceException(REPOSITORY_ERROR__USER__ID_NOT_IN_REPO__ID));

        final ServiceException exception = Assertions.assertThrows(
                ServiceException.class,
                () -> service.getAllByUserId(1L));

        Assertions.assertEquals(REPOSITORY_ERROR__USER__ID_NOT_IN_REPO__ID.getDescription(), exception.getMessage());
    }

    @Test
    @DisplayName("Тестирование метода - service.getById")
    public void getById__Valid_Param() {
        when(userRepository.findById(any())).thenReturn(Optional.of(user_1));
        when(itemRepository.findById(any())).thenReturn(Optional.of(item_1));

        ItemDto result = service.getById(1L, 1L);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(ItemMapper.mapperItemToDto(item_1), result);
    }

    @Test
    @DisplayName("Тестирование метода - service.getById : fail valid userId")
    public void getById__Fail_Valid_Param_UserId() {
        when(userRepository.findById(any())).thenThrow(new ServiceException(REPOSITORY_ERROR__USER__ID_NOT_IN_REPO__ID));

        final ServiceException exception = Assertions.assertThrows(
                ServiceException.class,
                () -> service.getById(1L, 1L));

        Assertions.assertEquals(REPOSITORY_ERROR__USER__ID_NOT_IN_REPO__ID.getDescription(), exception.getMessage());
    }

    @Test
    @DisplayName("Тестирование метода - service.getById : fail valid itemId")
    public void getById__Fail_Valid_Param_ItemId() {
        when(userRepository.findById(any())).thenReturn(Optional.of(user_1));
        when(itemRepository.findById(any())).thenThrow(new ServiceException(REPOSITORY_ERROR__USER__ID_NOT_IN_REPO__ID));

        final ServiceException exception = Assertions.assertThrows(
                ServiceException.class,
                () -> service.getById(1L, 1L));

        Assertions.assertEquals(REPOSITORY_ERROR__USER__ID_NOT_IN_REPO__ID.getDescription(), exception.getMessage());
    }

    @Test
    @DisplayName("Тестирование метода - service.getBySearchText : valid param")
    public void getBySearchText__Valid_Param() {
        when(itemRepository.findSearch(anyString())).thenReturn(List.of(item_1, item_1));
        List<ItemDto> result = service.getBySearchText("item");
        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.size(), 2);
    }

    @Test
    @DisplayName("Тестирование метода - service.getBySearchText : not valid param")
    public void getBySearchText__Fail_Valid_Param_Text() {
        List<ItemDto> result = service.getBySearchText("");
        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.size(), 0);
    }

    @Test
    @DisplayName("Тестирование метода - service.saveItem")
    public void saveItem__Valid_Param() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user_1));
        when(itemRepository.save(any())).thenReturn(item_1);
        ItemDto result = service.saveItem(1L, ItemDto.builder().build());
        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getName(), item_1.getName());
    }

    @Test
    @DisplayName("Тестирование метода - service.saveItem : not valid param")
    public void saveItem__Fail_Valid_Param_UserId() {
        when(userRepository.findById(anyLong())).thenThrow(new ServiceException(REPOSITORY_ERROR__USER__ID_NOT_IN_REPO__ID));

        final ServiceException exception = Assertions.assertThrows(
                ServiceException.class,
                () -> service.saveItem(1L, ItemMapper.mapperItemToDto(item_1)));

        Assertions.assertEquals(REPOSITORY_ERROR__USER__ID_NOT_IN_REPO__ID.getDescription(), exception.getMessage());
    }

    @Test
    @DisplayName("Тестирование метода - service.saveComment")
    public void saveComment__Valid_Param() {
        when(itemRepository.findById(anyLong())).thenReturn(null);
        when(userRepository.findById(anyLong())).thenReturn(null);
        when(bookingRepository.findFirstBookingByUserAndItemOrderByStartAsc(any(), any())).thenReturn(null);
        when(commentRepository.save(any())).thenReturn(null);
    }

    @Test
    @DisplayName("Тестирование метода - service.saveComment : not valid param userId")
    public void saveComment__Fail_Valid_Param_UserId() {
        when(userRepository.findById(anyLong())).thenThrow(new ServiceException(REPOSITORY_ERROR__USER__ID_NOT_IN_REPO__ID));

        final ServiceException exception = Assertions.assertThrows(
                ServiceException.class,
                () -> service.saveComment(1L, 1L, CommentDto.builder().build()));

        Assertions.assertEquals(REPOSITORY_ERROR__USER__ID_NOT_IN_REPO__ID.getDescription(), exception.getMessage());
    }

    @Test
    @DisplayName("Тестирование метода - service.saveComment : not valid param itemId")
    public void saveComment__Fail_Valid_Param_ItemId() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user_1));
        when(itemRepository.findById(anyLong())).thenThrow(new ServiceException(REPOSITORY_ERROR__ITEM__ID_NOT_IN_REPO__ID));

        final ServiceException exception = Assertions.assertThrows(
                ServiceException.class,
                () -> service.saveComment(1L, 1L, CommentDto.builder().build()));

        Assertions.assertEquals(REPOSITORY_ERROR__ITEM__ID_NOT_IN_REPO__ID.getDescription(), exception.getMessage());
    }

    @Test
    @DisplayName("Тестирование метода - service.saveComment : empty booking")
    public void saveComment__Fail_Empty_Booking() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user_1));
        when(itemRepository.findById(anyLong())).thenReturn(Optional.of(item_1));
        when(bookingRepository.findFirstBookingByUserAndItemOrderByStartAsc(any(), any())).thenThrow(new ServiceException(BOOKING_ERROR__NOT_BOOKINGS_IN_REPOSITORY));

        final ServiceException exception = Assertions.assertThrows(
                ServiceException.class,
                () -> service.saveComment(1L, 1L, CommentDto.builder().build()));

        Assertions.assertEquals(BOOKING_ERROR__NOT_BOOKINGS_IN_REPOSITORY.getDescription(), exception.getMessage());
    }

    @Test
    @DisplayName("Тестирование метода - service.saveComment : fail save comment")
    public void saveComment__Fail_Block_Save_Comment() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user_1));
        when(itemRepository.findById(anyLong())).thenReturn(Optional.of(item_1));
        when(bookingRepository.findFirstBookingByUserAndItemOrderByStartAsc(any(), any()))
                .thenReturn(Optional.of(Booking.builder().end(LocalDateTime.of(2024, 1, 1, 10, 0)).build()));

        final ServiceException exception = Assertions.assertThrows(
                ServiceException.class,
                () -> service.saveComment(1L, 1L, CommentDto.builder().build()));

        Assertions.assertEquals(BOOKING_ERROR__BLOCK_SAVE_BOOKING.getDescription(), exception.getMessage());
    }

    @Test
    @DisplayName("Тестирование метода - service.updateItem")
    public void updateItem__Valid_Param() {
        when(userRepository.findById(anyLong())).thenReturn(null);
        when(itemRepository.findById(anyLong())).thenReturn(null);
        when(itemRepository.save(any())).thenReturn(null);
    }

    @Test
    @DisplayName("Тестирование метода - service.updateItem")
    public void updateItem__Fail_Valid_Param_UserId() {
        when(userRepository.findById(anyLong())).thenThrow(new ServiceException(REPOSITORY_ERROR__USER__ID_NOT_IN_REPO__ID));

        final ServiceException exception = Assertions.assertThrows(
                ServiceException.class,
                () -> service.updateItem(1L, ItemDto.builder().build()));

        Assertions.assertEquals(REPOSITORY_ERROR__USER__ID_NOT_IN_REPO__ID.getDescription(), exception.getMessage());
    }

    @Test
    @DisplayName("Тестирование метода - service.updateItem")
    public void updateItem__Fail_Valid_Param_ItemId() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user_1));
        when(itemRepository.findById(anyLong()))
                .thenThrow(new ServiceException(REPOSITORY_ERROR__ITEM__ID_NOT_IN_REPO__ID));

        final ServiceException exception = Assertions.assertThrows(
                ServiceException.class,
                () -> service.updateItem(1L, ItemDto.builder().id(1L).build()));

        Assertions.assertEquals(REPOSITORY_ERROR__ITEM__ID_NOT_IN_REPO__ID.getDescription(), exception.getMessage());
    }
}