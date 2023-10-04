package ru.practicum.shareit.item.service;

import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dto.BookingMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.repo.BookingRepository;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.CommentMapper;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repo.CommentRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repo.UserRepository;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemMapper;
import ru.practicum.shareit.item.repo.ItemRepository;

import ru.practicum.shareit.util.exeptions.ServiceException;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static ru.practicum.shareit.util.exeptions.ErrorMessage.*;

@Service
@AllArgsConstructor
public class ItemServiceImpl implements ItemService {

    private ItemRepository itemRepository;
    private UserRepository userRepository;
    private BookingRepository bookingRepository;
    private CommentRepository commentRepository;

    @Override
    public List<ItemDto> getAllByUserId(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) throw new ServiceException(REPOSITORY_ERROR__USER__ID_NOT_IN_REPO__ID);

        List<ItemDto> result = new ArrayList<>();

        itemRepository.findByOwner_id(optionalUser.get().getId())
                .forEach(item -> {

                    ItemDto itemDto = ItemMapper.mapperItemToDto(item);

                    List<Booking> bookingLastList = bookingRepository.findListToLastBooking(item.getId(), LocalDateTime.now());
                    if (!bookingLastList.isEmpty()) itemDto.setLastBooking(BookingMapper.mapperBookingToShortDto(bookingLastList.get(0)));

                    List<Booking> bookingNextList = bookingRepository.findListToNextBooking(item.getId(), LocalDateTime.now());
                    if (!bookingNextList.isEmpty()) itemDto.setNextBooking(BookingMapper.mapperBookingToShortDto(bookingNextList.get(0)));

                    result.add(itemDto);
                });

        return result;
    }

    @Override
    public ItemDto getById(Long userId, Long itemId) {
        Optional<Item> optionalItem = itemRepository.findById(itemId);
        if (optionalItem.isEmpty()) throw new ServiceException(REPOSITORY_ERROR__ITEM__ID_NOT_IN_REPO__ID);
        ItemDto itemDto = ItemMapper.mapperItemToDto(optionalItem.get());

        if (optionalItem.get().getOwner().getId().equals(userId)) {

            List<Booking> bookingLastList = bookingRepository.findListToLastBooking(optionalItem.get().getId(), LocalDateTime.now());
            if (!bookingLastList.isEmpty()) itemDto.setLastBooking(BookingMapper.mapperBookingToShortDto(bookingLastList.get(0)));

            List<Booking> bookingNextList = bookingRepository.findListToNextBooking(optionalItem.get().getId(), LocalDateTime.now());
            if (!bookingNextList.isEmpty()) itemDto.setNextBooking(BookingMapper.mapperBookingToShortDto(bookingNextList.get(0)));
        }
        return itemDto;
    }

    @Override
    public List<ItemDto> getBySearchText(@NotNull String text) {
        if (text.isEmpty()) return new ArrayList<>();
        return itemRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseAndAvailable(text, text, true).stream()
                .map(ItemMapper::mapperItemToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ItemDto add(Long userId, ItemDto itemDto) {
        Item item = ItemMapper.mapperItemDtoToItem(itemDto);
        Optional<User> optional = userRepository.findById(userId);
        if (optional.isEmpty()) throw new ServiceException(REPOSITORY_ERROR__USER__ID_NOT_IN_REPO__ID);
        item.setOwner(optional.get());
        item = itemRepository.save(item);
        return ItemMapper.mapperItemToDto(item);
    }

    @Override
    public CommentDto addComment(Long userId, Long itemId, CommentDto commentDto) {

        Optional<Item> optionalItem = itemRepository.findById(itemId);
        if (optionalItem.isEmpty()) throw new ServiceException(REPOSITORY_ERROR__ITEM__ID_NOT_IN_REPO__ID);

        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) throw new ServiceException(REPOSITORY_ERROR__USER__ID_NOT_IN_REPO__ID);

        Optional<Booking> optionalBooking = bookingRepository.findFirstBookingByOwnerAndItemOrderByStartAsc(optionalUser.get(), optionalItem.get());

        if (optionalBooking.isEmpty()) throw new ServiceException("Пользователь не совершал бронирований этой вещи", 404);
        if (optionalBooking.get().getEnd().isAfter(LocalDateTime.now())) throw new ServiceException("Нельзя оставлять отзыв до завершения бронирования", 400);

        Comment comment = CommentMapper.mapperCommentDtoToComment(commentDto);
        comment.setItem(optionalItem.get());
        comment.setUser(optionalUser.get());
        comment.setCreated(LocalDateTime.now());

        comment = commentRepository.save(comment);
        return CommentMapper.mapperCommentToDto(comment);
    }

    @Override
    public ItemDto update(Long userId, @NotNull ItemDto itemDto) {
        Optional<User> optionalUser = userRepository.findById(userId);
        Optional<Item> optionalItem = itemRepository.findById(itemDto.getId());

        if (optionalUser.isEmpty()) throw new ServiceException(REPOSITORY_ERROR__USER__ID_NOT_IN_REPO__ID);
        if (optionalItem.isEmpty()) throw new ServiceException(REPOSITORY_ERROR__ITEM__ID_NOT_IN_REPO__ID);

        User user = optionalUser.get();
        Item item = optionalItem.get();

        if (!item.getOwner().getId().equals(user.getId())) throw new ServiceException(ITEM_ERROR__VALID__ITEM__USER_NOT_OWNER);

        if (itemDto.getName() != null && !itemDto.getName().equals(item.getName())) item.setName(itemDto.getName());
        if (itemDto.getDescription() != null && !itemDto.getDescription().equals(item.getDescription())) item.setDescription(itemDto.getDescription());
        if (itemDto.getAvailable() != null) item.setAvailable(itemDto.getAvailable());

        Item result = itemRepository.save(item);
        return ItemMapper.mapperItemToDto(result);
    }
}
