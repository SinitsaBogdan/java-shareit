package ru.practicum.shareit.booking.repo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repo.ItemRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repo.UserRepository;
import ru.practicum.shareit.util.EnumBookingState;

import java.time.LocalDateTime;

@DataJpaTest
@DisplayName("Интеграционное тестирование - BookingRepository")
class BookingRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private BookingRepository bookingRepository;

    private final User user_1 = User.builder()
            .name("user-2")
            .email("mail-1@mail.ru")
            .build();

    private final User user_2 = User.builder()
            .name("user-2")
            .email("mail-2@mail.ru")
            .build();

    private final Item item = Item.builder()
            .name("item")
            .description("description")
            .user(user_1)
            .available(true)
            .build();

    private final Booking booking = Booking.builder()
            .approved(EnumBookingState.APPROVED)
            .start(LocalDateTime.of(2023, 10, 20, 10, 0))
            .end(LocalDateTime.of(2023, 10, 20, 16, 0))
            .item(item)
            .user(user_2)
            .build();

    @Test
    @DisplayName("CONFIG")
    public void contextLoads() {
        Assertions.assertNotNull(bookingRepository);
    }

    @Nested
    @DisplayName("ENTITY")
    public class Entity {

        @Test
        @DisplayName("Проверка валидации поля approved - nullable")
        public void save__Fail_Approved_nullable() {
            userRepository.save(user_1);
            userRepository.save(user_2);
            itemRepository.save(item);

            Booking booking = Booking.builder()
                    .start(LocalDateTime.of(2023, 10, 20, 10, 0))
                    .end(LocalDateTime.of(2023, 10, 20, 16, 0))
                    .item(item)
                    .user(user_2)
                    .build();

            Assertions.assertThrows(DataIntegrityViolationException.class, () -> bookingRepository.save(booking));
        }

        @Test
        @DisplayName("Проверка валидации поля start - nullable")
        public void save__Fail_Start_nullable() {
            userRepository.save(user_1);
            userRepository.save(user_2);
            itemRepository.save(item);

            Booking booking = Booking.builder()
                    .approved(EnumBookingState.APPROVED)
                    .end(LocalDateTime.of(2023, 10, 20, 16, 0))
                    .item(item)
                    .user(user_2)
                    .build();

            Assertions.assertThrows(DataIntegrityViolationException.class, () -> bookingRepository.save(booking));
        }

        @Test
        @DisplayName("Проверка валидации поля end - nullable")
        public void save__Fail_End_nullable() {
            userRepository.save(user_1);
            userRepository.save(user_2);
            itemRepository.save(item);

            Booking booking = Booking.builder()
                    .approved(EnumBookingState.APPROVED)
                    .start(LocalDateTime.of(2023, 10, 20, 10, 0))
                    .item(item)
                    .user(user_2)
                    .build();

            Assertions.assertThrows(DataIntegrityViolationException.class, () -> bookingRepository.save(booking));
        }
    }

    @Nested
    @DisplayName("REPOSITORY")
    public class Repository {

        @Test
        @DisplayName("Успешное сохранение объекта в таблицу BOOKINGS")
        public void save() {
            userRepository.save(user_1);
            userRepository.save(user_2);
            itemRepository.save(item);

            Assertions.assertNull(booking.getId());
            bookingRepository.save(booking);
            Assertions.assertNotNull(booking.getId());
        }

        @Test
        @DisplayName("Проверка метода - findFirstBookingByUserAndItemOrderByStartAsc")
        public void findFirstBookingByUserAndItemOrderByStartAsc() {
            userRepository.save(user_1);
            userRepository.save(user_2);
            itemRepository.save(item);
            bookingRepository.save(booking);

            Assertions.assertTrue(bookingRepository.findFirstBookingByUserAndItemOrderByStartAsc(user_2, item).isPresent());
        }

        @Test
        @DisplayName("Проверка метода - findByItem_User_id")
        public void findByItem_User_id() {
            userRepository.save(user_1);
            userRepository.save(user_2);
            itemRepository.save(item);
            bookingRepository.save(booking);

            Assertions.assertEquals(bookingRepository.findByItem_User(user_1).size(), 1);
        }

        @Test
        @DisplayName("Проверка метода - findListToLastBooking")
        public void findListToLastBooking() {
            userRepository.save(user_1);
            userRepository.save(user_2);
            itemRepository.save(item);
            bookingRepository.save(booking);

            Assertions.assertEquals(bookingRepository.findListToLastBooking(item.getId(), LocalDateTime.of(2024, 10, 20, 10, 0)).size(), 1);
        }

        @Test
        @DisplayName("Проверка метода - findListToNextBooking")
        public void findListToNextBooking() {
            userRepository.save(user_1);
            userRepository.save(user_2);
            itemRepository.save(item);
            bookingRepository.save(booking);

            Assertions.assertEquals(bookingRepository.findListToLastBooking(item.getId(), LocalDateTime.of(2024, 10, 20, 10, 0)).size(), 1);
        }

    }
}