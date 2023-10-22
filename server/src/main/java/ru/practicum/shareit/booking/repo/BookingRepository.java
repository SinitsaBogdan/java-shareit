package ru.practicum.shareit.booking.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.util.BookingState;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    Optional<Booking> findFirstBookingByUserAndItemOrderByStartAsc(User user, Item item);

    List<Booking> findByItem_User(User user);

    @Query("select b from Booking b where b.item.id = :itemId and b.start <= :actual and b.approved = 'APPROVED' order by b.end desc")
    List<Booking> findListToLastBooking(long itemId, LocalDateTime actual);

    @Query("select b from Booking b where b.item.id = :itemId and b.start > :actual and b.approved = 'APPROVED' order by b.start asc")
    List<Booking> findListToNextBooking(long itemId, LocalDateTime actual);

    Page<Booking> findByUserOrderByStartDesc(User user, Pageable pageable);

    Page<Booking> findByUserAndStartAfterOrderByStartDesc(User user, LocalDateTime actual, Pageable pageable);

    @Query("select b from Booking b where b.user.id = :userId and b.approved = :approved order by b.start desc")
    Page<Booking> findAllBookingState(long userId, BookingState approved, Pageable pageable);

    @Query("select b from Booking b where b.item.user.id = :userId and b.approved = :approved order by b.start desc")
    Page<Booking> findAllUserBookingState(long userId, BookingState approved, Pageable pageable);

    @Query("select b from Booking b where b.item.user.id = :userId order by b.start desc")
    Page<Booking> findByBookingUser(long userId, Pageable pageable);

    @Query("select b from Booking b where b.item.user.id = :userId and b.start >= :actual order by b.start desc")
    Page<Booking> findByBookingUserAndStartAfter(long userId, LocalDateTime actual, Pageable pageable);

    @Query("select b from Booking b where b.item.user.id = :userId and b.end <= :actual order by b.start desc")
    Page<Booking> findAllBookingUserStatePast(long userId, LocalDateTime actual, Pageable pageable);

    @Query("select b from Booking b where b.item.user.id = :userId and b.start <= :actual and b.end >= :actual order by b.start desc")
    Page<Booking> findAllBookingUserStateCurrent(long userId, LocalDateTime actual, Pageable pageable);

    @Query("select b from Booking b where b.user.id = :userId and b.end <= :actual order by b.start desc")
    Page<Booking> findAllBookingStatePast(long userId, LocalDateTime actual, Pageable pageable);

    @Query("select b from Booking b where b.user.id = :userId and b.start <= :actual and b.end >= :actual order by b.start asc")
    Page<Booking> findAllBookingStateCurrent(long userId, LocalDateTime actual, Pageable pageable);
}
