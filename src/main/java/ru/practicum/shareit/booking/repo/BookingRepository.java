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

    List<Booking> findByUserOrderByStartDesc(User user);

    List<Booking> findByItem_User_id(long userId);

    Optional<Booking> findFirstBookingByUserAndItemOrderByStartAsc(User user, Item item);

    @Query("select b from Booking b where b.item.id = :itemId and b.start <= :actual and b.approved = 'APPROVED' order by b.end desc")
    List<Booking> findListToLastBooking(long itemId, LocalDateTime actual);

    @Query("select b from Booking b where b.item.id = :itemId and b.start > :actual and b.approved = 'APPROVED' order by b.start asc")
    List<Booking> findListToNextBooking(long itemId, LocalDateTime actual);

    List<Booking> findByUserAndStartAfterOrderByStartDesc(User user, LocalDateTime actual);

    @Query("select b from Booking b where b.user.id = :userId and b.approved = :approved order by b.start desc")
    List<Booking> findAllBookingState(long userId, EnumBookingState approved);

    @Query("select b from Booking b where b.item.user.id = :userId and b.approved = :approved order by b.start desc")
    List<Booking> findAllUserBookingState(long userId, EnumBookingState approved);

    @Query("select b from Booking b where b.item.user.id = :userId order by b.start desc")
    List<Booking> findByBookingUser(long userId);

    @Query("select b from Booking b where b.item.user.id = :userId and b.start >= :actual order by b.start desc")
    List<Booking> findByBookingUserAndStartAfter(long userId, LocalDateTime actual);

    @Query("select b from Booking b where b.item.user.id = :userId and b.end <= :actual order by b.start desc")
    List<Booking> findAllBookingUserStatePast(long userId, LocalDateTime actual);

    @Query("select b from Booking b where b.item.user.id = :userId and b.start <= :actual and b.end >= :actual order by b.start desc")
    List<Booking> findAllBookingUserStateCurrent(long userId, LocalDateTime actual);

    @Query("select b from Booking b where b.user.id = :userId and b.end <= :actual order by b.start desc")
    List<Booking> findAllBookingStatePast(long userId, LocalDateTime actual);

    @Query("select b from Booking b where b.user.id = :userId and b.start <= :actual and b.end >= :actual order by b.start asc")
    List<Booking> findAllBookingStateCurrent(long userId, LocalDateTime actual);

    void deleteAll();
}
