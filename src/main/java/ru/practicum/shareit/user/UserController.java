package ru.practicum.shareit.user;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.service.UserService;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    /**
     * Запрос всех пользователей
     **/
    @GetMapping
    public List<UserDto> getAll() {
        log.info("   GET [http://localhost:8080/users] : Запрос на получение всех пользователей");
        return userService.getAll();
    }

    /**
     * Запрос пользователя по ID
     **/
    @GetMapping("/{userId}")
    public UserDto getById(@PathVariable long userId) {
        log.info("   GET [http://localhost:8080/users/{}] : Запрос на получение пользователя id : {}", userId, userId);
        return userService.getById(userId);
    }

    /**
     * Добавление нового пользователя
     **/
    @PostMapping
    public UserDto add(@RequestBody UserDto user) {
        log.info("  POST [http://localhost:8080/users] : Запрос на добавление пользователя - {}", user);
        return userService.save(user);
    }

    /**
     * Обновление существующего пользователя
     **/
    @PatchMapping("/{userId}")
    public UserDto update(@PathVariable long userId, @RequestBody @NotNull UserDto user) {
        user.setId(userId);
        log.info(" PATCH [http://localhost:8080/users] : Запрос на обновление пользователя - {}", user);
        return userService.update(user);
    }

    /**
     * Удаление существующего пользователя
     **/
    @DeleteMapping("/{userId}")
    public void delete(@PathVariable long userId) {
        log.info(" DELETE [http://localhost:8080/users/{}] : Запрос на удаление пользователя id : {}", userId, userId);
        userService.deleteById(userId);
    }
}
