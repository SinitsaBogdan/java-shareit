package ru.practicum.shareit.booking.repo;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repo.ItemRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repo.UserRepository;
import ru.practicum.shareit.booking.util.BookingState;

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

    private final User user1 = User.builder()
            .name("user-2")
            .email("mail-1@mail.ru")
            .build();

    private final User user2 = User.builder()
            .name("user-2")
            .email("mail-2@mail.ru")
            .build();

    private final Item item = Item.builder()
            .name("item")
            .description("description")
            .user(user1)
            .available(true)
            .build();

    private final Booking booking = Booking.builder()
            .approved(BookingState.APPROVED)
            .start(LocalDateTime.of(2023, 10, 20, 10, 0))
            .end(LocalDateTime.of(2023, 10, 20, 16, 0))
            .item(item)
            .user(user2)
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
            Booking booking = Booking.builder()
                    .start(LocalDateTime.of(2023, 10, 20, 10, 0))
                    .end(LocalDateTime.of(2023, 10, 20, 16, 0))
                    .item(item)
                    .user(user2)
                    .build();

            Assertions.assertThrows(DataIntegrityViolationException.class, () -> bookingRepository.save(booking));
        }

        @Test
        @DisplayName("Проверка валидации поля start - nullable")
        public void save__Fail_Start_nullable() {
            Booking booking = Booking.builder()
                    .approved(BookingState.APPROVED)
                    .end(LocalDateTime.of(2023, 10, 20, 16, 0))
                    .item(item)
                    .user(user2)
                    .build();

            Assertions.assertThrows(DataIntegrityViolationException.class, () -> bookingRepository.save(booking));
        }

        @Test
        @DisplayName("Проверка валидации поля end - nullable")
        public void save__Fail_End_nullable() {
            Booking booking = Booking.builder()
                    .approved(BookingState.APPROVED)
                    .start(LocalDateTime.of(2023, 10, 20, 10, 0))
                    .item(item)
                    .user(user2)
                    .build();

            Assertions.assertThrows(DataIntegrityViolationException.class, () -> bookingRepository.save(booking));
        }
    }

    @Nested
    @DisplayName("REPOSITORY")
    public class Repository {

        @BeforeEach
        public void beforeEach() {
            userRepository.save(user1);
            userRepository.save(user2);
            itemRepository.save(item);
            Assertions.assertNull(booking.getId());
            bookingRepository.save(booking);
        }

        @Test
        @DisplayName("Успешное сохранение объекта в таблицу BOOKINGS")
        public void save() {
            Assertions.assertNotNull(booking.getId());
        }

        @Test
        @DisplayName("Проверка метода - findFirstBookingByUserAndItemOrderByStartAsc")
        public void findFirstBookingByUserAndItemOrderByStartAsc() {
            Assertions.assertTrue(bookingRepository.findFirstBookingByUserAndItemOrderByStartAsc(user2, item).isPresent());
        }

        @Test
        @DisplayName("Проверка метода - findByItem_User_id")
        public void findByItem_User_id() {
            Assertions.assertEquals(bookingRepository.findByItem_User(user1).size(), 1);
        }

        @Test
        @DisplayName("Проверка метода - findListToLastBooking")
        public void findListToLastBooking() {
            Assertions.assertEquals(bookingRepository.findListToLastBooking(item.getId(), LocalDateTime.of(2024, 10, 20, 10, 0)).size(), 1);
        }

        @Test
        @DisplayName("Проверка метода - findListToNextBooking")
        public void findListToNextBooking() {
            Assertions.assertEquals(bookingRepository.findListToNextBooking(item.getId(), LocalDateTime.of(2020, 10, 20, 10, 0)).size(), 1);
        }

        @Test
        @DisplayName("Проверка метода - findByUserOrderByStartDesc")
        public void findByUserOrderByStartDesc() {
            Page<Booking> result = bookingRepository.findByUserOrderByStartDesc(user2, PageRequest.of(0, 3));
            Assertions.assertEquals(result.getSize(), 3);
            Assertions.assertTrue(result.get().findFirst().isPresent());
            Assertions.assertEquals(result.get().findFirst().get(), booking);
        }

        @Test
        @DisplayName("Проверка метода - findByUserAndStartAfterOrderByStartDesc")
        public void findByUserAndStartAfterOrderByStartDesc() {
            Page<Booking> result = bookingRepository.findByUserAndStartAfterOrderByStartDesc(user2, LocalDateTime.now(), PageRequest.of(0, 3));
            Assertions.assertEquals(result.getSize(), 3);
        }

        @Test
        @DisplayName("Проверка метода - findAllBookingState")
        public void findAllBookingState() {
            Page<Booking> result = bookingRepository.findAllBookingState(user2.getId(), BookingState.APPROVED, PageRequest.of(0, 3));
            Assertions.assertEquals(result.getSize(), 3);
            Assertions.assertTrue(result.get().findFirst().isPresent());
            Assertions.assertEquals(result.get().findFirst().get(), booking);
        }

        @Test
        @DisplayName("Проверка метода - findAllUserBookingState")
        public void findAllUserBookingState() {
            Page<Booking> result = bookingRepository.findAllUserBookingState(user1.getId(), BookingState.APPROVED, PageRequest.of(0, 3));
            Assertions.assertEquals(result.getSize(), 3);
            Assertions.assertTrue(result.get().findFirst().isPresent());
            Assertions.assertEquals(result.get().findFirst().get(), booking);
        }

        @Test
        @DisplayName("Проверка метода - findByBookingUser")
        public void findByBookingUser() {
            Page<Booking> result = bookingRepository.findByBookingUser(user1.getId(), PageRequest.of(0, 3));
            Assertions.assertEquals(result.getSize(), 3);
            Assertions.assertTrue(result.get().findFirst().isPresent());
            Assertions.assertEquals(result.get().findFirst().get(), booking);
        }

        @Test
        @DisplayName("Проверка метода - findByBookingUserAndStartAfter")
        public void findByBookingUserAndStartAfter() {
            Page<Booking> result = bookingRepository.findByBookingUserAndStartAfter(user1.getId(), LocalDateTime.now(), PageRequest.of(0, 3));
            Assertions.assertEquals(result.getSize(), 3);
        }

        @Test
        @DisplayName("Проверка метода - findAllBookingUserStatePast")
        public void findAllBookingUserStatePast() {
            Page<Booking> result = bookingRepository.findAllBookingUserStatePast(user1.getId(), LocalDateTime.of(2024, 10, 20, 10, 0), PageRequest.of(0, 3));
            Assertions.assertEquals(result.getSize(), 3);
            Assertions.assertTrue(result.get().findFirst().isPresent());
            Assertions.assertEquals(result.get().findFirst().get(), booking);
        }

        @Test
        @DisplayName("Проверка метода - findAllBookingUserStateCurrent")
        public void findAllBookingUserStateCurrent() {
            Page<Booking> result = bookingRepository.findAllBookingUserStateCurrent(user1.getId(), LocalDateTime.of(2023, 10, 20, 14, 0), PageRequest.of(0, 3));
            Assertions.assertEquals(result.getSize(), 3);
            Assertions.assertTrue(result.get().findFirst().isPresent());
            Assertions.assertEquals(result.get().findFirst().get(), booking);
        }

        @Test
        @DisplayName("Проверка метода - findAllBookingStatePast")
        public void findAllBookingStatePast() {
            Page<Booking> result = bookingRepository.findAllBookingStatePast(user2.getId(), LocalDateTime.of(2023, 10, 25, 10, 0), PageRequest.of(0, 3));
            Assertions.assertEquals(result.getSize(), 3);
            Assertions.assertTrue(result.get().findFirst().isPresent());
            Assertions.assertEquals(result.get().findFirst().get(), booking);
        }

        @Test
        @DisplayName("Проверка метода - findAllBookingStateCurrent")
        public void findAllBookingStateCurrent() {
            Page<Booking> result = bookingRepository.findAllBookingStateCurrent(user2.getId(), LocalDateTime.of(2023, 10, 20, 15, 0), PageRequest.of(0, 3));
            Assertions.assertEquals(result.getSize(), 3);
            Assertions.assertTrue(result.get().findFirst().isPresent());
            Assertions.assertEquals(result.get().findFirst().get(), booking);
        }
    }
}