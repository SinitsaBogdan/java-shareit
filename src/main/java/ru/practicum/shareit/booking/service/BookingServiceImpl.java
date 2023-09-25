package ru.practicum.shareit.booking.service;

import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingMapper;
import ru.practicum.shareit.booking.dto.BookingResponseBookerDto;
import ru.practicum.shareit.booking.dto.BookingResponseOwnerDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.repo.BookingRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repo.ItemRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repo.UserRepository;
import ru.practicum.shareit.util.EnumBookingState;
import ru.practicum.shareit.util.exeptions.ServiceException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.practicum.shareit.util.EnumBookingState.*;
import static ru.practicum.shareit.util.exeptions.ErrorMessage.*;

@Service
@AllArgsConstructor
public class BookingServiceImpl implements BookingService {

    private BookingRepository bookingRepository;
    private UserRepository userRepository;
    private ItemRepository itemRepository;

    @Override
    public List<BookingResponseOwnerDto> getAll(Long userId, String state) {
        Optional<User> optional = userRepository.findById(userId);
        if (optional.isEmpty()) throw new ServiceException(REPOSITORY_ERROR__USER__ID_NOT_IN_REPO__ID);

        List<Booking> list;

        if      (ALL.name().equals(state)) list = bookingRepository.findByOwnerOrderByStartDesc(optional.get());
        else if (PAST.name().equals(state)) list = bookingRepository.findAllBookingStatePast(optional.get(), LocalDateTime.now());
        else if (FUTURE.name().equals(state)) list = bookingRepository.findByOwnerAndStartAfterOrderByStartDesc(optional.get(), LocalDateTime.now());
        else if (CURRENT.name().equals(state)) list = bookingRepository.findAllBookingStateCurrent(optional.get(), LocalDateTime.now());
        else if (WAITING.name().equals(state)) list = bookingRepository.findAllBookingState(optional.get().getId(), WAITING);
        else if (REJECTED.name().equals(state)) list = bookingRepository.findAllBookingState(optional.get().getId(), REJECTED);
        else throw new ServiceException(String.format("Unknown state: %s", state), 500);

        return list.stream()
                .map(BookingMapper::mapperBookingResponseOwnerToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookingResponseOwnerDto> getAllInItemOwner(Long userId, String state) {
        Optional<User> optional = userRepository.findById(userId);
        if (optional.isEmpty()) throw new ServiceException(REPOSITORY_ERROR__USER__ID_NOT_IN_REPO__ID);

        List<Booking> list;

        if      (ALL.name().equals(state)) list = bookingRepository.findByBookingOwner(optional.get().getId());
        else if (PAST.name().equals(state)) list = bookingRepository.findAllBookingStatePast(optional.get(), LocalDateTime.now());
        else if (FUTURE.name().equals(state)) list = bookingRepository.findByBookingOwnerAndStartAfter(optional.get().getId(), LocalDateTime.now());
        else if (CURRENT.name().equals(state)) list = bookingRepository.findAllBookingStateCurrent(optional.get(), LocalDateTime.now());
        else if (WAITING.name().equals(state)) list = bookingRepository.findAllOwnerBookingState(optional.get().getId(), WAITING);
        else if (REJECTED.name().equals(state)) list = bookingRepository.findAllOwnerBookingState(optional.get().getId(), REJECTED);
        else throw new ServiceException(String.format("Unknown state: %s", state), 500);

        return list.stream()
                .map(BookingMapper::mapperBookingResponseOwnerToDto)
                .collect(Collectors.toList());
    }

    @Override
    public BookingResponseOwnerDto getById(Long userId, Long bookingId) {
        try {
            Optional<Booking> optional = bookingRepository.findById(bookingId);
            if (!optional.get().getItem().getOwner().getId().equals(userId) && !optional.get().getOwner().getId().equals(userId)) throw new ServiceException(BOOKING_ERROR__USER_NOT_OWNER_ITEM);
            return BookingMapper.mapperBookingResponseOwnerToDto(optional.get());
        } catch (NoSuchElementException exception) {
            throw new ServiceException(REPOSITORY_ERROR__BOOKING__ID_NOT_IN_REPO__ID);
        }
    }

    @Override
    public BookingResponseOwnerDto add(Long userId, BookingDto bookingDto) {
        Booking booking = BookingMapper.mapperBookingDtoToBooking(bookingDto);
        try {

            Optional<User> optionalUser = userRepository.findById(userId);
            if (optionalUser.isEmpty()) throw new ServiceException(REPOSITORY_ERROR__USER__ID_NOT_IN_REPO__ID);

            Optional<Item> optionalItem = itemRepository.findById(bookingDto.getItemId());
            if (optionalItem.isEmpty()) throw new ServiceException(REPOSITORY_ERROR__ITEM__ID_NOT_IN_REPO__ID);
            Item item = optionalItem.get();

            if (item.getOwner().getId().equals(userId)) throw new ServiceException("Владелец вещи не может создать бронирование на ту же вещь", 404);
            if (!item.getAvailable()) throw new ServiceException(BOOKING_ERROR__AVAILABLE_FALSE);

            if (booking.getEnd().isBefore(booking.getStart()) || booking.getEnd().equals(booking.getStart())) throw new ServiceException(BOOKING_ERROR__VALID_DATETIME);
            if (!booking.getStart().isAfter(LocalDateTime.now())) throw new ServiceException(BOOKING_ERROR__VALID_DATETIME__START_TIME);

            booking.setOwner(optionalUser.get());
            booking.setItem(optionalItem.get());
            booking.setApproved(WAITING);
            booking = bookingRepository.save(booking);

            return BookingMapper.mapperBookingResponseOwnerToDto(booking);

        } catch (DataIntegrityViolationException exception) {
            throw new ServiceException(REPOSITORY_ERROR__USER__ID_NOT_IN_REPO__ID);
        }
    }

    @Override
    public BookingResponseBookerDto updateApproved(Long userId, Long bookingId, Boolean approved) {

        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) throw new ServiceException(REPOSITORY_ERROR__USER__ID_NOT_IN_REPO__ID);

        Optional<Booking> optionalBooking = bookingRepository.findById(bookingId);
        if (optionalBooking.isEmpty()) throw new ServiceException(REPOSITORY_ERROR__BOOKING__ID_NOT_IN_REPO__ID);
        Booking resultBooking = optionalBooking.get();

        if (optionalBooking.get().getOwner().getId().equals(userId)) throw new ServiceException("Недостаточно прав для смены статуса бронирования", 404);
        if (!optionalBooking.get().getItem().getOwner().getId().equals(userId)) throw new ServiceException("Пользователь не является владельцем вещи", 400);
        if (optionalBooking.get().getApproved().equals(APPROVED)) throw new ServiceException("Бронирование уже потдверждено", 400);

        resultBooking.setApproved(approved ? APPROVED : REJECTED);
        bookingRepository.save(resultBooking);
        return BookingMapper.mapperBookingResponseBookerToDto(resultBooking);
    }
}
