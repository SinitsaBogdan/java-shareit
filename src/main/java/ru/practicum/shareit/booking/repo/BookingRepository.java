package ru.practicum.shareit.booking.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.util.EnumBookingState;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByOwnerOrderByStartDesc(User user);

    Optional<Booking> findFirstBookingByOwnerAndItemOrderByStartAsc(User user, Item item);

    @Query("select b from Booking b where b.item.id = :itemId and b.start <= :actual and b.approved = 'APPROVED' order by b.end desc")
    List<Booking> findListToLastBooking(Long itemId, LocalDateTime actual);

    @Query("select b from Booking b where b.item.id = :itemId and b.start > :actual and b.approved = 'APPROVED' order by b.start asc")
    List<Booking> findListToNextBooking(Long itemId, LocalDateTime actual);

    List<Booking> findByOwnerAndStartAfterOrderByStartDesc(User owner, LocalDateTime actual);

    @Query("select b from Booking b where b.owner.id = :userId and b.approved = :approved order by start desc")
    List<Booking> findAllBookingState(Long userId, EnumBookingState approved);

    @Query("select b from Booking b where b.item.owner.id = :userId and b.approved = :approved order by start desc")
    List<Booking> findAllOwnerBookingState(Long userId, EnumBookingState approved);

    @Query("select b from Booking b where b.item.owner.id = :userId order by start desc")
    List<Booking> findByBookingOwner(Long userId);

    @Query("select b from Booking b where b.item.owner.id = :userId and b.start >= :actual order by start desc")
    List<Booking> findByBookingOwnerAndStartAfter(Long userId, LocalDateTime actual);

    @Query("select b from Booking b where b.item.owner.id = :userId and b.end <= :actual order by start desc")
    List<Booking> findAllBookingOwnerStatePast(Long userId, LocalDateTime actual);

    @Query("select b from Booking b where b.item.owner.id = :userId and b.start <= :actual and b.end >= :actual order by start desc")
    List<Booking> findAllBookingOwnerStateCurrent(Long userId, LocalDateTime actual);

    @Query("select b from Booking b where b.owner.id = :userId and b.end <= :actual order by start desc")
    List<Booking> findAllBookingStatePast(Long userId, LocalDateTime actual);

    @Query("select b from Booking b where b.owner.id = :userId and b.start <= :actual and b.end >= :actual order by start desc")
    List<Booking> findAllBookingStateCurrent(Long userId, LocalDateTime actual);
}
