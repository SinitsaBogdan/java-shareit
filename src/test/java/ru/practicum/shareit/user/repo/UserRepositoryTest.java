package ru.practicum.shareit.user.repo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.util.exeptions.CustomException;


@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository repository;

    @Test
    public void contextLoads() {
        Assertions.assertNotNull(repository);
    }

    @Test
    @DisplayName("Успешное сохранение объекта в таблицу USERS")
    public void saveUser() {
        User emp = User.builder()
                .name("user-1")
                .email("mail@mail.ru")
                .build();

        Assertions.assertNull(emp.getId());
        repository.save(emp);
        Assertions.assertNotNull(emp.getId());
    }

    @Test
    @DisplayName("Проверка валидации поля name - MaxLength20")
    public void saveUser__Fail_Name_MaxLength20() {
        User emp = User.builder()
                .name("user-1-BigName-BigName-BigName-33")
                .email("mail@mail.ru")
                .build();

        Assertions.assertThrows(DataIntegrityViolationException.class, () -> {
            repository.save(emp);
        });
    }

}