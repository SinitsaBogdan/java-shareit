package ru.practicum.shareit.item.service;

import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.repo.BookingRepository;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.CommentMapper;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repo.CommentRepository;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.repo.ItemRequestRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repo.UserRepository;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemMapper;
import ru.practicum.shareit.item.repo.ItemRepository;

import ru.practicum.shareit.util.exeptions.ServiceException;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static ru.practicum.shareit.util.EnumBookingState.APPROVED;
import static ru.practicum.shareit.util.exeptions.ErrorMessage.*;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final ItemRequestRepository itemRequestRepository;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    private final CommentRepository commentRepository;

    @Override
    public List<ItemDto> getAllByUserId(long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) throw new ServiceException(REPOSITORY_ERROR__USER__ID_NOT_IN_REPO__ID);

        List<ItemDto> result = new ArrayList<>();
        LocalDateTime actual = LocalDateTime.now();
        List<Item> items = itemRepository.findByUser(optionalUser.get());
        List<Booking> bookings = bookingRepository.findByItem_User(optionalUser.get());

        items.forEach(item -> {

                    ItemDto itemDto = ItemMapper.mapperItemToDto(item);

                    Booking bookingNext = bookings.stream()
                            .filter(booking -> booking.getItem().getId().equals(item.getId()))
                            .filter(booking -> booking.getStart().isAfter(actual) || booking.getStart().equals(actual))
                            .filter(booking -> booking.getApproved().equals(APPROVED))
                            .min(Comparator.comparing(Booking::getStart))
                            .orElse(null);

                    Booking bookingLast = bookings.stream()
                            .filter(booking -> booking.getItem().getId().equals(item.getId()))
                            .filter(booking -> booking.getStart().isBefore(actual))
                            .filter(booking -> booking.getApproved().equals(APPROVED))
                            .min(Comparator.comparing(Booking::getEnd))
                            .orElse(null);

                    itemDto.setLastBooking(bookingLast != null ? new ItemDto.LocalBooker(bookingLast.getId(), bookingLast.getUser().getId()) : null);
                    itemDto.setNextBooking(bookingNext != null ? new ItemDto.LocalBooker(bookingNext.getId(), bookingNext.getUser().getId()) : null);

                    result.add(itemDto);
                });

        return result;
    }

    @Override
    public ItemDto getById(long userId, long itemId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) throw new ServiceException(REPOSITORY_ERROR__USER__ID_NOT_IN_REPO__ID);

        Optional<Item> optionalItem = itemRepository.findById(itemId);
        if (optionalItem.isEmpty()) throw new ServiceException(REPOSITORY_ERROR__ITEM__ID_NOT_IN_REPO__ID);
        ItemDto itemDto = ItemMapper.mapperItemToDto(optionalItem.get());

        if (optionalItem.get().getUser().getId().equals(userId)) {

            List<Booking> bookingLastList = bookingRepository.findListToLastBooking(optionalItem.get().getId(), LocalDateTime.now());
            if (!bookingLastList.isEmpty()) itemDto.setLastBooking(new ItemDto.LocalBooker(bookingLastList.get(0).getId(), bookingLastList.get(0).getUser().getId()));

            List<Booking> bookingNextList = bookingRepository.findListToNextBooking(optionalItem.get().getId(), LocalDateTime.now());
            if (!bookingNextList.isEmpty()) itemDto.setNextBooking(new ItemDto.LocalBooker(bookingNextList.get(0).getId(), bookingNextList.get(0).getUser().getId()));
        }
        return itemDto;
    }

    @Override
    public List<ItemDto> getBySearchText(@NotNull String text) {
        if (text.isEmpty()) return new ArrayList<>();
        else return itemRepository.findSearch(text).stream()
                .map(ItemMapper::mapperItemToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ItemDto saveItem(long userId, ItemDto itemDto) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) throw new ServiceException(REPOSITORY_ERROR__USER__ID_NOT_IN_REPO__ID);

        Item item = ItemMapper.mapperItemDtoToItem(itemDto);

        if (itemDto.getRequestId() != null) {
            Optional<ItemRequest> optionalItemRequest = itemRequestRepository.findById(itemDto.getRequestId());
            if (optionalItemRequest.isEmpty()) throw new ServiceException(REPOSITORY_ERROR__REQUEST__ID_NOT_IN_REPO__ID);
            item.setRequest(optionalItemRequest.get());
        }

        item.setUser(optionalUser.get());
        item = itemRepository.save(item);
        return ItemMapper.mapperItemToDto(item);
    }

    @Override
    @Transactional
    public CommentDto saveComment(long userId, long itemId, CommentDto commentDto) {

        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) throw new ServiceException(REPOSITORY_ERROR__USER__ID_NOT_IN_REPO__ID);

        Optional<Item> optionalItem = itemRepository.findById(itemId);
        if (optionalItem.isEmpty()) throw new ServiceException(REPOSITORY_ERROR__ITEM__ID_NOT_IN_REPO__ID);

        Optional<Booking> optionalBooking = bookingRepository.findFirstBookingByUserAndItemOrderByStartAsc(optionalUser.get(), optionalItem.get());

        if (optionalBooking.isEmpty()) throw new ServiceException(BOOKING_ERROR__NOT_BOOKINGS_IN_REPOSITORY);
        if (optionalBooking.get().getEnd().isAfter(LocalDateTime.now())) throw new ServiceException(BOOKING_ERROR__BLOCK_SAVE_BOOKING__DATETIME);

        Comment comment = CommentMapper.mapperCommentDtoToComment(commentDto);
        comment.setItem(optionalItem.get());
        comment.setUser(optionalUser.get());
        comment.setCreated(LocalDateTime.now());

        comment = commentRepository.save(comment);
        return CommentMapper.mapperCommentToDto(comment);
    }

    @Override
    @Transactional
    public ItemDto updateItem(long userId, @NotNull ItemDto itemDto) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) throw new ServiceException(REPOSITORY_ERROR__USER__ID_NOT_IN_REPO__ID);

        Optional<Item> optionalItem = itemRepository.findById(itemDto.getId());
        if (optionalItem.isEmpty()) throw new ServiceException(REPOSITORY_ERROR__ITEM__ID_NOT_IN_REPO__ID);

        User user = optionalUser.get();
        Item item = optionalItem.get();

        if (!item.getUser().getId().equals(user.getId())) throw new ServiceException(ITEM_ERROR__VALID__ITEM__USER_NOT_OWNER);

        if (itemDto.getName() != null && !itemDto.getName().equals(item.getName())) item.setName(itemDto.getName());
        if (itemDto.getDescription() != null && !itemDto.getDescription().equals(item.getDescription())) item.setDescription(itemDto.getDescription());
        if (itemDto.getAvailable() != null) item.setAvailable(itemDto.getAvailable());

        Item result = itemRepository.save(item);
        return ItemMapper.mapperItemToDto(result);
    }
}
