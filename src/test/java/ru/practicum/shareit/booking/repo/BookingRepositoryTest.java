package ru.practicum.shareit.booking.repo;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.util.EnumBookingState;

import java.time.LocalDateTime;


@DataJpaTest
@RunWith(SpringRunner.class)
public class BookingRepositoryTest {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private BookingRepository repository;

    @Test
    public void contextLoads() {
        Assertions.assertNotNull(em);
    }

    @Test
    public void verifyBootstrappingByPersistingAnBooking() {

        User user_1 = User.builder().name("user-1").email("mail-1@mail.ru").build();
        Item item = Item.builder().name("item").description("description").available(true).user(user_1).build();
        User user_2 = User.builder().name("user-2").email("mail-2@mail.ru").build();

        Booking booking = new Booking();
        booking.setApproved(EnumBookingState.APPROVED);
        booking.setStart(LocalDateTime.of(2023, 12, 1, 10, 0));
        booking.setEnd(LocalDateTime.of(2023, 12, 1, 16, 0));
        booking.setItem(item);
        booking.setUser(user_2);

        Assertions.assertNull(booking.getId());
        em.persist(booking);
        Assertions.assertNotNull(booking.getId());
    }

    @Test
    public void verifyRepositoryByPersistingAnBooking() {

        User user_1 = User.builder().name("user-1").email("mail-1@mail.ru").build();
        Item item = Item.builder().name("item").description("description").available(true).user(user_1).build();
        User user_2 = User.builder().name("user-2").email("mail-2@mail.ru").build();

        Booking booking = new Booking();
        booking.setApproved(EnumBookingState.APPROVED);
        booking.setStart(LocalDateTime.of(2023, 12, 1, 10, 0));
        booking.setEnd(LocalDateTime.of(2023, 12, 1, 16, 0));
        booking.setItem(item);
        booking.setUser(user_2);

        Assertions.assertNull(booking.getId());
        repository.save(booking);
        Assertions.assertNotNull(booking.getId());
    }
}