package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
@RequestMapping(path = "/users")
public class UserController {

    private final UserService userService;

    /**
     * Запрос всех пользователей
     **/
    @GetMapping
    public List<UserDto> getAll(@RequestHeader("X-Later-User-Id") long userId) {
        log.info(
                "   GET [http://localhost:8080/users] : " +
                        "Запрос на получение всех пользователей от пользователя id : {}",
                userId
        );
        return null;
    }

    /**
     * Запрос пользователя по ID
     **/
    @GetMapping("/{id}")
    public UserDto getById(
            @RequestHeader("X-Later-User-Id") long userId,
            @RequestParam long id
    ) {
        log.info(
                "   GET [http://localhost:8080/users/{}] : " +
                        "Запрос на получение пользователя id : {} от пользователя id : {}",
                id, id, userId
        );
        return null;
    }

    /**
     * Добавление нового пользователя
     **/
    @PostMapping
    public UserDto add(
            @RequestHeader("X-Later-User-Id") long userId,
            @RequestBody UserDto user
    ) {
        log.info(
                "  POST [http://localhost:8080/users] : " +
                        "Запрос на добавление пользователя от пользователя id : {} - {}",
                userId, user
        );
        return null;
    }

    /**
     * Обновление существующего пользователя
     **/
    @PatchMapping
    public UserDto update(
            @RequestHeader("X-Later-User-Id") long userId,
            @RequestBody UserDto user
    ) {
        log.info(
                " PATCH [http://localhost:8080/users] : " +
                        "Запрос на обновление пользователя от пользователя id : {} - {}",
                userId, user
        );
        return null;
    }

    /**
     * Удаление существующего пользователя
     **/
    @DeleteMapping("/{id}")
    public void delete(
            @RequestHeader("X-Later-User-Id") long userId,
            @PathVariable long id
    ) {
        log.info(
                " DELETE [http://localhost:8080/users/{}] : " +
                        "Запрос на удаление пользователя id : {} от пользователя id : {}",
                id, id, userId
        );
    }
}
