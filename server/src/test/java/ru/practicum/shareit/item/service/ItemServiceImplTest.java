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
import ru.practicum.shareit.booking.util.BookingState;
import ru.practicum.shareit.item.util.MapperItem;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repo.CommentRepository;
import ru.practicum.shareit.item.repo.ItemRepository;
import ru.practicum.shareit.request.repo.ItemRequestRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repo.UserRepository;
import ru.practicum.shareit.util.exeptions.RepositoryException;
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

    private final User user = User.builder().id(1L).name("user-1").email("mail1").build();
    private final Item item = Item.builder().id(1L).name("item").description("description").available(true).user(user).build();
    private final Booking booking = Booking.builder()
            .item(item)
            .user(user)
            .approved(BookingState.APPROVED)
            .start(LocalDateTime.of(2023, 8, 1, 10, 0))
            .end(LocalDateTime.of(2023, 10, 1, 10, 0))
            .build();
    private final Comment comment = Comment.builder().text("text").item(item).user(user).created(LocalDateTime.now()).build();

    @Test
    @DisplayName("Тестирование метода - service.getAllByUserId : valid id")
    public void getAllByUserId__Valid_Param_Id() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(itemRepository.findByUser(any())).thenReturn(List.of(item));
        when(bookingRepository.findByItem_User(any())).thenReturn(new ArrayList<>());

        List<ItemDto> result = service.getAllByUserId(1L);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.size(), 1);
        Assertions.assertEquals(MapperItem.mapperEntityToDto(item), result.get(0));
    }

    @Test
    @DisplayName("Тестирование метода - service.getAllByUserId : fail valid id")
    public void getAllByUserId__Fail_Valid_Param_Id() {
        when(userRepository.findById(anyLong()))
                .thenThrow(new RepositoryException(REPOSITORY_ERROR__USER__ID_NOT_IN_REPO__ID));

        final RepositoryException exception = Assertions.assertThrows(
                RepositoryException.class,
                () -> service.getAllByUserId(1L));

        Assertions.assertEquals(REPOSITORY_ERROR__USER__ID_NOT_IN_REPO__ID.getDescription(), exception.getMessage());
    }

    @Test
    @DisplayName("Тестирование метода - service.getById")
    public void getById__Valid_Param() {
        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        when(itemRepository.findById(any())).thenReturn(Optional.of(item));

        ItemDto result = service.getById(1L, 1L);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(MapperItem.mapperEntityToDto(item), result);
    }

    @Test
    @DisplayName("Тестирование метода - service.getById : fail valid userId")
    public void getById__Fail_Valid_Param_UserId() {
        when(userRepository.findById(any())).thenThrow(new RepositoryException(REPOSITORY_ERROR__USER__ID_NOT_IN_REPO__ID));

        final RepositoryException exception = Assertions.assertThrows(
                RepositoryException.class,
                () -> service.getById(1L, 1L));

        Assertions.assertEquals(REPOSITORY_ERROR__USER__ID_NOT_IN_REPO__ID.getDescription(), exception.getMessage());
    }

    @Test
    @DisplayName("Тестирование метода - service.getById : fail valid itemId")
    public void getById__Fail_Valid_Param_ItemId() {
        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        when(itemRepository.findById(any())).thenThrow(new RepositoryException(REPOSITORY_ERROR__USER__ID_NOT_IN_REPO__ID));

        final RepositoryException exception = Assertions.assertThrows(
                RepositoryException.class,
                () -> service.getById(1L, 1L));

        Assertions.assertEquals(REPOSITORY_ERROR__USER__ID_NOT_IN_REPO__ID.getDescription(), exception.getMessage());
    }

    @Test
    @DisplayName("Тестирование метода - service.getBySearchText : valid param")
    public void getBySearchText__Valid_Param() {
        when(itemRepository.findSearch(anyString())).thenReturn(List.of(item, item));
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
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(itemRepository.save(any())).thenReturn(item);
        ItemDto result = service.saveItem(1L, ItemDto.builder().build());
        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getName(), item.getName());
    }

    @Test
    @DisplayName("Тестирование метода - service.saveItem : not valid param")
    public void saveItem__Fail_Valid_Param_UserId() {
        when(userRepository.findById(anyLong())).thenThrow(new RepositoryException(REPOSITORY_ERROR__USER__ID_NOT_IN_REPO__ID));

        final RepositoryException exception = Assertions.assertThrows(
                RepositoryException.class,
                () -> service.saveItem(1L, MapperItem.mapperEntityToDto(item)));

        Assertions.assertEquals(REPOSITORY_ERROR__USER__ID_NOT_IN_REPO__ID.getDescription(), exception.getMessage());
    }

    @Test
    @DisplayName("Тестирование метода - service.saveComment")
    public void saveComment__Valid_Param() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(itemRepository.findById(anyLong())).thenReturn(Optional.of(item));
        when(bookingRepository.findFirstBookingByUserAndItemOrderByStartAsc(any(), any())).thenReturn(Optional.of(booking));
        when(commentRepository.save(any())).thenReturn(comment);

        CommentDto result = service.saveComment(1L, 1L, CommentDto.builder().text("text").build());
        Assertions.assertNotNull(result);
    }

    @Test
    @DisplayName("Тестирование метода - service.saveComment : not valid param userId")
    public void saveComment__Fail_Valid_Param_UserId() {
        when(userRepository.findById(anyLong())).thenThrow(new RepositoryException(REPOSITORY_ERROR__USER__ID_NOT_IN_REPO__ID));

        final RepositoryException exception = Assertions.assertThrows(
                RepositoryException.class,
                () -> service.saveComment(1L, 1L, CommentDto.builder().build()));

        Assertions.assertEquals(REPOSITORY_ERROR__USER__ID_NOT_IN_REPO__ID.getDescription(), exception.getMessage());
    }

    @Test
    @DisplayName("Тестирование метода - service.saveComment : not valid param itemId")
    public void saveComment__Fail_Valid_Param_ItemId() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(itemRepository.findById(anyLong())).thenThrow(new RepositoryException(REPOSITORY_ERROR__ITEM__ID_NOT_IN_REPO__ID));

        final RepositoryException exception = Assertions.assertThrows(
                RepositoryException.class,
                () -> service.saveComment(1L, 1L, CommentDto.builder().build()));

        Assertions.assertEquals(REPOSITORY_ERROR__ITEM__ID_NOT_IN_REPO__ID.getDescription(), exception.getMessage());
    }

    @Test
    @DisplayName("Тестирование метода - service.saveComment : empty booking")
    public void saveComment__Fail_Empty_Booking() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(itemRepository.findById(anyLong())).thenReturn(Optional.of(item));
        when(bookingRepository.findFirstBookingByUserAndItemOrderByStartAsc(any(), any())).thenThrow(new RepositoryException(BOOKING_ERROR__NOT_BOOKINGS_IN_REPOSITORY));

        final RepositoryException exception = Assertions.assertThrows(
                RepositoryException.class,
                () -> service.saveComment(1L, 1L, CommentDto.builder().build()));

        Assertions.assertEquals(BOOKING_ERROR__NOT_BOOKINGS_IN_REPOSITORY.getDescription(), exception.getMessage());
    }

    @Test
    @DisplayName("Тестирование метода - service.saveComment : fail save comment")
    public void saveComment__Fail_Block_Save_Comment() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(itemRepository.findById(anyLong())).thenReturn(Optional.of(item));
        when(bookingRepository.findFirstBookingByUserAndItemOrderByStartAsc(any(), any()))
                .thenReturn(Optional.of(Booking.builder().end(LocalDateTime.of(2024, 1, 1, 10, 0)).build()));

        final ServiceException exception = Assertions.assertThrows(
                ServiceException.class,
                () -> service.saveComment(1L, 1L, CommentDto.builder().build()));

        Assertions.assertEquals(BOOKING_ERROR__BLOCK_SAVE_BOOKING__DATETIME.getDescription(), exception.getMessage());
    }

    @Test
    @DisplayName("Тестирование метода - service.updateItem")
    public void updateItem__Valid_Param() {

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(itemRepository.findById(anyLong())).thenReturn(Optional.of(item));
        when(itemRepository.save(any())).thenReturn(item);

        ItemDto result = service.updateItem(1L, ItemDto.builder().id(1L).build());
        Assertions.assertNotNull(result);
    }

    @Test
    @DisplayName("Тестирование метода - service.updateItem : not valid param userId")
    public void updateItem__Fail_Valid_Param_UserId() {
        when(userRepository.findById(anyLong()))
                .thenThrow(new RepositoryException(REPOSITORY_ERROR__USER__ID_NOT_IN_REPO__ID));

        final RepositoryException exception = Assertions.assertThrows(
                RepositoryException.class,
                () -> service.updateItem(1L, ItemDto.builder().build()));

        Assertions.assertEquals(REPOSITORY_ERROR__USER__ID_NOT_IN_REPO__ID.getDescription(), exception.getMessage());
    }

    @Test
    @DisplayName("Тестирование метода - service.updateItem : not valid param itemId")
    public void updateItem__Fail_Valid_Param_ItemId() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(itemRepository.findById(anyLong()))
                .thenThrow(new RepositoryException(REPOSITORY_ERROR__ITEM__ID_NOT_IN_REPO__ID));

        final RepositoryException exception = Assertions.assertThrows(
                RepositoryException.class,
                () -> service.updateItem(1L, ItemDto.builder().id(1L).build()));

        Assertions.assertEquals(REPOSITORY_ERROR__ITEM__ID_NOT_IN_REPO__ID.getDescription(), exception.getMessage());
    }
}