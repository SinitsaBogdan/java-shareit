package ru.practicum.shareit.booking.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.dto.BookingShortDto;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.util.EnumBookingState;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByOwnerOrderByStartDesc(User user);

    List<Booking> findByOwnerAndStartAfterOrderByStartDesc(User user, LocalDateTime actual);

    @Query("select b from Booking b where b.owner.id = :userId and b.approved = :approved order by start desc")
    List<Booking> findAllBookingState(Long userId, EnumBookingState approved);

    @Query("select b from Booking b where b.item.owner.id = :userId and b.approved = :approved order by start desc")
    List<Booking> findAllOwnerBookingState(Long userId, EnumBookingState approved);

    @Query("select b from Booking b where b.item.owner.id = :userId order by start desc")
    List<Booking> findByBookingOwner(Long userId);

    @Query("select b from Booking b where b.item.owner.id = :userId and b.start >= :actual order by start desc")
    List<Booking> findByBookingOwnerAndStartAfter(Long userId, LocalDateTime actual);

    @Query("select b from Booking b where b.item.owner.id = :userId and b.end <= :actual order by start desc")
    List<Booking> findAllBookingStatePast(User user, LocalDateTime actual);

    @Query("select b from Booking b where b.item.owner.id = :userId and b.start <= :actual and b.end >= :actual order by start desc")
    List<Booking> findAllBookingStateCurrent(User user, LocalDateTime actual);

    @Query(value = "select b from Booking b where b.item.id = :itemId and b.end <= :actual order by b.start asc")
    List<Booking> findLastBookingToItem(Long itemId, LocalDateTime actual);

    @Query(value = "select b from Booking b where b.item.id = :itemId and b.start > :actual order by b.end asc")
    List<Booking> findNextActualBookingToItem(Long itemId, LocalDateTime actual);
}
