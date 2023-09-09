package ru.practicum.shareit.user;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.service.UserService;

import java.util.List;

/**
 * TODO Sprint add-controllers.
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    /**
     * Запрос всех пользователей
     **/
    @GetMapping
    public List<UserDto> getAll() {
        log.info(
                "   GET [http://localhost:8080/users] : " +
                        "Запрос на получение всех пользователей"
        );
        return null;
    }

    /**
     * Запрос пользователя по ID
     **/
    @GetMapping("/{id}")
    public UserDto getById(@RequestParam Long id) {
        log.info(
                "   GET [http://localhost:8080/users/{}] : " +
                        "Запрос на получение пользователя id : {}",
                id, id
        );
        return null;
    }

    /**
     * Добавление нового пользователя
     **/
    @PostMapping
    public UserDto add(@RequestBody UserDto user) {
        log.info(
                "  POST [http://localhost:8080/users] : " +
                        "Запрос на добавление пользователя - {}",
                user
        );
        return userService.add(user);
    }

    /**
     * Обновление существующего пользователя
     **/
    @PatchMapping
    public UserDto update(@RequestBody UserDto user) {
        log.info(
                " PATCH [http://localhost:8080/users] : " +
                        "Запрос на обновление пользователя - {}",
                user
        );
        return null;
    }

    /**
     * Удаление существующего пользователя
     **/
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        log.info(
                " DELETE [http://localhost:8080/users/{}] : " +
                        "Запрос на удаление пользователя id : {}",
                id, id
        );
    }
}
