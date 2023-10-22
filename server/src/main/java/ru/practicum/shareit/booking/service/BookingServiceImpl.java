package ru.practicum.shareit.booking.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.dto.BookingRequestDto;
import ru.practicum.shareit.booking.dto.BookingResponseDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.repo.BookingRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repo.ItemRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repo.UserRepository;
import ru.practicum.shareit.util.BookingState;
import ru.practicum.shareit.util.exeptions.CustomException;
import ru.practicum.shareit.util.exeptions.RepositoryException;
import ru.practicum.shareit.util.exeptions.ServiceException;
import ru.practicum.shareit.booking.util.MapperBooking;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.practicum.shareit.util.BookingState.*;
import static ru.practicum.shareit.util.exeptions.ErrorMessage.*;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    @Override
    public List<BookingResponseDto> getAll(long userId, String state, Pageable pageable) {

        Optional<User> optional = userRepository.findById(userId);
        if (optional.isEmpty()) throw new RepositoryException(REPOSITORY_ERROR__USER__ID_NOT_IN_REPO__ID);

        try {

            BookingState status = BookingState.valueOf(state);
            LocalDateTime time = LocalDateTime.now();

            switch (status) {
                case ALL : {
                    return bookingRepository.findByUserOrderByStartDesc(optional.get(), pageable).stream()
                            .map(MapperBooking::mapperEntityToDto)
                            .collect(Collectors.toList());
                }
                case PAST : {
                    return bookingRepository.findAllBookingStatePast(optional.get().getId(), time, pageable).stream()
                            .map(MapperBooking::mapperEntityToDto)
                            .collect(Collectors.toList());
                }
                case FUTURE : {
                    return bookingRepository.findByUserAndStartAfterOrderByStartDesc(optional.get(), time, pageable).stream()
                            .map(MapperBooking::mapperEntityToDto)
                            .collect(Collectors.toList());
                }
                case CURRENT : {
                    return bookingRepository.findAllBookingStateCurrent(optional.get().getId(), time, pageable).stream()
                            .map(MapperBooking::mapperEntityToDto)
                            .collect(Collectors.toList());
                }
                case WAITING : {
                    return bookingRepository.findAllBookingState(optional.get().getId(), WAITING, pageable).stream()
                            .map(MapperBooking::mapperEntityToDto)
                            .collect(Collectors.toList());
                }
                case REJECTED : {
                    return bookingRepository.findAllBookingState(optional.get().getId(), REJECTED, pageable).stream()
                            .map(MapperBooking::mapperEntityToDto)
                            .collect(Collectors.toList());
                }
                default: return new ArrayList<>();
            }

        } catch (IllegalArgumentException exception) {
            throw new CustomException(String.format("Unknown state: %s", state), 500);
        }
    }

    @Override
    public List<BookingResponseDto> getAllInItemOwner(long userId, String state, Pageable pageable) {

        Optional<User> optional = userRepository.findById(userId);
        if (optional.isEmpty()) throw new RepositoryException(REPOSITORY_ERROR__USER__ID_NOT_IN_REPO__ID);


        try {

            BookingState status = BookingState.valueOf(state);
            LocalDateTime time = LocalDateTime.now();

            switch (status) {
                case ALL : {
                    return bookingRepository.findByBookingUser(optional.get().getId(), pageable).stream()
                            .map(MapperBooking::mapperEntityToDto)
                            .collect(Collectors.toList());
                }
                case PAST : {
                    return bookingRepository.findAllBookingUserStatePast(optional.get().getId(), time, pageable).stream()
                            .map(MapperBooking::mapperEntityToDto)
                            .collect(Collectors.toList());
                }
                case FUTURE : {
                    return bookingRepository.findByBookingUserAndStartAfter(optional.get().getId(), time, pageable).stream()
                            .map(MapperBooking::mapperEntityToDto)
                            .collect(Collectors.toList());
                }
                case CURRENT : {
                    return bookingRepository.findAllBookingUserStateCurrent(optional.get().getId(), time, pageable).stream()
                            .map(MapperBooking::mapperEntityToDto)
                            .collect(Collectors.toList());
                }
                case WAITING : {
                    return bookingRepository.findAllUserBookingState(optional.get().getId(), WAITING, pageable).stream()
                            .map(MapperBooking::mapperEntityToDto)
                            .collect(Collectors.toList());
                }
                case REJECTED : {
                    return bookingRepository.findAllUserBookingState(optional.get().getId(), REJECTED, pageable).stream()
                            .map(MapperBooking::mapperEntityToDto)
                            .collect(Collectors.toList());
                }
                default: return new ArrayList<>();
            }

        } catch (IllegalArgumentException exception) {
            throw new CustomException(String.format("Unknown state: %s", state), 500);
        }
    }

    @Override
    public BookingResponseDto getById(long userId, long bookingId) {

        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) throw new RepositoryException(REPOSITORY_ERROR__USER__ID_NOT_IN_REPO__ID);

        Optional<Booking> optional = bookingRepository.findById(bookingId);
        if (optional.isEmpty()) throw new RepositoryException(REPOSITORY_ERROR__BOOKING__ID_NOT_IN_REPO__ID);

        Booking booking = optional.get();
        if (!booking.getItem().getUser().getId().equals(userId) && !booking.getUser().getId().equals(userId)) {
            throw new ServiceException(BOOKING_ERROR__USER_NOT_OWNER_ITEM);
        }

        return MapperBooking.mapperEntityToDto(booking);
    }

    @Override
    @Transactional
    public BookingResponseDto save(long userId, BookingRequestDto bookingRequestDto) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) throw new RepositoryException(REPOSITORY_ERROR__USER__ID_NOT_IN_REPO__ID);

        Optional<Item> optionalItem = itemRepository.findById(bookingRequestDto.getItemId());
        if (optionalItem.isEmpty()) throw new RepositoryException(REPOSITORY_ERROR__ITEM__ID_NOT_IN_REPO__ID);

        Item item = optionalItem.get();
        Booking booking = MapperBooking.mapperDtoToEntity(bookingRequestDto);

        if (item.getUser().getId().equals(userId)) throw new ServiceException(BOOKING_ERROR__FAIL_PARAM_BOOKING__OWNER_ID);
        if (!item.getAvailable()) throw new ServiceException(BOOKING_ERROR__AVAILABLE_FALSE);
        if (booking.getEnd().isBefore(booking.getStart()) || booking.getEnd().equals(booking.getStart())) throw new ServiceException(BOOKING_ERROR__VALID_DATETIME);
        if (!booking.getStart().isAfter(LocalDateTime.now())) throw new ServiceException(BOOKING_ERROR__VALID_DATETIME__START_TIME);

        booking.setUser(optionalUser.get());
        booking.setItem(optionalItem.get());
        booking.setApproved(WAITING);
        booking = bookingRepository.save(booking);

        return MapperBooking.mapperEntityToDto(booking);
    }

    @Override
    @Transactional
    public BookingResponseDto updateApproved(long userId, long bookingId, boolean approved) {

        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) throw new RepositoryException(REPOSITORY_ERROR__USER__ID_NOT_IN_REPO__ID);

        Optional<Booking> optionalBooking = bookingRepository.findById(bookingId);
        if (optionalBooking.isEmpty()) throw new RepositoryException(REPOSITORY_ERROR__BOOKING__ID_NOT_IN_REPO__ID);

        Booking booking = optionalBooking.get();

        if (booking.getUser().getId().equals(userId)) throw new ServiceException(BOOKING_ERROR__BLOCK_SAVE_BOOKING__USER);
        if (!booking.getItem().getUser().getId().equals(userId)) throw new ServiceException(BOOKING_ERROR__BLOCK_SAVE_BOOKING__USER_NOT_IN_BOOKING);
        if (booking.getApproved().equals(APPROVED)) throw new ServiceException(BOOKING_ERROR__BLOCK_SAVE_BOOKING__BOOKING__APPROVE);

        booking.setApproved(approved ? APPROVED : REJECTED);
        return MapperBooking.mapperEntityToDto(bookingRepository.save(booking));
    }
}
