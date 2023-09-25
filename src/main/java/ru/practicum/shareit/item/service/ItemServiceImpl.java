package ru.practicum.shareit.item.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import ru.practicum.shareit.booking.dto.BookingMapper;
import ru.practicum.shareit.booking.dto.BookingShortDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.repo.BookingRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repo.UserRepository;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemMapper;
import ru.practicum.shareit.item.repo.ItemRepository;

import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.util.exeptions.ServiceException;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static ru.practicum.shareit.util.exeptions.ErrorMessage.*;

@Service
@Transactional(readOnly=true)
@AllArgsConstructor
public class ItemServiceImpl implements ItemService {

    private ItemRepository itemRepository;
    private UserRepository userRepository;
    private BookingRepository bookingRepository;

    @Override
    public List<ItemDto> getAllByUserId(Long userId) {
        Optional<User> optional = userRepository.findById(userId);
        if (optional.isEmpty()) throw new ServiceException(REPOSITORY_ERROR__USER__ID_NOT_IN_REPO__ID);
        return optional.get().getItems().stream()
                .map(ItemMapper::mapperItemToDto)
                .peek(item -> {

                    List<Booking> bookingLastList = bookingRepository.findLastBookingToItem(item.getId(), LocalDateTime.now());
                    if (!bookingLastList.isEmpty()) item.setLastBooking(BookingMapper.mapperBookingToShortDto(bookingLastList.get(0)));

                    List<Booking> bookingNextList = bookingRepository.findNextActualBookingToItem(item.getId(), LocalDateTime.now());
                    if (!bookingNextList.isEmpty()) item.setNextBooking(BookingMapper.mapperBookingToShortDto(bookingNextList.get(0)));
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<ItemDto> getBySearchText(String text) {
        if (text.isEmpty()) return new ArrayList<>();
        return itemRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseAndAvailable(text, text, true).stream()
                .map(ItemMapper::mapperItemToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ItemDto getById(Long userId, Long itemId) {
        Optional<Item> optional = itemRepository.findById(itemId);
        if (optional.isEmpty()) throw new ServiceException(REPOSITORY_ERROR__ITEM__ID_NOT_IN_REPO__ID);
        ItemDto itemDto = ItemMapper.mapperItemToDto(optional.get());

        if (optional.get().getOwner().getId().equals(userId)) {

            List<Booking> bookingLastList = bookingRepository.findLastBookingToItem(itemId, LocalDateTime.now());
            if (!bookingLastList.isEmpty()) itemDto.setLastBooking(BookingMapper.mapperBookingToShortDto(bookingLastList.get(0)));

            List<Booking> bookingNextList = bookingRepository.findNextActualBookingToItem(itemId, LocalDateTime.now());
            if (!bookingNextList.isEmpty()) itemDto.setNextBooking(BookingMapper.mapperBookingToShortDto(bookingNextList.get(0)));
        }
        return itemDto;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public ItemDto add(Long userId, ItemDto itemDto) {
        Item item = ItemMapper.mapperItemDtoToItem(itemDto);
        Optional<User> optional = userRepository.findById(userId);
        if (optional.isEmpty()) throw new ServiceException(REPOSITORY_ERROR__USER__ID_NOT_IN_REPO__ID);
        item.setOwner(optional.get());
        item = itemRepository.save(item);
        return ItemMapper.mapperItemToDto(item);
    }

    @Transactional
    @Override
    public ItemDto update(Long userId, ItemDto itemDto) {
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
